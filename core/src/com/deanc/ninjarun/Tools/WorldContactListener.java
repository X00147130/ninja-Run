package com.deanc.ninjarun.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.deanc.ninjarun.NinjaRun;
import com.deanc.ninjarun.Sprites.Enemies.Enemy;
import com.deanc.ninjarun.Sprites.Enemies.Ninja;
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
            case NinjaRun.RYU_BIT | NinjaRun.FINISH_BIT:
                if(fixA.getFilterData().categoryBits == NinjaRun.RYU_BIT)
                ((InteractiveTileObject) fixB.getUserData()).onHit((Ryu) fixA.getUserData());
            else
                ((InteractiveTileObject) fixA.getUserData()).onHit((Ryu) fixB.getUserData());
                break;

            case NinjaRun.ENEMY_BIT | NinjaRun.ATTACK_BIT:
                if(fixA.getFilterData().categoryBits == NinjaRun.ATTACK_BIT)
                    ((Ninja)fixB.getUserData()).attacked();
                else
                    ((Ninja)fixA.getUserData()).attacked();
                break;

            case NinjaRun.ENEMY_BIT | NinjaRun.BARRIER_BIT:
                if (fixA.getFilterData().categoryBits == NinjaRun.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;
            case NinjaRun.RYU_BIT | NinjaRun.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == NinjaRun.RYU_BIT)
                    ((Ryu)fixA.getUserData()).hit();
                else
                    ((Ryu)fixB.getUserData()).hit();
                break;

            case NinjaRun.ENEMY_BIT | NinjaRun.ENEMY_BIT:
                ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;

            case NinjaRun.ITEM_BIT | NinjaRun.RYU_BIT:
                if (fixA.getFilterData().categoryBits == NinjaRun.ITEM_BIT)
                    ((Item) fixA.getUserData()).useItem((Ryu) fixB.getUserData());
                else
                    ((Item) fixB.getUserData()).useItem((Ryu) fixA.getUserData());
                break;

            case NinjaRun.MONEY_BIT | NinjaRun.RYU_BIT:
                if (fixA.getFilterData().categoryBits == NinjaRun.MONEY_BIT)
                    ((Item) fixA.getUserData()).useItem((Ryu) fixB.getUserData());
                else
                    ((Item) fixB.getUserData()).useItem((Ryu) fixA.getUserData());
                break;

            case NinjaRun.SKY_BIT | NinjaRun.RYU_BIT:
                if (fixA.getFilterData().categoryBits == NinjaRun.SKY_BIT)
                    ((InteractiveTileObject) fixA.getUserData()).onHit((Ryu) fixB.getUserData());
                else
                    ((InteractiveTileObject) fixB.getUserData()).onHit((Ryu) fixA.getUserData());
                break;

            case NinjaRun.WEAPON_BIT | NinjaRun.RYU_BIT:
                if (fixA.getFilterData().categoryBits == NinjaRun.WEAPON_BIT)
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
