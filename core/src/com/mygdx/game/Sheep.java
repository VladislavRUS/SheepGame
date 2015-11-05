package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

/**
 * Created by Владислав on 01.11.2015.
 */
public class Sheep {
    private float x = 0;
    private float y = 0;

    private int indicator;
    private float ACCEL = 0.30f;
    private float ySpeed = 0;
    private Sprite sheepSprite;

    public Sheep(Sprite sheepTexture){
        sheepSprite = sheepTexture;
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }

    public int getIndicator() {
        return indicator;
    }

    public void update(){
        ySpeed -= ACCEL;
        setPosition(x, y + ySpeed);
    }

    public void shiftLeft(){
            x -= 20;
    }

    public void shiftRight(){
            x += 20;
    }

    public void setSheepSprite(Sprite sheep){
        sheepSprite = sheep;
    }

    public void setIndicator(int indicator){
        this.indicator = indicator;
    }

    public float getY() {
        return y;
    }

    public void resetAcceleration(){
        ySpeed = 0;
    }

    public void draw(SpriteBatch batch){
        sheepSprite.setPosition(x - sheepSprite.getWidth()/2, y - sheepSprite.getHeight()/2);
        sheepSprite.draw(batch);
    }
}
