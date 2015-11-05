package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

/**
 * Created by Владислав on 01.11.2015.
 */
public class Sun {
    private float x = 0;
    private float y = 0;
    private final Circle sunCircle;
    private final float sunRadius = 50f;
    private Sprite sprite;

    public Sun(Sprite sprite){
        this.sprite = sprite;
        sunCircle = new Circle(x, y, sunRadius);
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
        updateSun();
    }

    private void updateSun() {
        sunCircle.setX(x);
        sunCircle.setY(y);
    }

    public void drawDebug(ShapeRenderer shapeRenderer){
        shapeRenderer.circle(sunCircle.x, sunCircle.y, sunCircle.radius);
    }

    public void draw(SpriteBatch batch){
        sprite.setPosition(sunCircle.x - sprite.getWidth()/2, sunCircle.y - sprite.getHeight()/2);
        sprite.draw(batch);
    }
}
