package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class Unit extends Actor {
    private final Texture unit;
    private int health;
    private final int dmg;
    private final int speed = 80;
    private boolean isTouched = false;


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(unit, getX(), getY(), 80, 80);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public Unit(Texture unit, int health, int dmg, int x, int y) {
        this.unit = unit;
        this.health = health;
        this.dmg = dmg;
        setBounds(x, y, 80, 80);
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isTouched = true;
            }
        });
    }

    public Texture getUnit() {
        return unit;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDmg() {
        return dmg;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean getTouched() {
        return isTouched;
    }

    public void setTouched(boolean touched) {
        isTouched = touched;
    }
}
