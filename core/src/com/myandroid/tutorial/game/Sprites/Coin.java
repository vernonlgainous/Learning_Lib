package com.myandroid.tutorial.game.Sprites;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.myandroid.tutorial.game.MyAndroidTutorialGame;

/**
 * Created by vgainous on 3/3/2016.
 */
public class Coin extends InteractiveTileObject{

    public Coin (World world, TiledMap map, Rectangle bounds){
        super(world, map, bounds);


    }
}
