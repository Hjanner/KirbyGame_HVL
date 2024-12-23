package KirbyGame_HVL.git.miniGames.viejita;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Random;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Files.FileType;

public class GamePanelViejita implements Screen {
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture backgroundTexture;
    private Array<Texture> odsTextures;
    private Random random;
    private boolean resourcesLoaded = false;

    private final int CELL_SIZE = 100;
    private final int BOARD_SIZE = 3;
    private float boardX;
    private float boardY;

    private final Color LIGHT_PINK = new Color(1, 0.8f, 0.9f, 1); // Rosa claro
    private final Color DARK_PINK = new Color(0.8f, 0.4f, 0.6f, 1); // Rosa oscuro

    private Cell[][] board;
    private boolean isPlayerTurn;
    private boolean gameOver;
    private String winner;

    private ArrayList<String> odsImages;
    private final String ODS_IMAGES_PATH = "assets/art/minijuegos/ods/";

    public GamePanelViejita() {
        //this.game = game;
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        random = new Random();

        odsImages = new ArrayList<>();
        odsTextures = new Array<>();
        backgroundTexture = new Texture(Gdx.files.internal("assets/art/minijuegos/logito.png"));

        loadODSTextures();

        preloadResources();
        initGame();
    }

    @Override
    public void render(float delta) {
        if (!resourcesLoaded) {
            return;
        }
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);        //rectangulo
        shapeRenderer.setColor(LIGHT_PINK);
        shapeRenderer.rect(boardX - 20, boardY - 20,
            CELL_SIZE * BOARD_SIZE + 40,
            CELL_SIZE * BOARD_SIZE + 40);
        shapeRenderer.end();

        drawBoard();                                                //lineas
        drawPieces();                                               //ods y x

        if (!gameOver && isPlayerTurn) {
            handleInput();
        } else if (!gameOver && !isPlayerTurn) {
            makeComputerMove();                 // Turno de la computadora
        }

        // UI
        batch.begin();
        String message;
        if (gameOver) {
            message = winner != null ? "¡Ganó " + winner + "!" : "¡Empate!";
        } else {
            message = isPlayerTurn ? "Tu turno" : "Turno de la computadora";
        }
        font.draw(batch, message, 10, Gdx.graphics.getHeight() - 10);
        batch.end();

