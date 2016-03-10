package com.myandroid.tutorial.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.myandroid.tutorial.game.MyAndroidTutorialGame;
import com.myandroid.tutorial.game.Screens.PlayScreen;

/**
 * Created by vgainous on 3/3/2016.
 */
public class Hero extends Sprite{
    public enum State{FALLING, JUMPING, STANDING, RUNNING};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion marioStand;
    private Animation marioRun;
    private Animation marioJump;
    private float stateTimer;
    private boolean runningRight;

    public Hero(PlayScreen screen){
        super(screen.getAtlas().findRegion("little_mario"));
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        //before initalizing the animations we need an array of texture regions
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 1; i<4; i++) {
            frames.add(new TextureRegion(getTexture(), i * 16, 11, 16, 16));
        }
        marioRun = new Animation(0.1f, frames);
        frames.clear();
        for (int i =4; i<6; i++){
            frames.add(new TextureRegion(getTexture(), i*16, 11, 16, 16));
        }
        marioJump = new Animation(0.1f, frames);


        defineHero();
        //to get the coordinates of this sprite look at the image itself and this one is the first
        //so start at (0,0) and it is 16px by 16px.
        marioStand = new TextureRegion(getTexture(),0,11,16,16);
        setBounds(0,0,16/MyAndroidTutorialGame.PPM, 16/MyAndroidTutorialGame.PPM);
        setRegion(marioStand);
    }

    public void update(float dt){
        //set the sprite on the box2dBody the math calculations are to find the bottom left point of the b2dbody
        setPosition(b2body.getPosition().x-getWidth()/2, b2body.getPosition().y-getHeight()/2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();
        TextureRegion region;
        switch (currentState){
            case JUMPING:
                region = marioJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = marioRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = marioStand;
                break;
        }

        if ((b2body.getLinearVelocity().x<0 || !runningRight)&& !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        }
        else if ((b2body.getLinearVelocity().x>0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }
        //if the current state equals the previous state set the state timer equal to dt else set it to 0
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;

    }

    public State getState(){
        if (b2body.getLinearVelocity().y >0 || b2body.getLinearVelocity().y<0 && previousState== State.JUMPING)
            return State.JUMPING;
        else if (b2body.getLinearVelocity().y<0)
            return State.FALLING;
        else if (b2body.getLinearVelocity().x !=0)
            return State.RUNNING;
        else
            return State.STANDING;
    }
    public void defineHero(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32/MyAndroidTutorialGame.PPM, 32/MyAndroidTutorialGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/MyAndroidTutorialGame.PPM);
        fdef.filter.categoryBits = MyAndroidTutorialGame.HERO_BIT;
        //define what other entities/tiled layers the hero can collide with.
        fdef.filter.maskBits=MyAndroidTutorialGame.GROUND_BIT | MyAndroidTutorialGame.COIN_BIT | MyAndroidTutorialGame.BRICK_BIT | MyAndroidTutorialGame.ENEMY_BIT | MyAndroidTutorialGame.OBJECT_BIT;


        fdef.shape = shape;
        b2body.createFixture(fdef);

        //create sensor for hero's head
        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2/MyAndroidTutorialGame.PPM, 6/MyAndroidTutorialGame.PPM),new Vector2(2/MyAndroidTutorialGame.PPM, 6/MyAndroidTutorialGame.PPM));
        fdef.shape = head;
        fdef.isSensor=true;
        b2body.createFixture(fdef).setUserData("head");
    }
}
