package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Владислав on 05.11.2015.
 */
public class Gates {
    private float x;
    private float y;
    private Sprite gate;

    public Gates(Sprite gate){
        x = 0;
        y = 0;
        this.gate = gate;
    }

    public void draw(SpriteBatch batch){
        gate.setPosition(this.x, this.y);
        gate.draw(batch);
    }

    public void setGate(Sprite gate){
        this.gate = gate;
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }
}
