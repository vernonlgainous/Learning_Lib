package com.myandroid.tutorial.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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

    @Override
    public void create (){
        batch = new SpriteBatch();
        setScreen(new PlayScreen(this));
    }


	@Override
	public void render () {
        super.render();
	}
}
