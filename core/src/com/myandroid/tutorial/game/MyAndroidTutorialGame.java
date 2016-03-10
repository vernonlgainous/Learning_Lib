package com.myandroid.tutorial.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.myandroid.tutorial.game.Screens.PlayScreen;

import java.awt.Menu;
import java.util.Iterator;


public class MyAndroidTutorialGame extends Game {
    public static final int V_WIDTH=400;
    public static final int V_HEIGHT=208;
	public SpriteBatch batch;
    public static final float PPM = 100;

    public static final short GROUND_BIT=1;
    public static final short HERO_BIT = 2;
    public static final short BRICK_BIT=4;
    public static final short COIN_BIT=8;
    public static final short DESTROYED_BIT=16;
    public static final short OBJECT_BIT=32;
    public static final short ENEMY_BIT=64;

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
	*/

    /*In tiled use TileLayers to make the level and ObjectLayers to make things you will reference programatically.
    Use "Ctrl + click" when drawing an object layer to make it snap to the grid.
    Get tiles from www.spriters-resource.com/nes/supermariobros/sheet/52571/
    Get Textures for sprite sheets at libgdx-texturepacker-gui
     */

    /*
    Don't use a static AssetManager it can cause issues on Android instead pass around the AssetManager to the
    classes that need it.
     */

    public static AssetManager manager;

    @Override
    public void create (){
        batch = new SpriteBatch();

        manager = new AssetManager();
        manager.load("audio/music/mario_music.ogg", Music.class);
        manager.load("audio/sounds/coin.wav", Sound.class);
        manager.load("audio/sounds/bump.wav", Sound.class);
        manager.load("audio/sounds/breakblock.wav", Sound.class);
        manager.finishLoading();

        setScreen(new PlayScreen(this));
    }


	@Override
	public void render () {
        super.render();

	}

    @Override
    public void dispose(){
        super.dispose();
        manager.dispose();
        batch.dispose();
    }
}
