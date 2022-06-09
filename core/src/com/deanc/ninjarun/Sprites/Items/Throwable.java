package com.deanc.ninjarun.Sprites.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.deanc.ninjarun.NinjaRun;
import com.deanc.ninjarun.Screens.PlayScreen;
import com.deanc.ninjarun.Sprites.Ryu;

public class Throwable extends Item{
    public final short SPEED = 2;
    private Texture texture;
    private float stateTime;


    public Throwable(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        texture = new Texture(" ");
        setBounds(x,y,10 / NinjaRun.PPM, 10 / NinjaRun.PPM);
        stateTime = 0;

    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(2 / NinjaRun.PPM);
        fdef.filter.categoryBits = NinjaRun.SHURIKEN_BIT;
        fdef.filter.maskBits = NinjaRun.ENEMY_BIT |
                NinjaRun.GROUND_BIT |
                NinjaRun.PLATFORM_BIT |
                NinjaRun.FINISH_BIT;

        fdef.shape = shape;
        fdef.restitution = 0f;
        fdef.friction = 0f;
        body.setGravityScale(0);
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void useItem(Ryu ryu) {
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if(!destroyed){
            stateTime += dt;
            if(body!=null){
                setPosition(body.getPosition().x / getWidth() / 2, body.getPosition().y / getHeight() / 2);
                body.setLinearVelocity(SPEED,0);
            }

            if (stateTime > 1.5f ||  (getDestroyed()) && !destroyed){
                destroy();
                screen.getPlayer().setCanThrow(true);

            }
        }


    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public boolean getDestroyed() {
        return super.getDestroyed();
    }
}
