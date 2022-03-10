package com.deanc.ninjarun.Attacks;



import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.deanc.ninjarun.NinjaRun;
import com.deanc.ninjarun.Screens.PlayScreen;
import com.deanc.ninjarun.Sprites.Ryu;

public class Sword extends Ryu {

    private final int DAMAGE = 50;

    private Ryu player;
    private boolean attack;


    public Sword(PlayScreen screen) {
        super(screen);
        attack = false;
        player = new Ryu(screen);
    }

    public void defineSword(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(33 / NinjaRun.PPM,33 / NinjaRun.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox( player.b2body.getWorldCenter().x +1, player.b2body.getWorldCenter().y+1);
        fdef.filter.categoryBits = NinjaRun.ATTACK_BIT;
        fdef.filter.maskBits = NinjaRun.ENEMY_BIT;

        fdef.shape= shape;
        b2body.createFixture(fdef).setUserData(this);

    }
}
