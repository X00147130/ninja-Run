package com.deanc.ninjarun.Sprites.Enemies;

import static com.deanc.ninjarun.Sprites.Enemies.Ninja.State.RUNNING;
import static com.deanc.ninjarun.Sprites.Enemies.Ninja.State.DEAD;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.deanc.ninjarun.NinjaRun;
import com.deanc.ninjarun.Screens.PlayScreen;
import com.deanc.ninjarun.Sprites.Ryu;

public class Ninja extends Enemy {
    //animation variables
    public enum State{RUNNING, DEAD }
    public State currentState;
    public State previousState;
    private boolean ninjaDead;

    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> dieAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    private boolean runningRight;

    private int enemyHitCounter;

    public Ninja(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        //run animation
        frames = new Array<TextureRegion>();
        frames.add(screen.getAtlas().findRegion("enemyRun1"));
        frames.add(screen.getAtlas().findRegion("enemyRun2"));
        frames.add(screen.getAtlas().findRegion("enemyRun3"));
        frames.add(screen.getAtlas().findRegion("enemyRun4"));
        frames.add(screen.getAtlas().findRegion("enemyRun5"));
        frames.add(screen.getAtlas().findRegion("enemyRun6"));

        walkAnimation = new Animation <TextureRegion>(0.1f, frames);
        frames.clear();


        stateTime = 0;
        setBounds(getX(), getY(), 16 / NinjaRun.PPM , 16 / NinjaRun.PPM);
        setToDestroy = false;
        destroyed =false;
        enemyHitCounter = 0;
        ninjaDead = false;
    }

    public State getState() {
        if(ninjaDead = true)
            return Ninja.State.DEAD;

        else
            return RUNNING;

    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;


        switch (currentState) {
            case DEAD:
                region = dieAnimation.getKeyFrame(stateTime, false);
                break;

            case RUNNING:

            default:
                region = walkAnimation.getKeyFrame(stateTime,true);
                break;
        }
        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }
        stateTime = currentState == previousState ? stateTime + dt : 0;
        previousState = currentState;
        return region;
    }


    public void update(float dt) {
        stateTime += dt;
        if (setToDestroy && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;

            //death animation
            frames.clear();

            frames.add(screen.getAtlas().findRegion("enemyDie1"));
            frames.add(screen.getAtlas().findRegion("enemyDie2"));
            frames.add(screen.getAtlas().findRegion("enemyDie3"));
            frames.add(screen.getAtlas().findRegion("enemyDie4"));
            frames.add(screen.getAtlas().findRegion("enemyDie5"));
            frames.add(screen.getAtlas().findRegion("enemyDie6"));
            frames.add(screen.getAtlas().findRegion("enemyDie7"));

            dieAnimation = new Animation <TextureRegion>(0.1f, frames);
            frames.clear();

            stateTime=0;
        } else if (!destroyed) {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / NinjaRun.PPM);
        fdef.filter.categoryBits = NinjaRun.ENEMY_BIT;
        fdef.filter.maskBits = NinjaRun.GROUND_BIT |
                NinjaRun.COIN_BIT |
                NinjaRun.BRICK_BIT |
                NinjaRun.ENEMY_BIT |
                NinjaRun.OBJECT_BIT|
                NinjaRun.ATTACK_BIT|
                NinjaRun.RYU_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch){
        if(!destroyed || stateTime < 1)
            super.draw(batch);
    }

    @Override
    public void attacked() {
            setToDestroy = true;
            NinjaRun.manager.get("audio/sounds/stomp.wav", Sound.class).play();
        }
    }

