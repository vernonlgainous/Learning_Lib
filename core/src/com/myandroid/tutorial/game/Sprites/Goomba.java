package com.myandroid.tutorial.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.bullet.softbody.btSoftBody;
import com.badlogic.gdx.utils.Array;
import com.myandroid.tutorial.game.MyAndroidTutorialGame;
import com.myandroid.tutorial.game.Screens.PlayScreen;

import java.util.ArrayList;

/**
 * Created by vgainous on 3/10/2016.
 */
public class Goomba extends Enemy{
    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;

    public Goomba(PlayScreen screen, float x, float y){
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for (int i = 0; i < 2; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("goomba"), i*16, 0, 16, 16));
        }
        walkAnimation = new Animation(0.4f, frames);
        stateTime = 0;
    }

    public void update(float dt){
        stateTime += dt;
        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
        setRegion(walkAnimation.getKeyFrame(stateTime, true));
        setBounds(getX(), getY(), 16/MyAndroidTutorialGame.PPM, 16/MyAndroidTutorialGame.PPM);
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32/ MyAndroidTutorialGame.PPM + 1.6f, 32/MyAndroidTutorialGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/MyAndroidTutorialGame.PPM);
        fdef.filter.categoryBits = MyAndroidTutorialGame.ENEMY_BIT;
        fdef.filter.maskBits = MyAndroidTutorialGame.GROUND_BIT | MyAndroidTutorialGame.COIN_BIT |
                MyAndroidTutorialGame.BRICK_BIT | MyAndroidTutorialGame.ENEMY_BIT |
                MyAndroidTutorialGame.OBJECT_BIT | MyAndroidTutorialGame.HERO_BIT;
        fdef.shape = shape;
        fdef.filter.categoryBits = MyAndroidTutorialGame.OBJECT_BIT;
        b2body.createFixture(fdef);

    }
}
