package com.myandroid.tutorial.game.Sprites;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.bullet.softbody.btSoftBody;
import com.myandroid.tutorial.game.Screens.PlayScreen;

/**
 * Created by vgainous on 3/10/2016.
 */
public abstract class Enemy extends Sprite {
    protected World world;
    protected Screen screen;
    public Body b2body;

    public Enemy(PlayScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineEnemy();
    }

    protected abstract void defineEnemy();

}
