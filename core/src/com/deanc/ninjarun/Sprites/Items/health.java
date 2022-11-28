package com.deanc.ninjarun.Sprites.Items;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.deanc.ninjarun.NinjaRun;
import com.deanc.ninjarun.Screens.PlayScreen;
import com.deanc.ninjarun.Sprites.Ryu;

public class health extends Item{
    public NinjaRun ninjarun;

    public health(NinjaRun ninjarun, PlayScreen screen, float x, float y) {
        super(screen, x, y);
        setRegion(new Texture("health_vial1.png"));// clipart used
        this.ninjarun = ninjarun;
    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / NinjaRun.PPM);
        fdef.filter.categoryBits = NinjaRun.ITEM_BIT;
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
        Ryu.setHitCounter(0);
        ninjarun.loadSound("audio/sounds/healthDrink.wav");
        long id = ninjarun.sound.play();
        if(ninjarun.getSoundVolume() != 0)
            ninjarun.sound.setVolume(id, 1f);
        else{
            ninjarun.sound.setVolume(id,0);
        }
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }
}
