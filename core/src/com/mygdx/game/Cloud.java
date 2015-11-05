package com.mygdx.game;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Владислав on 01.11.2015.
 */
public class Cloud {
    private float x = 0;
    private float y = 0;
    private float cloudSpeed = 0.02f;
    private float xSpeed = 0;
    private final Rectangle cloudRectangle;
    public static final float DISTANCE = 100f;
    private Sprite cloudSprite;

    public void setCloudSpeed(float cloudSpeed) {
        this.cloudSpeed = cloudSpeed;
    }

    public Cloud(Sprite cloud, float cloudSpeed){
        cloudSprite = cloud;
        cloudRectangle = new Rectangle(x, y, 100, 50);
    }

    public void drawDebug(ShapeRenderer shapeRenderer){
        shapeRenderer.rect(cloudRectangle.x, cloudRectangle.y, cloudRectangle.width, cloudRectangle.height);
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
        updateCloud();
    }

    private void updateCloud() {
        cloudRectangle.setX(x);
        cloudRectangle.setY(y);
    }

    public void update(){
        //cloudSpeed -= acc;
        xSpeed -= cloudSpeed;
        setPosition(x+xSpeed, y);
    }

    public void resetCloud(){
        xSpeed = 0;
    }

    public float getX() {
        return x;
    }

    public void draw(SpriteBatch batch){
        cloudSprite.setPosition(cloudRectangle.x, cloudRectangle.y);
        cloudSprite.draw(batch);
    }
}
