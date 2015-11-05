package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;

/**
 * Created by Владислав on 02.11.2015.
 */
public class StartScreen extends ScreenAdapter {
    private static final float WORLD_WIDTH = 600;
    private static final float WORLD_HEIGHT = 800;

    private Stage stage;

    private Texture backgroundTexture;
    private Texture playTexture;
    private Texture playPressTexture;
    private Texture titleTexture;
    private final Game game;

    public StartScreen(Game game){
        this.game = game;
    }

    public void show(){
        stage = new Stage(new FillViewport(WORLD_WIDTH, WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        backgroundTexture = new Texture(Gdx.files.internal("field.jpg"));
        Image background = new Image(backgroundTexture);

        background.setSize(WORLD_WIDTH, WORLD_HEIGHT);
        stage.addActor(background);

        playTexture = new Texture(Gdx.files.internal("sheep.png"));
        playPressTexture = new Texture(Gdx.files.internal("cloud.png"));
        ImageButton play = new ImageButton(new TextureRegionDrawable(new TextureRegion(playTexture)), new TextureRegionDrawable(new TextureRegion(playPressTexture)));
        play.setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, Align.center);
        play.addListener(new ActorGestureListener(){
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);
                game.setScreen(new LoadingScreen((SheepGame) game));
                dispose();
            }
        });
        stage.addActor(play);

        titleTexture = new Texture(Gdx.files.internal("sun.png"));
        Image title = new Image(titleTexture);
        title.setPosition(WORLD_WIDTH/2, 3*WORLD_HEIGHT/4, Align.center);
        stage.addActor(title);
    }

    public void resize(int width, int height){
        stage.getViewport().update(width, height, true);
    }

    public void render(float delta){
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose(){
        stage.dispose();
        backgroundTexture.dispose();
        playPressTexture.dispose();
        playTexture.dispose();
        titleTexture.dispose();
    }
}
