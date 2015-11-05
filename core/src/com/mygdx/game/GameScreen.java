package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

/**
 * Created by Владислав on 01.11.2015.
 */
public class GameScreen extends ScreenAdapter {
    private static final float WORLD_WIDTH = 600;
    private static final float WORLD_HEIGHT = 800;
    private static int gateState = 0;
    private int score = 0;
    private boolean wasScored = false;
    private boolean allowed = false;

    //Other
    private ShapeRenderer shapeRenderer;
    private Viewport viewport;
    private Camera camera;
    private SpriteBatch batch;
    private final SheepGame sheepGame;
    private AssetManager assetManager;
    private Random random = new Random();
    private BitmapFont bitmapFont;
    private GlyphLayout glyphLayout;

    //Sprites
    private Sprite sunSprite;
    private Sprite backSprite;
    private Sprite sheepRedSprite;
    private Sprite sheepGreySprite;
    private Sprite sheepBlueSprite;
    private Sprite cloudSprite;
    private Sprite middle;
    private Sprite right;
    private Sprite left;

    //Textures
    private Texture sheepRedTexture;
    private Texture sheepGreyTexture;
    private Texture sheepBlueTexture;
    private Texture background;
    private Texture cloudTexture;
    private Texture sunTexture;
    private Texture middleTexture;
    private Texture rightTexture;
    private Texture leftTexture;

    //Objects
    private Sheep sheep;
    private Sun sun;
    private Cloud cloud1;
    private Cloud cloud2;
    private Gates gates;

    public GameScreen(SheepGame sheepGame){
        this.sheepGame = sheepGame;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void show() {
        assetManager = sheepGame.getAssetManager();
        camera = new OrthographicCamera();
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();
        viewport = new FillViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();

        sheepRedTexture = assetManager.get("red.png");
        sheepRedTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sheepRedSprite = new Sprite(sheepRedTexture);
        sheepRedSprite.setSize(70, 70);

        sheepGreyTexture = assetManager.get("grey.png");
        sheepGreyTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sheepGreySprite = new Sprite(sheepGreyTexture);
        sheepGreySprite.setSize(70, 70);

        sheepBlueTexture = assetManager.get("blue.png");
        sheepBlueTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sheepBlueSprite = new Sprite(sheepBlueTexture);
        sheepBlueSprite.setSize(70, 70);

        sheep = new Sheep(sheepGreySprite);
        sheep.setIndicator(1);
        sheep.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT * 1.5f);

        cloudTexture = assetManager.get("cloud.png");
        cloudSprite = new Sprite(cloudTexture);
        cloudSprite.setSize(200, 125);
        cloud1 = new Cloud(cloudSprite, 1 + random.nextInt(1));
        cloud1.setPosition(WORLD_WIDTH * 1.25f, 600 + random.nextInt(100));
        cloud2 = new Cloud(cloudSprite,1 + random.nextInt(2));
        cloud2.setPosition(WORLD_WIDTH * 1.50f, 600 + random.nextInt(100));

        background = assetManager.get("field.jpg");
        backSprite = new Sprite(background);
        backSprite.setSize(WORLD_WIDTH, WORLD_HEIGHT);

        sunTexture = assetManager.get("sun.png");
        sunSprite = new Sprite(sunTexture);
        sunSprite.setSize(100, 100);
        sun = new Sun(sunSprite);
        sun.setPosition(0.75f * WORLD_WIDTH, 0.85f * WORLD_HEIGHT);

        leftTexture = assetManager.get("left.png");
        rightTexture = assetManager.get("right.png");
        middleTexture = assetManager.get("middle.png");
        left = new Sprite(leftTexture);
        left.setSize(WORLD_WIDTH, WORLD_HEIGHT/2.5f);
        right = new Sprite(rightTexture);
        right.setSize(WORLD_WIDTH, WORLD_HEIGHT/2.5f);
        middle = new Sprite(middleTexture);
        middle.setSize(WORLD_WIDTH, WORLD_HEIGHT/2.5f);

        gates = new Gates(middle);
        gates.setPosition(0, 0);

        bitmapFont = new BitmapFont();
        bitmapFont.getData().setScale(5,5);
        glyphLayout = new GlyphLayout();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
        backSprite.draw(batch);
        sheep.draw(batch);
        sun.draw(batch);
        cloud1.draw(batch);
        cloud2.draw(batch);
        gates.draw(batch);
        drawScore();
        batch.end();
        update(delta);
    }

    private void update(float delta) {
        shift();
        sheep.update();
        cloud1.update();
        cloud2.update();
        checkSheep();
        checkCloud();
        checkGate();
    }

    private void checkSheep(){
        if(sheep.getY() < WORLD_HEIGHT/9){
            if((sheep.getIndicator() == gateState) && !wasScored) {
                score++;
                wasScored = true;
                allowed = true;
            }
        }
        if(sheep.getY() <  0) {
            sheep.resetAcceleration();
            int i = random.nextInt(3);
            switch (i){
                case 0:{
                    sheep.setSheepSprite(sheepBlueSprite);
                    sheep.setIndicator(0);
                    break;
                }
                case 1:{
                    sheep.setSheepSprite(sheepGreySprite);
                    sheep.setIndicator(1);
                    break;
                }

                case 2:{
                    sheep.setSheepSprite(sheepRedSprite);
                    sheep.setIndicator(2);
                    break;
                }
            }
            sheep.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT * 1.5f);
            wasScored = false;
            allowed = false;
        }
    }

    public void shift(){
        if(gateState == sheep.getIndicator() && gateState == 0 && allowed){
            sheep.shiftLeft();
        }
        if(gateState == sheep.getIndicator() && gateState == 2 && allowed){
            sheep.shiftRight();
        }
    }

    private void checkCloud(){
        if(cloud1.getX() < -150){
            cloud1.resetCloud();
            cloud1.setPosition(WORLD_WIDTH * 1.25f, 600 + random.nextInt(100));
            //cloud1.setCloudSpeed(1 + random.nextInt(1));
        }
        if(cloud2.getX() < -150){
            cloud2.resetCloud();
            cloud2.setPosition(WORLD_WIDTH * 1.75f, 600 + random.nextInt(100));
            //cloud2.setCloudSpeed(1 + random.nextInt(2));
        }
    }

    private void checkGate(){
        if(Gdx.input.isTouched()){
            int x = Gdx.input.getX();
            if(x > Gdx.graphics.getWidth()/2){
                gateState = 2;
                gates.setGate(right);
            }
            else{
                gateState = 0;
                gates.setGate(left);
            }
        }
        else {
            gateState = 1;
            gates.setGate(middle);
        }
    }

    public void drawScore(){
        String scoreAsString = Integer.toString(score);
        glyphLayout.setText(bitmapFont, scoreAsString);
        bitmapFont.draw(batch, scoreAsString, WORLD_WIDTH/6, WORLD_HEIGHT*0.95f);
    }
}
