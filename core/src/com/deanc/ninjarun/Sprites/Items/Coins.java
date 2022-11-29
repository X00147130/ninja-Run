package com.deanc.ninjarun.Sprites.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.deanc.ninjarun.NinjaRun;
import com.deanc.ninjarun.Screens.GameOverScreen;
import com.deanc.ninjarun.Screens.PlayScreen;
import com.deanc.ninjarun.Sprites.Ryu;

public class Coins extends Item {
    private static int count = 0;
    public NinjaRun ninjarun;
    public Coins(NinjaRun ninjarun,PlayScreen screen,float  x, float y) {
        super(screen, x, y);
        setRegion(new Texture("coin.png"));// clipart used
        this.ninjarun = ninjarun;
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
                NinjaRun.GROUND_BIT |
                NinjaRun.PLATFORM_BIT |
                NinjaRun.FINISH_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void useItem(Ryu ryu) {
        destroy();
        count += 100;
        screen.setCoins(count);
        Gdx.app.log("Coin", "destroyed");
        ninjarun.loadSound("audio/sounds/coin.mp3");
        long id = ninjarun.sound.play();
        if(ninjarun.getSoundVolume() != 0)
            ninjarun.sound.setVolume(id, ninjarun.getSoundVolume());
        else{
            ninjarun.sound.setVolume(id,0);
        }

    }
    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() /2);
        if(screen.isComplete() || (screen.getPlayer().currentState == Ryu.State.DEAD && screen.getPlayer().getStateTimer() > 3)){
            count = 0;
        }

    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }

    public void dispose(){
        screen.dispose();
    }
}
