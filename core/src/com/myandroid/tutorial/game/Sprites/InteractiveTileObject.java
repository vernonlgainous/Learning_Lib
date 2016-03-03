package com.myandroid.tutorial.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.myandroid.tutorial.game.MyAndroidTutorialGame;

/**
 * Created by vgainous on 3/3/2016.
 */
public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;

    public InteractiveTileObject(World world, TiledMap map, Rectangle bounds){
        this.world = world;
        this.map = map;
        this.bounds = bounds;

        //to create a box2d
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();


        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX()+bounds.getWidth()/2)/ MyAndroidTutorialGame.PPM, (bounds.getY()+bounds.getHeight()/2)/MyAndroidTutorialGame.PPM);

        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth()/2/MyAndroidTutorialGame.PPM, bounds.getHeight()/2/MyAndroidTutorialGame.PPM);
        fdef.shape = shape;
        body.createFixture(fdef);
    }

}
