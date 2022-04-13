package com.deanc.ninjarun.Sprites;

import static com.deanc.ninjarun.NinjaRun.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
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
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Array;
import com.deanc.ninjarun.NinjaRun;
import com.deanc.ninjarun.Screens.PlayScreen;

public class Ryu extends Sprite {
    //State Variables for animation purposes
    public enum State{ FALLING, JUMPING, STANDING, RUNNING, ATTACK, DEAD }
    public State currentState;
    public State previousState;


    //Basic variables
    public World world;
    public Body b2body;
    public Body arm;
    public TextureRegion ryuStand;


    //Animation Variables
    private Animation <TextureRegion> ryuRun;
    private Animation <TextureRegion> ryuJump;
    private Animation <TextureRegion> ryuAttack;
    private Animation<TextureRegion> ryuDead;
    private boolean runningRight;
    private float stateTimer;


    //boolean tests
    private boolean ryuIsDead;


    //health variables
    private int health;
    private int damage;
    private static int hitCounter;


    //Attack Variables
    public FixtureDef attackdef;


    public Ryu(PlayScreen screen){
        this.world = screen.getWorld();
        defineRyu();

        //initialising health variables
        health = 100;
        damage = 50;
        hitCounter = 0;


        //Animation variables initialization
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        //Animation initialization for Mario Standing
        ryuStand = new TextureRegion(screen.getAtlas().findRegion("attack1"));
         setBounds(0,0,16 / NinjaRun.PPM, 16 / NinjaRun.PPM);
       setRegion(ryuStand);

        //Creating Animation loop for Ryu running
        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.clear();

        frames.add(screen.getAtlas().findRegion("running1"));
        frames.add(screen.getAtlas().findRegion("running2"));
        frames.add(screen.getAtlas().findRegion("running3"));
        frames.add(screen.getAtlas().findRegion("running4"));
        frames.add(screen.getAtlas().findRegion("running5"));
        frames.add(screen.getAtlas().findRegion("running6"));

        ryuRun = new Animation <TextureRegion>(0.1f, frames);
        frames.clear();

        //Creating Jump Animation loop
        frames.clear();

        frames.add(screen.getAtlas().findRegion("jumpup2"));
        frames.add(screen.getAtlas().findRegion("jumpup4"));
        frames.add(screen.getAtlas().findRegion("jumpup5"));
        frames.add(screen.getAtlas().findRegion("jumpup6"));
        frames.add(screen.getAtlas().findRegion("jumpup7"));

        ryuJump = new Animation <TextureRegion>(0.1f, frames);
        frames.clear();


        //Ryu death animation

        frames.add(screen.getAtlas().findRegion("die2"));
        frames.add(screen.getAtlas().findRegion("die3"));
        frames.add(screen.getAtlas().findRegion("die4"));
        frames.add(screen.getAtlas().findRegion("die5"));
        frames.add(screen.getAtlas().findRegion("die7"));
        frames.add(screen.getAtlas().findRegion("die8"));
        frames.add(screen.getAtlas().findRegion("die10"));

        ryuDead = new Animation <TextureRegion>(0.1f, frames);
        frames.clear();

        //Attack Texture
        frames.add(screen.getAtlas().findRegion("attack1"));
        frames.add(screen.getAtlas().findRegion("attack2"));
        frames.add(screen.getAtlas().findRegion("attack3"));
        frames.add(screen.getAtlas().findRegion("attack4"));
        frames.add(screen.getAtlas().findRegion("attack5"));
        frames.add(screen.getAtlas().findRegion("attacka6"));
        frames.add(screen.getAtlas().findRegion("attacka7"));
        ryuAttack = new Animation <TextureRegion>(0.1f, frames);
        frames.clear();
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() /2, b2body.getPosition().y - getHeight() /2);
        setRegion(getFrame(dt));

    }


    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;


        switch(currentState){
           case DEAD:
                region = ryuDead.getKeyFrame(stateTimer, false);
            break;

            case JUMPING:
                region =  ryuJump.getKeyFrame(stateTimer, false);
                break;

            case RUNNING:
                region = ryuRun.getKeyFrame(stateTimer, true);
               break;

            case ATTACK:
                region = ryuAttack.getKeyFrame(stateTimer,true);
                break;

            case FALLING:

            case STANDING:

            default:
                region = ryuStand;
                break;
        }
        if(b2body!=null) {
            if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
                region.flip(true, false);
                runningRight = false;
            } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
                region.flip(true, false);
                runningRight = true;
            }
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
        }

    public State getState(){
        if(ryuIsDead)
            return State.DEAD;

        else if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;

        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;

        else if (b2body.getLinearVelocity().x != 0)
            return State.RUNNING;

        else if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
            return State.ATTACK;

        else
            return State.STANDING;
    }



    public void defineRyu(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / PPM,32 / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / PPM);
        fdef.filter.categoryBits = NinjaRun.RYU_BIT;
        fdef.filter.maskBits = NinjaRun.GROUND_BIT |
                NinjaRun.COIN_BIT |
                NinjaRun.BRICK_BIT|
                NinjaRun.ENEMY_BIT|
                NinjaRun.OBJECT_BIT|
                NinjaRun.ITEM_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);


    //Ryus Head(Used for colliding with bricks
        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / PPM, 6 / PPM), new Vector2(2 / PPM, 6 / PPM));
        fdef.filter.categoryBits=NinjaRun.RYU_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);

    }

    //An Attack System Attempt

    public Fixture createAttack(){
        if(!isFlipX()){
            b2body.applyLinearImpulse(new Vector2(1,0),b2body.getWorldCenter(),true);
        }
        else {
            b2body.applyLinearImpulse(new Vector2(-1,0),b2body.getWorldCenter(),true);
        }
        b2body.setAwake(true);

        //Collision Detection Line

        EdgeShape head = new EdgeShape();
        if(!isFlipX()){
            head.set(new Vector2(-3/ NinjaRun.PPM,0/NinjaRun.PPM),new Vector2(9/NinjaRun.PPM, 0 / NinjaRun.PPM));
        }
        else{
            head.set(new Vector2(-9/ NinjaRun.PPM,0/NinjaRun.PPM),new Vector2(-3/NinjaRun.PPM, 0 / NinjaRun.PPM));
        }
        attackdef.shape = head;
        attackdef.isSensor = false;
        attackdef.filter.categoryBits= NinjaRun.ATTACK_BIT;
        attackdef.filter.maskBits = NinjaRun.ENEMY_BIT| NinjaRun.COIN_BIT;

        Fixture fix1 = b2body.createFixture(attackdef);
        fix1.setUserData("attack");
        NinjaRun.manager.get("audio/sounds/coin.mp3",Sound.class).play();
        head.dispose();
        return fix1;
    }



    //getting hit method
    public void hit(){
        //loading gameover music in to set it to loop in case player leaves it for a while.
        Music music = NinjaRun.manager.get("audio/music/mixkit-piano-horror-671.mp3", Music.class);
        music.setLooping(true);

        if(hitCounter < 1){    //ryu is pushed back and says ow
            b2body.applyLinearImpulse(new Vector2(-1f,1f),b2body.getWorldCenter(),true);
            NinjaRun.manager.get("audio/sounds/getting-hit.wav", Sound.class).play();

            hitCounter++;
        }
        else{   //Ryus death
            NinjaRun.manager.get("audio/music/yoitrax-warrior.mp3", Music.class).stop();
            music.play();
            NinjaRun.manager.get("audio/sounds/sexynakedbunny-ouch.mp3", Sound.class).play();
            ryuIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = NinjaRun.GROUND_BIT;
            for (Fixture fixture : b2body.getFixtureList())
                fixture.setFilterData(filter);
            b2body.applyLinearImpulse(new Vector2(-1f,2f), b2body.getWorldCenter(), true);
            hitCounter = 2;
        }
    }

    public boolean isDead(){
        return ryuIsDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }

    public static int getHitCounter(){
        return hitCounter;
    }

    public static void setHitCounter(int resetHits){
        hitCounter = resetHits;
    }


}
