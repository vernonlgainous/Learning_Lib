package com.myandroid.tutorial.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.myandroid.tutorial.game.MyAndroidTutorialGame;
import com.myandroid.tutorial.game.Scenes.Hud;
import com.myandroid.tutorial.game.Screens.PlayScreen;

/**
 * Created by vgainous on 3/3/2016.
 */
public class Coin extends InteractiveTileObject{
    private static TiledMapTileSet tileSet;
    //in Tiled go to the specific tile and view its properties it has id of 27
    //Tiled starts counting at 0 while LibGDX starts at 1 so use 28
    private final int BLANK_COIN = 28;
    public Coin (PlayScreen screen, Rectangle bounds){
        super(screen, bounds);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(MyAndroidTutorialGame.COIN_BIT);

    }

    @Override
    public void onHeadHit() {

        Gdx.app.log("Coin", "collision");
        if (getCell().getTile().getId() == BLANK_COIN){
            Sound sound = MyAndroidTutorialGame.manager.get("audio/sounds/bump.wav", Sound.class);
            sound.play();

        }else{
            Sound sound = MyAndroidTutorialGame.manager.get("audio/sounds/coin.wav", Sound.class);
            sound.play();
        }
        getCell().setTile(tileSet.getTile(BLANK_COIN));
        Hud.addScore(100);
    }
}
