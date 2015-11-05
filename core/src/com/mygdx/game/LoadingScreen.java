package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Владислав on 02.11.2015.
 */
public class LoadingScreen extends ScreenAdapter {
    private static final float WORLD_WIDTH = 600;
    private static final float WORLD_HEIGHT = 800;
    private static final float PROGRESS_BAR_WIDTH = 100;
    private static final float PROGRESS_BAR_HEIGHT = 25;
    private ShapeRenderer shapeRenderer;
    private Viewport viewport;
    private Camera camera;
    private float progress = 0;
    private final SheepGame sheepGame;

    public LoadingScreen(SheepGame sheepGame){
        this.sheepGame = sheepGame;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void show(){
        camera = new OrthographicCamera();
        camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2, 0);
        camera.update();
        viewport = new FillViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        shapeRenderer = new ShapeRenderer();
        sheepGame.getAssetManager().load("field.jpg", Texture.class);
        sheepGame.getAssetManager().load("cloud.png", Texture.class);
        sheepGame.getAssetManager().load("grey.png", Texture.class);
        sheepGame.getAssetManager().load("red.png", Texture.class);
        sheepGame.getAssetManager().load("blue.png", Texture.class);
        sheepGame.getAssetManager().load("sun.png", Texture.class);
        sheepGame.getAssetManager().load("middle.png", Texture.class);
        sheepGame.getAssetManager().load("right.png", Texture.class);
        sheepGame.getAssetManager().load("left.png", Texture.class);
    }

    @Override
    public void render(float delta){
        update();
        clearScreen();
        draw();
    }

    @Override
    public void dispose(){
        shapeRenderer.dispose();
    }

    private void update(){
        if(sheepGame.getAssetManager().update()){
            sheepGame.setScreen(new GameScreen(sheepGame));
        } else {
            progress = sheepGame.getAssetManager().getProgress();
        }
    }

    private void clearScreen(){
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void draw(){
        shapeRenderer.setProjectionMatrix(camera.projection);
        shapeRenderer.setTransformMatrix(camera.view);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect((WORLD_WIDTH - PROGRESS_BAR_WIDTH) / 2, (WORLD_HEIGHT - PROGRESS_BAR_HEIGHT) / 2, progress * PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT);
        shapeRenderer.end();
    }
}
