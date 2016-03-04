package com.myandroid.tutorial.game.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.myandroid.tutorial.game.Sprites.InteractiveTileObject;

/**
 * Created by vgainous on 3/4/2016.
 */
//A contact listener is called when 2 fixtures in box2d collide with each other
public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if (fixA.getUserData()=="head" || fixB.getUserData()=="head"){
            Fixture head = fixA.getUserData()=="head" ? fixA :fixB;
            Fixture object = head == fixA? fixB: fixA;

            if (object.getUserData() instanceof InteractiveTileObject){
                ((InteractiveTileObject) object.getUserData()).onHeadHit();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    //allows changing characteristics once something has collided
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    //gives results of what happens due to that collision
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
