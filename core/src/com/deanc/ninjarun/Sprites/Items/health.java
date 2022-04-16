package com.deanc.ninjarun.Sprites.Items;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.deanc.ninjarun.NinjaRun;
import com.deanc.ninjarun.Screens.PlayScreen;
import com.deanc.ninjarun.Sprites.Ryu;

public class health extends Item{
    public TextureAtlas atlas;

    public health(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        atlas = new TextureAtlas("items.pack");
        setRegion(atlas.findRegion("health"));
        velocity = new Vector2(0.7f ,0);
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
                NinjaRun.OBJECT_BIT |
                NinjaRun.GROUND_BIT |
                NinjaRun.BRICK_BIT |
                NinjaRun.COIN_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);

    }

    @Override
    public void useItem(Ryu ryu) {
        destroy();
        Ryu.setHitCounter(0);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        NinjaRun.manager.get("audio/sounds/coin.mp3", Sound.class);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() /2);
        velocity.y= body.getLinearVelocity().y;
    }
}
