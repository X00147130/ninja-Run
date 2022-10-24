package com.deanc.ninjarun.Sprites;

import static com.deanc.ninjarun.NinjaRun.PPM;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.utils.Timer;
import com.deanc.ninjarun.NinjaRun;
import com.deanc.ninjarun.Screens.PlayScreen;
import com.deanc.ninjarun.Tools.Controller;

public class Ryu extends Sprite {
    //State Variables for animation purposes
    public enum State{ FALLING, JUMPING, STANDING, RUNNING, ATTACK, DEAD, COMPLETE}
    public State currentState;
    public State previousState;


    //Basic variables
    public World world;
    private PlayScreen screen;
    private NinjaRun ninjarun;
    public Body b2body;
    public TextureRegion ryuStand;


    //Animation Variables
    private Animation <TextureRegion> ryuRun;
    private Animation <TextureRegion> ryuJump;
    private Animation <TextureRegion> ryuAttack;
    private Animation <TextureRegion> ryuDead;
    private Animation<TextureRegion> ryuComplete;
    private boolean runningRight;
    private float stateTimer;


    //boolean tests
    private boolean ryuIsDead;


    //health variables
    private int health;
    private int damage;
    private static int hitCounter;

    /*Jump Variables*/
    private int jumpCounter = 0;

    //Attack Variables
    public FixtureDef attackdef;
    private boolean attacking;
    private Fixture fix;

    //movement variables
    private Vector2 limit;


