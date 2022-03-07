package com.deanc.ninjarun.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.deanc.ninjarun.NinjaRun;
import com.deanc.ninjarun.Sprites.Enemies.Enemy;
import com.deanc.ninjarun.Sprites.Items.Item;
import com.deanc.ninjarun.Sprites.Ryu;
import com.deanc.ninjarun.Sprites.TileObjects.InteractiveTileObject;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case NinjaRun.MARIO_HEAD_BIT | NinjaRun.BRICK_BIT:
            case NinjaRun.MARIO_HEAD_BIT | NinjaRun.COIN_BIT:
                if(fixA.getFilterData().categoryBits == NinjaRun.MARIO_HEAD_BIT)
                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit((Ryu) fixA.getUserData());
                else
                    ((InteractiveTileObject) fixA.getUserData()).onHeadHit((Ryu) fixB.getUserData());
                break;

            case NinjaRun.ENEMY_HEAD_BIT | NinjaRun.MARIO_BIT:
                if(fixA.getFilterData().categoryBits == NinjaRun.ENEMY_HEAD_BIT)
                    ((Enemy) fixA.getUserData()).hitOnHead();
                else
                    ((Enemy) fixB.getUserData()).hitOnHead();
                break;

            case NinjaRun.ENEMY_BIT | NinjaRun.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == NinjaRun.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;
            case NinjaRun.MARIO_BIT | NinjaRun.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == NinjaRun.MARIO_BIT)
                    ((Ryu)fixA.getUserData()).hit();
                else
                    ((Ryu)fixB.getUserData()).hit();
                break;

            case NinjaRun.ENEMY_BIT | NinjaRun.ENEMY_BIT:
                ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;
            case NinjaRun.ITEM_BIT | NinjaRun.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == NinjaRun.ITEM_BIT)
                    ((Item) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Item) fixB.getUserData()).reverseVelocity(true, false);
                break;
            case NinjaRun.ITEM_BIT | NinjaRun.MARIO_BIT:
                if (fixA.getFilterData().categoryBits == NinjaRun.ITEM_BIT)
                    ((Item) fixA.getUserData()).useItem((Ryu) fixB.getUserData());
                else
                    ((Item) fixB.getUserData()).useItem((Ryu) fixA.getUserData());
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
