package com.myandroid.tutorial.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by vgainous on 3/3/2016.
 */
public class Brick extends InteractiveTileObject{

    public Brick(World world, TiledMap map, Rectangle bounds){
        super(world, map, bounds);

    }
}
