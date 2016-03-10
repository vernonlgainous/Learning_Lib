package com.myandroid.tutorial.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.myandroid.tutorial.game.MyAndroidTutorialGame;
import com.myandroid.tutorial.game.Screens.PlayScreen;
import com.myandroid.tutorial.game.Sprites.Brick;
import com.myandroid.tutorial.game.Sprites.Coin;

/**
 * Created by vgainous on 3/3/2016.
 */
public class B2WorldCreator {
    public B2WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;


        //create ground bodies/fixtures
        //to get the index for layers open Tiled and in the layers tab start from 0 count from the bottom up
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/ MyAndroidTutorialGame.PPM, (rect.getY()+rect.getHeight()/2)/MyAndroidTutorialGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2/MyAndroidTutorialGame.PPM, rect.getHeight()/2/MyAndroidTutorialGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //create pipe bodies/fixtures
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/MyAndroidTutorialGame.PPM, (rect.getY()+rect.getHeight()/2)/MyAndroidTutorialGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2/MyAndroidTutorialGame.PPM, rect.getHeight()/2/MyAndroidTutorialGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //create brick bodies/fixtures
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Brick(screen, rect);
        }

        //create coin bodies/fixtures
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Coin(screen, rect);
        }
    }
}
