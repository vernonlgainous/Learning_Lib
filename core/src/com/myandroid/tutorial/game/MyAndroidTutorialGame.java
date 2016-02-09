package com.myandroid.tutorial.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.awt.Menu;
import java.util.Iterator;


public class MyAndroidTutorialGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
    private Texture bucketImage;
    private Texture dropImage;
    private Sound dropSound;
    private Music rainMusic;
    private OrthographicCamera camera;
    private Rectangle bucket;
    //use GDX's array instead of the native Java Array or ArrayList because this one is more aware of GC (Garbage Collection)
    private Array<Rectangle> raindrops;
    private long lastDropTime;

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
	*/
    
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));
        dropImage = new Texture(Gdx.files.internal("droplet.png"));
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        camera = new OrthographicCamera();
        camera.setToOrtho(false,800,480);
        batch = new SpriteBatch();
        bucket = new Rectangle();
        bucket.x = 800/2-64/2;
        bucket.y = 20;
        bucket.width = 64;
        bucket.height = 64;
        raindrops = new Array<Rectangle>();
        spawnRaindrop();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
		batch.begin();
        batch.draw(bucketImage, bucket.x, bucket.y);
		batch.end();

        //Touch input to move the bucket sprite wherever the user's finger is x-axis, y-axis, z-axis
        //Gdx.input.getX() and Gdx.input.getY() return the current touch position
        if (Gdx.input.isTouched()){
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(),Gdx.input.getY(),0);
            camera.unproject(touchPos);
            bucket.x = touchPos.x -64/2;
        }
        if (bucket.x <0) bucket.x = 0;
        if (bucket.x > 800-64) bucket.x = 800-64;
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();

        Iterator<Rectangle> iterator = raindrops.iterator();
        while(iterator.hasNext()){
            Rectangle raindrop = iterator.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.y + 64 <0) iterator.remove();
            if (raindrop.overlaps(bucket)){
                dropSound.play();
                iterator.remove();
            }
        }
	}

    private void spawnRaindrop(){
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0,800-64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = System.nanoTime();
    }

    /*Any libgdx class that implements the Disposable interface and has a dispose() method needs to
    be cleaned up manually when it is no longer used.
     */
    @Override
    public void dispose(){
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
        batch.dispose();
    }
}
