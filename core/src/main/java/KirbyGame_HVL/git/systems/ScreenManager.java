//package KirbyGame_HVL.git.systems;
//
//import KirbyGame_HVL.git.screens.gameplay.GameScreen;
//import KirbyGame_HVL.git.Main;
//
//public class ScreenManager {
//    private Main main;
//    private GameScreen currentGameScreen;
//
//    public ScreenManager(Main main) {
//        this.main = main;
//    }
//
//    public void startNewGame() {
//        if (currentGameScreen != null) {
//            cleanupCurrentScreen();
//        }
//        currentGameScreen = new GameScreen(main);
//        main.setScreen(currentGameScreen);
//    }
//
//    public void switchToMinigame() {
//        // Don't dispose the current screen yet, just pause it
//        main.setScreen(main.gameCulebrita);
//    }
//
//    public void returnFromMinigame(int score) {
//        // Cleanup old screen and create fresh instance
//        cleanupCurrentScreen();
//        System.out.println("hola");
//        currentGameScreen = new GameScreen(main);
//        currentGameScreen.getKirby().setCurrentScore(score);
//        main.setScreen(currentGameScreen);
//        System.out.println("hola1");
//    }
//
//    private void cleanupCurrentScreen() {
//        if (currentGameScreen != null) {
//            currentGameScreen.dispose();
//            currentGameScreen = null;
//        }
//    }
//
//    public GameScreen getCurrentGameScreen() {
//        return currentGameScreen;
//    }
//}
