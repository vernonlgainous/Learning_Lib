package com.myandroid.tutorial.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.myandroid.tutorial.game.MyAndroidTutorialGame;

/**
 * Created by vgainous on 3/3/2016.
 */
public class Brick extends InteractiveTileObject{

    public Brick(World world, TiledMap map, Rectangle bounds){
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(MyAndroidTutorialGame.BRICK_BIT);

    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick", "collision");
        setCategoryFilter(MyAndroidTutorialGame.DESTROYED_BIT);
        getCell().setTile(null);
    }
}
