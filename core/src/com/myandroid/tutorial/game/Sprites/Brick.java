package com.myandroid.tutorial.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.myandroid.tutorial.game.MyAndroidTutorialGame;
import com.myandroid.tutorial.game.Scenes.Hud;
import com.myandroid.tutorial.game.Screens.PlayScreen;

/**
 * Created by vgainous on 3/3/2016.
 */
public class Brick extends InteractiveTileObject{

    public Brick(PlayScreen screen, Rectangle bounds){
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(MyAndroidTutorialGame.BRICK_BIT);

    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick", "collision");
        setCategoryFilter(MyAndroidTutorialGame.DESTROYED_BIT);
        getCell().setTile(null);
        Hud.addScore(200);
        Sound sound = MyAndroidTutorialGame.manager.get("audio/sounds/breakblock.wav", Sound.class);
        sound.play();
    }
}
