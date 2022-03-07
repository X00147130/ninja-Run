package com.deanc.ninjarun.Sprites;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.deanc.ninjarun.NinjaRun;
import com.deanc.ninjarun.Screens.PlayScreen;
import com.deanc.ninjarun.NinjaRun;

public class Ryu extends Sprite {
    //State Variables for animation purposes
    public enum State{ FALLING, JUMPING, STANDING, RUNNING, GROWING, DEAD }
    public State currentState;
    public State previousState;

    //Basic variables
    public World world;
    public Body b2body;
    private TextureRegion marioStand;
    private TextureRegion marioDead;

    //Animation Variables
    private Animation <TextureRegion> marioRun;
    private TextureRegion marioJump;
    private boolean runningRight;
    private float stateTimer;

    //Big Mario Animation Variables
    private TextureRegion bigMarioStand;
    private TextureRegion bigMarioJump;
    private Animation<TextureRegion> bigMarioRun;
    private Animation<TextureRegion> bigMarioGrow;

    //boolean tests
    private boolean runGrowAnimation;
    private boolean marioIsBig;
    private boolean timeToDefineBigMario;
    private boolean timeToRedefineMario;
    private boolean marioIsDead;

    public Ryu(PlayScreen screen){
        this.world = screen.getWorld();
        defineMario();

        //Animation variables initialization
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        //Animation initialization for Mario Standing
        marioStand = new TextureRegion(screen.getAtlas().findRegion("little_mario"), 0,0,16,16);
        setBounds(0,0,16 / NinjaRun.PPM, 16 / NinjaRun.PPM);
        setRegion(marioStand);

        //Animation For Big Mario Standing
        bigMarioStand = new TextureRegion(screen.getAtlas().findRegion("big_mario"), 0,0, 16,32 );


        //Creating Animation loop for Mario running
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i =1; i<4; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("little_mario"), i * 16,0,16,16 ));
        marioRun = new Animation <TextureRegion>(0.1f, frames);
        frames.clear();

        /*Creating Run Animation For Big Mario
         using array from small mario thats already initialised*/
        for(int i =1; i<4; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), i * 16,0,16,32 ));
        bigMarioRun = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        //getting frames for growing mario animation
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), 240, 0 , 16, 32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), 0, 0 , 16, 32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), 240, 0 , 16, 32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), 0, 0 , 16, 32));
        bigMarioGrow = new Animation<TextureRegion>(0.2f, frames);

        //Creating Jump Animation loop
        marioJump = (new TextureRegion(screen.getAtlas().findRegion("little_mario"), 80, 0,16,16));

        //Creating Big Mario Jump Animation
        bigMarioJump = new TextureRegion(screen.getAtlas().findRegion("big_mario"), 80,0, 16,32 );

        runGrowAnimation = false;
        marioIsBig = false;

        //mario dying contsruction
        marioDead = new TextureRegion(screen.getAtlas().findRegion("little_mario"), 96, 0, 16, 16);
    }

    public void update(float dt){
        if(marioIsBig)
            setPosition(b2body.getPosition().x - getWidth() /2, b2body.getPosition().y - getHeight() /2 - 6 / NinjaRun.PPM);
        else
            setPosition(b2body.getPosition().x - getWidth() /2, b2body.getPosition().y - getHeight() /2);

        setRegion(getFrame(dt));

        if(timeToDefineBigMario)
            defineBigMario();
        if(timeToRedefineMario)
            redefineMario();
    }

    public void grow(){
        runGrowAnimation = true;
        marioIsBig = true;
        timeToDefineBigMario = true;
        setBounds(getX(), getY(), getWidth(), getHeight() * 2);
        NinjaRun.manager.get("audio/sounds/powerup.wav", Sound.class).play();
    }


    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;

        switch(currentState){
            case DEAD:
                region = marioDead;
            break;

            case GROWING:
                region = bigMarioGrow.getKeyFrame(stateTimer);
                if(bigMarioGrow.isAnimationFinished(stateTimer))
                    runGrowAnimation = false;
                break;

            case JUMPING:
                region = marioIsBig ? bigMarioJump : marioJump;
                break;

            case RUNNING:
                region = marioIsBig ? bigMarioRun.getKeyFrame(stateTimer, true) : marioRun.getKeyFrame(stateTimer, true);
                break;

            case FALLING:

            case STANDING:

            default:
                region = marioIsBig ? bigMarioStand : marioStand;
                break;
        }
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true,false);
            runningRight = false;
        }
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;

        }

    public State getState(){
        if(marioIsDead)
            return State.DEAD;

        else if(runGrowAnimation)
            return State.GROWING;

        else if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;

        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;

        else if (b2body.getLinearVelocity().x != 0)
            return State.RUNNING;

        else
            return State.STANDING;
    }

    public void defineBigMario(){
        Vector2 currentPosition = b2body.getPosition();
        world.destroyBody(b2body);

        BodyDef bdef = new BodyDef();
        bdef.position.set(currentPosition.add(0, 10 / NinjaRun.PPM));
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / NinjaRun.PPM);
        fdef.filter.categoryBits = NinjaRun.MARIO_BIT;
        fdef.filter.maskBits = NinjaRun.GROUND_BIT |
                NinjaRun.COIN_BIT |
                NinjaRun.BRICK_BIT|
                NinjaRun.ENEMY_BIT|
                NinjaRun.OBJECT_BIT|
                NinjaRun.ENEMY_HEAD_BIT|
                NinjaRun.ITEM_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
        shape.setPosition(new Vector2 (0, -14 / NinjaRun.PPM));
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / NinjaRun.PPM, 6 / NinjaRun.PPM), new Vector2(2 /NinjaRun.PPM, 6 / NinjaRun.PPM));
        fdef.filter.categoryBits = NinjaRun.MARIO_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
        timeToDefineBigMario = false;
    }

    public void defineMario(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / NinjaRun.PPM,32 / NinjaRun.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / NinjaRun.PPM);
        fdef.filter.categoryBits = NinjaRun.MARIO_BIT;
        fdef.filter.maskBits = NinjaRun.GROUND_BIT |
                NinjaRun.COIN_BIT |
                NinjaRun.BRICK_BIT|
                NinjaRun.ENEMY_BIT|
                NinjaRun.OBJECT_BIT|
                NinjaRun.ENEMY_HEAD_BIT|
                NinjaRun.ITEM_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / NinjaRun.PPM, 6 / NinjaRun.PPM), new Vector2(2 /NinjaRun.PPM, 6 / NinjaRun.PPM));
        fdef.filter.categoryBits=NinjaRun.MARIO_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
    }

    public boolean isBig(){
        return marioIsBig;
    }

    public void hit(){
        if(marioIsBig){    //mario shrinks
            marioIsBig = false;
            timeToRedefineMario = true;
            setBounds(getX(), getY(), getWidth(), getHeight() / 2);
            NinjaRun.manager.get("audio/sounds/powerdown.wav", Sound.class).play();
        }
        else{   //marios death
            NinjaRun.manager.get("audio/music/yoitrax-warrior.mp3", Music.class).stop();
            NinjaRun.manager.get("audio/music/Gewitter__Thunderstorm-Tim.mp3", Music.class).play();
            NinjaRun.manager.get("audio/sounds/mariodie.wav", Sound.class).play();
            marioIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = NinjaRun.NOTHING_BIT;
            for (Fixture fixture : b2body.getFixtureList())
                fixture.setFilterData(filter);
            b2body.applyLinearImpulse(new Vector2(0,4f), b2body.getWorldCenter(), true);
        }
    }

    public void redefineMario(){
        Vector2 position = b2body.getPosition();
        world.destroyBody(b2body);
        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / NinjaRun.PPM);
        fdef.filter.categoryBits = NinjaRun.MARIO_BIT;
        fdef.filter.maskBits = NinjaRun.GROUND_BIT |
                NinjaRun.COIN_BIT |
                NinjaRun.BRICK_BIT|
                NinjaRun.ENEMY_BIT|
                NinjaRun.OBJECT_BIT|
                NinjaRun.ENEMY_HEAD_BIT|
                NinjaRun.ITEM_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / NinjaRun.PPM, 6 / NinjaRun.PPM), new Vector2(2 /NinjaRun.PPM, 6 / NinjaRun.PPM));
        fdef.filter.categoryBits=NinjaRun.MARIO_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);

        timeToRedefineMario = false;
    }

    public boolean isDead(){
        return marioIsDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }
}