        //ciclo
        if (gameOver && Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            initGame();
        }
    }

    private void initGame() {
        board = new Cell[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new Cell();
            }
        }
        isPlayerTurn = false;
        gameOver = false;
        winner = null;

        boardX = (Gdx.graphics.getWidth() - (CELL_SIZE * BOARD_SIZE)) / 2;
        boardY = (Gdx.graphics.getHeight() - (CELL_SIZE * BOARD_SIZE)) / 2;
    }

    private class Cell {
        char type;                  // 'X' para X, 'O' para ODS, ' ' para vacío
        Texture odsTexture;          // null si no es ODS

        Cell() {
            type = ' ';
            odsTexture = null;
        }
    }

    private void preloadResources() {
        try {
            // Asegurarse de que las colecciones estén vacías
            odsTextures.clear();
            odsImages.clear();

            // Cargar las texturas directamente
            FileHandle dirHandle = Gdx.files.internal(ODS_IMAGES_PATH);
            for (FileHandle entry : dirHandle.list()) {
                if (entry.name().toLowerCase().endsWith(".png") ||
                    entry.name().toLowerCase().endsWith(".jpg")) {

                    try {
                        // Crear la textura inmediatamente
                        Texture texture = new Texture(entry);
                        odsTextures.add(texture);
                        odsImages.add(entry.path());
                        System.out.println("Textura cargada: " + entry.name());
                    } catch (Exception e) {
                        System.err.println("Error cargando textura individual: " + entry.name());
                        e.printStackTrace();
                    }
                }
            }

            if (odsTextures.size == 0) {
                throw new RuntimeException("No se encontraron imágenes ODS válidas en " + ODS_IMAGES_PATH);
            }

            resourcesLoaded = true;
            System.out.println("Total de texturas cargadas: " + odsTextures.size);

        } catch (Exception e) {
            System.err.println("Error en preloadResources: " + e.getMessage());
            e.printStackTrace();

            // Cargar textura por defecto
            try {
                Texture defaultTexture = new Texture(Gdx.files.internal("assets/art/minijuegos/logito.png"));
                odsTextures.clear();
                odsTextures.add(defaultTexture);
                resourcesLoaded = true;
            } catch (Exception ex) {
                System.err.println("Error crítico: No se pudo cargar ni la textura por defecto");
            }
        }
    }


    private void loadODSTextures() {
        try {
            FileHandle dirHandle = Gdx.files.internal(ODS_IMAGES_PATH);
            for (FileHandle entry : dirHandle.list()) {
                if (entry.extension().equals("png") || entry.extension().equals("jpg")) {
                    odsImages.add(entry.path());
                }
            }

            // crear las texturas
            for (String imagePath : odsImages) {
                try {
                    Texture texture = new Texture(Gdx.files.internal(imagePath));
                    odsTextures.add(texture);
                } catch (Exception e) {
                    System.err.println("Error cargando textura: " + imagePath);
                }
            }

            if (odsTextures.size == 0) {
                throw new RuntimeException("No se pudieron cargar las imagenes ODS desde " + ODS_IMAGES_PATH);
            }
        } catch (Exception e) {
            System.err.println("Error cargando imagenes ODS: " + e.getMessage());
            try {
                Texture defaultTexture = new Texture(Gdx.files.internal("assets/art/minijuegos/logito.png"));
                odsTextures.add(defaultTexture);
            } catch (Exception ex) {
                System.err.println("Error cargando textura por defecto");
            }
        }
    }

    private void drawBoard() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(DARK_PINK);

        // Líneas verticales
        for (int i = 1; i < BOARD_SIZE; i++) {
            shapeRenderer.line(
                boardX + i * CELL_SIZE, boardY,
                boardX + i * CELL_SIZE, boardY + BOARD_SIZE * CELL_SIZE
            );
        }

        // Líneas horizontales
        for (int i = 1; i < BOARD_SIZE; i++) {
            shapeRenderer.line(
                boardX, boardY + i * CELL_SIZE,
                boardX + BOARD_SIZE * CELL_SIZE, boardY + i * CELL_SIZE
            );
        }

        shapeRenderer.end();
    }

    private void drawPieces() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                float x = boardX + j * CELL_SIZE;
                float y = boardY + i * CELL_SIZE;

                if (board[i][j].type == 'X') {
                    // Dibujar X
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                    shapeRenderer.setColor(DARK_PINK);
                        shapeRenderer.line(x + 20, y + 20, x + CELL_SIZE - 20, y + CELL_SIZE - 20);
                        shapeRenderer.line(x + CELL_SIZE - 20, y + 20, x + 20, y + CELL_SIZE - 20);
                    shapeRenderer.end();
                } else if (board[i][j].type == 'O' && board[i][j].odsTexture != null) {
                    // Dibujar imagen ODS
                    batch.begin();
                        batch.draw(board[i][j].odsTexture, x + 10, y + 10,
                        CELL_SIZE - 20, CELL_SIZE - 20);
                    batch.end();
                }
            }
        }
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            int boardI = (int)((touchY - boardY) / CELL_SIZE);
            int boardJ = (int)((touchX - boardX) / CELL_SIZE);

            if (boardI >= 0 && boardI < BOARD_SIZE &&
                boardJ >= 0 && boardJ < BOARD_SIZE &&
                board[boardI][boardJ].type == ' ') {

                Texture selectedTexture = odsTextures.random();
                if (selectedTexture != null && selectedTexture.isManaged()) {
                    board[boardI][boardJ].type = 'O';
                    board[boardI][boardJ].odsTexture = selectedTexture;
                }

                if (checkWin(boardI, boardJ)) {
                    gameOver = true;
                    winner = "Jugador";
                } else if (checkDraw()) {
                    gameOver = true;
                    winner = null;
                } else {
                    isPlayerTurn = false;
                }
            }
        }

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.R)) {
            initGame();
        }
    }

    private void makeComputerMove() {
        // Encontrar casillas vacias
        Array<int[]> emptyCells = new Array<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j].type == ' ') {
                    emptyCells.add(new int[]{i, j});
                }
            }
        }

        if (emptyCells.size > 0) {
            // Seleccionar una casilla vacía al azar
            int[] move = emptyCells.random();
            board[move[0]][move[1]].type = 'X';

            if (checkWin(move[0], move[1])) {
                gameOver = true;
                winner = "Computadora";
            } else if (checkDraw()) {
                gameOver = true;
                winner = null;
            } else {
                isPlayerTurn = true;
            }
        }
    }

    private boolean checkWin(int row, int col) {
        char player = board[row][col].type;

        // Verificar fila
        boolean win = true;
        for (int j = 0; j < BOARD_SIZE; j++) {
            if (board[row][j].type != player) {
                win = false;
                break;
            }
        }
        if (win) return true;

        // Verificar columna
        win = true;
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][col].type != player) {
                win = false;
                break;
            }
        }
        if (win) return true;

        // Verificar diagonal principal
        if (row == col) {
            win = true;
            for (int i = 0; i < BOARD_SIZE; i++) {
                if (board[i][i].type != player) {
                    win = false;
                    break;
                }
            }
            if (win) return true;
        }

        // Verificar diagonal secundaria
        if (row + col == BOARD_SIZE - 1) {
            win = true;
            for (int i = 0; i < BOARD_SIZE; i++) {
                if (board[i][BOARD_SIZE - 1 - i].type != player) {
                    win = false;
                    break;
                }
            }
            if (win) return true;
        }

        return false;
    }

    private boolean checkDraw() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j].type == ' ') return false;
            }
        }
        return true;
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
        boardX = (width - (CELL_SIZE * BOARD_SIZE)) / 2;
        boardY = (height - (CELL_SIZE * BOARD_SIZE)) / 2;
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
        font.dispose();
        backgroundTexture.dispose();
        for (Texture texture : odsTextures) {
            texture.dispose();
        }
    }
}
