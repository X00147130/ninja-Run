package com.deanc.ninjarun.Sprites.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.deanc.ninjarun.NinjaRun;
import com.deanc.ninjarun.Screens.PlayScreen;
import com.deanc.ninjarun.Sprites.Ryu;


public class Shuriken extends Item{
public Shuriken(PlayScreen screen,float  x, float y) {
        super(screen, x, y);
        setRegion(new Texture("coins6.png"));
        }

@Override
public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / NinjaRun.PPM);
        fdef.filter.categoryBits = NinjaRun.WEAPON_BIT;
        fdef.filter.maskBits = NinjaRun.RYU_BIT |
        NinjaRun.GROUND_BIT |
        NinjaRun.PLATFORM_BIT |
        NinjaRun.FINISH_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
        }

@Override
public void useItem(Ryu ryu) {
        destroy();
        Gdx.app.log("SHURIKEN", "fired");
        NinjaRun.manager.get("audio/sounds/coin.mp3", Sound.class).play();

        }
@Override
public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() /2);

        }

@Override
public void draw(Batch batch) {
        super.draw(batch);
        }
        }
