package com.deanc.ninjarun.Sprites.Items;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.deanc.ninjarun.NinjaRun;
import com.deanc.ninjarun.Scenes.Hud;
import com.deanc.ninjarun.Screens.PlayScreen;
import com.deanc.ninjarun.Sprites.Ryu;

public class Coins extends Item {
    public TextureAtlas atlas;
    private Animation<TextureRegion> coins;
    private float stateTimer;

    public Coins(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        atlas = new TextureAtlas("items.pack");

        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.clear();

        frames.add(screen.getAtlas().findRegion("coins1"));
        frames.add(screen.getAtlas().findRegion("coins2"));
        frames.add(screen.getAtlas().findRegion("coins3"));
        frames.add(screen.getAtlas().findRegion("coins4"));
        frames.add(screen.getAtlas().findRegion("coins5"));
        frames.add(screen.getAtlas().findRegion("coins6"));
        frames.add(screen.getAtlas().findRegion("coins7"));
        frames.add(screen.getAtlas().findRegion("coins8"));
        frames.add(screen.getAtlas().findRegion("coins9"));
        frames.add(screen.getAtlas().findRegion("coins10"));
        frames.add(screen.getAtlas().findRegion("coins11"));
        frames.add(screen.getAtlas().findRegion("coins12"));
        frames.add(screen.getAtlas().findRegion("coins13"));
        frames.add(screen.getAtlas().findRegion("coins14"));

        coins = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(3 / NinjaRun.PPM);
        fdef.filter.categoryBits = NinjaRun.MONEY_BIT;
        fdef.filter.maskBits = NinjaRun.RYU_BIT |
                NinjaRun.OBJECT_BIT |
                NinjaRun.GROUND_BIT |
                NinjaRun.PLATFORM_BIT |
                NinjaRun.FINISH_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);

    }

    @Override
    public void useItem(Ryu ryu) {
        destroy();

    }


    @Override
    public void update(float dt) {
        super.update(dt);
        NinjaRun.manager.get("audio/sounds/coin.mp3", Sound.class);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt) {
        TextureRegion region;
        region = coins.getKeyFrame(stateTimer, true);
        return region;
    }
}
