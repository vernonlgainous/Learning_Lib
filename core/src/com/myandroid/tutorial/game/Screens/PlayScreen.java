package com.myandroid.tutorial.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.AtlasTmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.myandroid.tutorial.game.MyAndroidTutorialGame;
import com.myandroid.tutorial.game.Scenes.Hud;
import com.myandroid.tutorial.game.Scenes.TouchController;
import com.myandroid.tutorial.game.Sprites.Goomba;
import com.myandroid.tutorial.game.Sprites.Hero;
import com.myandroid.tutorial.game.Tools.B2WorldCreator;
import com.myandroid.tutorial.game.Tools.WorldContactListener;

import sun.rmi.runtime.Log;

/**
 * Created by vgainous on 3/2/2016.
 */
public class PlayScreen implements Screen{
    private MyAndroidTutorialGame game;

    private TextureAtlas atlas;

    private Hud hud;
    private OrthographicCamera gamecam;
    private Viewport gamePort;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;

    private Hero player;
    private Goomba goomba;

    private OrthographicCamera guicam;
    Rectangle wLeftBounds;
    Rectangle wRightBounds;
    Rectangle wUpBounds;

    private Music music;


    public PlayScreen(MyAndroidTutorialGame game){
        atlas = new TextureAtlas("Mario_and_Enemies.pack");

        this.game = game;
        //camera that will follow the player throughout the world.
        gamecam = new OrthographicCamera();
        //create fitViewport to maintain virtual aspect ratio despite varying screen sizes added this /MyAndroidTutorialGame.PPM to fix a pixels per meter scaling issue since libGdx is in the metric system
        gamePort = new FitViewport(MyAndroidTutorialGame.V_WIDTH/MyAndroidTutorialGame.PPM, MyAndroidTutorialGame.V_HEIGHT/MyAndroidTutorialGame.PPM, gamecam);
        //game hud for score, lives, time, etc.
        hud = new Hud(game.batch);
        //loads the tmx map into the project and draws it to the screen
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/MyAndroidTutorialGame.PPM);
        gamecam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);


        //Just messing around with input for the game adding simple left and right control rectangles
        guicam = new OrthographicCamera(MyAndroidTutorialGame.V_WIDTH,MyAndroidTutorialGame.V_HEIGHT);
        guicam.position.set(MyAndroidTutorialGame.V_WIDTH/2f, MyAndroidTutorialGame.V_HEIGHT/2f, 0);
        wLeftBounds = new Rectangle(40,40,210,700);
        wRightBounds = new Rectangle(280,50,210,700);
        wUpBounds = new Rectangle(1000,50, 1230,700);


        //first param is a vector for gravity
        //second is boolean sleep objects at rest
        world = new World(new Vector2(0,-10), true);
        b2dr = new Box2DDebugRenderer();

        //new B2WorldCreator(world, map);
        new B2WorldCreator(this);

        //create the hero in the game world
        player = new Hero(this);

        world.setContactListener(new WorldContactListener());

        music = MyAndroidTutorialGame.manager.get("audio/music/mario_music.ogg", Music.class);
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();

        goomba = new Goomba(this, .32f, .32f);
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt){
        //you must use the getWorldCenter or you could cause the physics body to spin on some axis when a force/impulse is applied.
        //the true at the end is a way of telling the box2d object to wake up
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            player.b2body.applyLinearImpulse(new Vector2(0,4f), player.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <=2){
            player.b2body.applyLinearImpulse(new Vector2(0.1f,0), player.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >=-2){
            player.b2body.applyLinearImpulse(new Vector2(-0.1f,0), player.b2body.getWorldCenter(), true);
        }
        //a single touch is the same as a left button click

        for (int i = 0; i< 20; i++) { //this for loop is for handling multi-touch where 20 is the max number of touch points.
            if (Gdx.input.isTouched(i)) {
                Gdx.app.log("INFO", "The touch point is " + Gdx.input.getX() + "," + Gdx.input.getY());
                if (wLeftBounds.contains(Gdx.input.getX(i), Gdx.input.getY(i)) && player.b2body.getLinearVelocity().x >= -1.1)
                    player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
                if (wRightBounds.contains(Gdx.input.getX(i), Gdx.input.getY(i)) && player.b2body.getLinearVelocity().x <= 1.1)
                    player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
                if (wUpBounds.contains(Gdx.input.getX(i), Gdx.input.getY(i)) && player.b2body.getLinearVelocity().y <= 1.3f)
                    player.b2body.applyLinearImpulse(new Vector2(0, 0.4f), player.b2body.getWorldCenter(), true);
            }
        }
        MassData massData = new MassData();
        massData.mass = 0.2f;
        player.b2body.setMassData(massData);

    }

    public void update(float dt){
        handleInput(dt);

        //set how often the world will refresh
        world.step(1/60f, 6, 2);

        //this is part of the work to add the sprite to the box2dBody
        player.update(dt);
        goomba.update(dt);
        hud.update(dt);

        //makes the camera follow the player
        gamecam.position.x = player.b2body.getPosition().x;

        gamecam.update();

        //this will only render what the gamecam can see.
        renderer.setView(gamecam);
    }

    //this render method is the only method that is being called over and over
    @Override
    public void render(float delta) {
        update(delta);

        //first thing in render method is to clear the screen
        Gdx.gl.glClearColor(10/255f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        goomba.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);

    }

    public TiledMap getMap(){
        return map;
    }
    public World getWorld(){
        return world;
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();

    }
}
