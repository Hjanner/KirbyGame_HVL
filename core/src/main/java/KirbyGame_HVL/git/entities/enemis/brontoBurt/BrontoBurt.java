package KirbyGame_HVL.git.entities.enemis.brontoBurt;

import KirbyGame_HVL.git.Main;
import KirbyGame_HVL.git.entities.States.EnumStateEnemy;
import KirbyGame_HVL.git.entities.States.State;
import KirbyGame_HVL.git.entities.States.StateManager;
import KirbyGame_HVL.git.entities.States.StatesWaddleDee.AtractStateWaddleDee;
import KirbyGame_HVL.git.entities.States.statesBrontoBurt.AtractStateBrontoBurt;
import KirbyGame_HVL.git.entities.States.statesBrontoBurt.Die2StateBrontoBurt;
import KirbyGame_HVL.git.entities.States.statesBrontoBurt.DieStateBrontoBurt;
import KirbyGame_HVL.git.entities.States.statesBrontoBurt.FlyStateBrontoBurt;
import KirbyGame_HVL.git.entities.enemis.Enemy;
import KirbyGame_HVL.git.entities.enemis.EnumEnemyType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.MathUtils;

public class BrontoBurt extends Enemy {

// Atributos
    // Texturas y animaciones del BrontoBurt
    private Texture brontoBurtFlyTexture;
    private TextureRegion brontoBurtFlyRegion;
    private TextureRegion[] brontoBurtFlyFrames;
    private Texture brontoBurtDieTexture;
    private TextureRegion brontoBurtDieRegion;
    private TextureRegion[] brontoBurtDieFrames;
    private Animation flyAnimation;
    private Animation dieAnimation;
    private Animation currentAnimation;
    private Sprite brontoBurtSprite;

    private float duration = 0;
    private float startY;

    // Estados del BrontoBurt
    private StateManager stateManager;
    private FlyStateBrontoBurt flyBrontoBurt;
    private DieStateBrontoBurt dieBrontoBurt;
    private AtractStateBrontoBurt atractBrontoBurt;
    private Die2StateBrontoBurt die2BrontoBurt;

    // Bandera para el dispose
    private boolean isDisposed = false;

    // Constructor
    public BrontoBurt(World world, Main main, float x, float y) {
        this.world = world;
        this.main = main;
        this.flipX = false;
        this.stateManager = new StateManager();
        this.flyBrontoBurt = new FlyStateBrontoBurt(this);
        this.dieBrontoBurt = new DieStateBrontoBurt(this);
        this.atractBrontoBurt = new AtractStateBrontoBurt(this);
        this.die2BrontoBurt = new Die2StateBrontoBurt(this);
        this.stateManager.setState(flyBrontoBurt);
        this.startY = y;
        this.type = EnumEnemyType.BRONTO;
        createBody(world, x, y);
        loadTextures();
    }

    // Setters y Getters

    public World getWorld() {
        return this.world;
    }

    public float getStartY() {
        return startY;
    }

    @Override
    public void setFlipX(boolean flipX) {
        this.flipX = flipX;
    }

    @Override
    public boolean getFlipX() {
        return flipX;
    }

    public void setAnimation(EnumStateEnemy typeState) {
        switch (typeState) {
            case FLY:
                currentAnimation = flyAnimation;
                break;
            case DIE:
                currentAnimation = dieAnimation;
                break;
            default:
                break;
        }
    }

    @Override
    public void setState(EnumStateEnemy typeState) {
        switch (typeState) {
            case FLY:
                stateManager.setState(flyBrontoBurt);
                break;
            case DIE:
                stateManager.setState(dieBrontoBurt);
                break;
            case ATRACT:
                stateManager.setState(atractBrontoBurt);
                break;
            case DIE2:
                stateManager.setState(die2BrontoBurt);
                break;
            default:
                break;
        }
    }

    // Creamos el cuerpo del BrontoBurt
    @Override
    public void createBody(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(6f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.1f;

        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        body.setFixedRotation(true);
        shape.dispose();
    }

    // Cargamos las texturas y animaciones
    private void loadTextures() {
        brontoBurtFlyTexture = main.getManager().get("assets/art/sprites/spritesBrontoBurt/BrontoBurtFly.png");
        brontoBurtFlyRegion = new TextureRegion(brontoBurtFlyTexture, 96, 32);
        brontoBurtDieTexture = main.getManager().get("assets/art/sprites/spritesBrontoBurt/BrontoBurtDie.png");
        brontoBurtDieRegion = new TextureRegion(brontoBurtDieTexture, 32, 32);

        TextureRegion[][] tempFrames = brontoBurtFlyRegion.split(96/3, 32);
        brontoBurtFlyFrames = new TextureRegion[tempFrames.length * tempFrames[0].length];
        brontoBurtDieFrames = new TextureRegion[1];
        brontoBurtDieFrames[0] = brontoBurtDieRegion;

        int index = 0;
        for (int i = 0; i < tempFrames.length; i++) {
            for (int j = 0; j < tempFrames[i].length; j++) {
                brontoBurtFlyFrames[index++] = tempFrames[i][j];
            }
        }

        flyAnimation = new Animation(0.1f, brontoBurtFlyFrames);
        dieAnimation = new Animation(1, brontoBurtDieFrames);
        brontoBurtSprite = new Sprite(brontoBurtFlyFrames[0]);
        brontoBurtSprite.setSize(24, 24);                               //ajusta el tamano de sprite
        currentAnimation = flyAnimation;
    }

    // Dibujamos el BrontoBurt
    @Override
    public void draw(Batch batch, float parentAlpha) {
        brontoBurtSprite.setPosition(body.getPosition().x - 12, body.getPosition().y - 6);
        brontoBurtSprite.draw(batch);
    }

    // Actualizamos el BrontoBurt
    @Override
    public void act(float delta) {
        if (isDisposed) {
            return;
        }
        super.act(delta);
        this.stateManager.update(delta);
        updateAnimation(delta);
    }

    // Actualizamos la animacion del BrontoBurt
    @Override
    public void updateAnimation(float delta) {
        duration += delta;
        TextureRegion frame = (TextureRegion) currentAnimation.getKeyFrame(duration, true);
        brontoBurtSprite.setRegion(frame);
        brontoBurtSprite.setFlip(flipX, false);
    }

    // Eliminamos cualquier tipo de residuo
    public void dispose() {
        if (!isDisposed) {
            isDisposed = true;
            this.remove();
        }
    }
}