    public Ryu(PlayScreen screen,NinjaRun ninjarun){
        this.world = screen.getWorld();
        defineRyu();
        this.ninjarun = ninjarun;

        limit = new Vector2(0,0);

        this.screen = screen;
        attacking = false;

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
        if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
            setBounds(0, 0, 16 / NinjaRun.PPM, 18 / NinjaRun.PPM);
            setRegion(ryuStand);
        }
        if(Gdx.app.getType() == Application.ApplicationType.Android){
            setBounds(0, 0, 18 / NinjaRun.PPM, 20 / NinjaRun.PPM);
            setRegion(ryuStand);
        }

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
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            setBounds(0, 0, 18 / NinjaRun.PPM, 20 / NinjaRun.PPM);
        }
        frames.clear();

        //Creating Jump Animation loop
        frames.clear();

        frames.add(screen.getAtlas().findRegion("jumpup2"));
        frames.add(screen.getAtlas().findRegion("jumpup4"));
        frames.add(screen.getAtlas().findRegion("jumpup5"));
        frames.add(screen.getAtlas().findRegion("jumpup6"));
        frames.add(screen.getAtlas().findRegion("jumpup7"));

        ryuJump = new Animation <TextureRegion>(0.1f, frames);
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            setBounds(0, 0, 18 / NinjaRun.PPM, 20 / NinjaRun.PPM);
        }
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
        if(Gdx.app.getType() == Application.ApplicationType.Android){
            setBounds(0, 0, 18 / NinjaRun.PPM, 20 / NinjaRun.PPM);}
        frames.clear();

        //Attack Texture
        frames.add(screen.getAtlas().findRegion("attack1"));
        frames.add(screen.getAtlas().findRegion("attack2"));
        frames.add(screen.getAtlas().findRegion("attack3"));
        frames.add(screen.getAtlas().findRegion("attack4"));
        frames.add(screen.getAtlas().findRegion("attack5"));
        frames.add(screen.getAtlas().findRegion("attack4"));
        frames.add(screen.getAtlas().findRegion("attack3"));
        frames.add(screen.getAtlas().findRegion("attack2"));
        frames.add(screen.getAtlas().findRegion("attack1"));


        ryuAttack = new Animation <TextureRegion>(0.08f, frames);
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            setBounds(0, 0, 18 / NinjaRun.PPM, 20 / NinjaRun.PPM);
        }
        frames.clear();

        //Level Complete
        frames.add(screen.getAtlas().findRegion("attack2"));
        frames.add(screen.getAtlas().findRegion("attack3"));
        ryuComplete = new Animation<TextureRegion>(0.2f,frames);
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            setBounds(0, 0, 18 / NinjaRun.PPM, 20 / NinjaRun.PPM);
        }
        frames.clear();
    }

    public void update(float dt){
        if(Gdx.app.getType() == Application.ApplicationType.Android)
            setPosition(b2body.getPosition().x - getWidth() /2, b2body.getPosition().y - getHeight() /2);
        setPosition(b2body.getPosition().x - getWidth() /2, b2body.getPosition().y - getHeight() /3);
        setRegion(getFrame(dt));
        if(getY() < 0){
            ryuIsDead = true;
            b2body.applyLinearImpulse(new Vector2(0, 3f), b2body.getWorldCenter(), true);
            NinjaRun.manager.get("audio/music/yoitrax - Fuji.mp3", Music.class).stop();
            NinjaRun.manager.get("audio/music/mixkit-piano-horror-671.mp3", Music.class).play();
        }
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

            case COMPLETE:
                region = ryuComplete.getKeyFrame(stateTimer, true);
                break;

            case FALLING:

            case STANDING:

            default:
                region = ryuStand;
                break;
        }
        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
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

        else if (b2body.getLinearVelocity().x != 0 && currentState != State.ATTACK)
            return State.RUNNING;

        else if (attacking == true) {
            return State.ATTACK;
        }

        else if(screen.complete == true) {
            return State.COMPLETE;

        }

        else
            return State.STANDING;
    }



    public void defineRyu(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / PPM,31 / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / PPM);
        fdef.filter.categoryBits = NinjaRun.RYU_BIT;
        fdef.filter.maskBits = NinjaRun.GROUND_BIT |
                NinjaRun.FINISH_BIT |
                NinjaRun.PLATFORM_BIT |
                NinjaRun.ENEMY_BIT|
                NinjaRun.MONEY_BIT|
                NinjaRun.SKY_BIT|
                NinjaRun.ITEM_BIT;

        fdef.shape = shape;
        fdef.restitution = 0f;
        fdef.friction = 2f;
        b2body.setGravityScale(1.5f);
        b2body.createFixture(fdef).setUserData(this);


    //Ryus Head(Used for colliding with bricks
        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / PPM, 6 / PPM), new Vector2(2 / PPM, 6 / PPM));
        fdef.filter.categoryBits=NinjaRun.RYU_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);

    }

    /*Jump Counter System*/
    public void jumpReset(){
            ninjarun.jumpCounter = 0;
            ninjarun.doubleJumped = false;
            Gdx.app.log("Jumps", "Reset");
        }


    /*An Attack System Attempt*/

    public Fixture createAttack(){
        if(!isFlipX()){
            b2body.applyForce(new Vector2(1,0),b2body.getWorldCenter(),true);
        }
        else {
            b2body.applyForce(new Vector2(-1,0),b2body.getWorldCenter(),true);
        }
        b2body.setAwake(true);


        //Collision Detection Line

        EdgeShape head = new EdgeShape();
        if(!isFlipX()){
            head.set(new Vector2(-3/ NinjaRun.PPM,0/NinjaRun.PPM),new Vector2(12/NinjaRun.PPM,  -2 / NinjaRun.PPM));
        }
        else{
            head.set(new Vector2(-12/ NinjaRun.PPM,0/NinjaRun.PPM),new Vector2(-3/NinjaRun.PPM, 0  / NinjaRun.PPM));
        }
        attackdef = new FixtureDef();
        attackdef.shape = head;
        attackdef.isSensor = false;
        attackdef.filter.categoryBits= NinjaRun.ATTACK_BIT;
        attackdef.filter.maskBits = NinjaRun.ENEMY_BIT| NinjaRun.FINISH_BIT;

        Fixture fix1 = b2body.createFixture(attackdef);
        fix1.setUserData("attack");
        NinjaRun.manager.get("audio/sounds/mixkit-fast-sword-whoosh-2792.wav",Sound.class).play();
        head.dispose();
        return fix1;
    }

    public boolean isAttacking(){
        return attacking;
    }

    public void setIsAttacking(boolean attack){
        attacking = attack;
    }

    public void attack(){
        if(!attacking&& currentState != State.DEAD){
            attacking = true;
            Timer time = new Timer();
            currentState = State.ATTACK;
            Timer.Task task = time.scheduleTask(new Timer.Task(){
                @Override
                public void run() {
                    currentState = State.STANDING;
                    }
                },0.5f);

            fix = createAttack();
            if(isAttacking() == true){
                Timer.Task task1 = time.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        while(b2body.getFixtureList().size > 2){
                            b2body.destroyFixture(b2body.getFixtureList().pop());
                        }
                        attacking = false;
                    }
                },0.2f);
            }

        }
    }



    //getting hit method
    public void hit(){
        //loading gameover music in to set it to loop in case player leaves it for a while.
        Music music = NinjaRun.manager.get("audio/music/mixkit-piano-horror-671.mp3", Music.class);
        music.setLooping(true);

        if(hitCounter < 2){    //ryu is pushed back and says ow
            b2body.applyLinearImpulse(new Vector2(-1f,1f),b2body.getWorldCenter(),true);
            NinjaRun.manager.get("audio/sounds/getting-hit.wav", Sound.class).play();

            hitCounter++;
        }
        else{   //Ryus death
            NinjaRun.manager.get("audio/music/yoitrax - Fuji.mp3", Music.class).stop();
            music.play();
            NinjaRun.manager.get("audio/sounds/sexynakedbunny-ouch.mp3", Sound.class).play();
            ryuIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = NinjaRun.GROUND_BIT|NinjaRun.PLATFORM_BIT;
            for (Fixture fixture : b2body.getFixtureList())
                fixture.setFilterData(filter);
            b2body.applyLinearImpulse(new Vector2(-1f,2f), b2body.getWorldCenter(), true);
            hitCounter = 3;
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

    public void reverseVelocity(boolean x, boolean y){
        if(x){
            limit.x = -limit.x;
        }
        if(y) {
            limit.y = -limit.y;
        }
    }
}
