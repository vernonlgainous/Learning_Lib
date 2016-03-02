package com.myandroid.tutorial.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.AtlasTmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.myandroid.tutorial.game.MyAndroidTutorialGame;
import com.myandroid.tutorial.game.Scenes.Hud;

/**
 * Created by vgainous on 3/2/2016.
 */
public class PlayScreen implements Screen{
    private MyAndroidTutorialGame game;
    private Hud hud;
    private OrthographicCamera gamecam;
    private Viewport gamePort;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    public PlayScreen(MyAndroidTutorialGame game){
        this.game = game;
        //camera that will follow the player throughout the world.
        gamecam = new OrthographicCamera();
        //create fitViewport to maintain virtual aspect ratio despite varying screen sizes
        gamePort = new FitViewport(MyAndroidTutorialGame.V_WIDTH,MyAndroidTutorialGame.V_HEIGHT,gamecam);
        //game hud for score, lives, time, etc.
        hud = new Hud(game.batch);
        //loads the tmx map into the project and draws it to the screen
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gamecam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);


    }

    @Override
    public void show() {

    }

    public void handleInput(float dt){
        if (Gdx.input.isTouched()){
            gamecam.position.x += 100 *dt;
        }
    }

    public void update(float dt){
        handleInput(dt);
        gamecam.update();
        //this will only render what the gamecam can see.
        renderer.setView(gamecam);
    }

    //this render method is the only method that is being called over and over
    @Override
    public void render(float delta) {
        update(delta);

        //first thing in render method is to clear the screen
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
