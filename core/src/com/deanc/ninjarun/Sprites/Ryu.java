package com.deanc.ninjarun.Sprites;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.deanc.ninjarun.NinjaRun;
import com.deanc.ninjarun.Scenes.Hud;
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
    public Texture ryuStand;
    private Animation<Texture> ryuDead;

    //Animation Variables
    private Animation <Texture> ryuRun;
    private Animation <Texture> ryuJump;
    private boolean runningRight;
    private float stateTimer;

    //boolean tests
    private boolean runGrowAnimation;
    private boolean marioIsDead;

    //health variables
    private int health;
    private int damage;
    private static int hitCounter;


    public Ryu(PlayScreen screen){
        this.world = screen.getWorld();
        defineMario();

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
        ryuStand = new Texture("jumpup1.png");
        setBounds(0,0,16 / NinjaRun.PPM, 15 / NinjaRun.PPM);
        setRegion(ryuStand);

        //Creating Animation loop for Mario running
        Texture run1 = new Texture("running1.png");
        Texture run2 = new Texture("running2.png");
        Texture run3 = new Texture("running3.png");
        Texture run4 = new Texture("running4.png");
        Texture run5 = new Texture("running5.png");
        Texture run6 = new Texture("running6.png");
        Array<Texture> frames = new Array<Texture>(6);
        frames.add(run1);
        frames.add(run2);
        frames.add(run3);
        frames.add(run4);
        frames.add(run5);
        frames.add(run6);
        ryuRun = new Animation <Texture>(0.1f, frames);
        frames.clear();

        //Creating Jump Animation loop
        Texture jump1 = new Texture("jumpup1.png");
        Texture jump2 = new Texture("jumpup2.png");
        Texture jump3 = new Texture("jumpup3.png");
        Texture jump4 = new Texture("jumpup4.png");
        Texture jump5 = new Texture("jumpup5.png");
        Texture jump6 = new Texture("jumpup6.png");
        Texture jump7 = new Texture("jumpup7.png");
        Array<Texture> jumping = new Array<Texture>(7);
        jumping.add(jump1);
        jumping.add(jump2);
        jumping.add(jump3);
        jumping.add(jump4);
        jumping.add(jump5);
        jumping.add(jump6);
        jumping.add(jump7);
        ryuJump = (new Animation<Texture>(0.1f,jumping));

        //mario dying contsruction

        Texture dead1 = new Texture("die1.png");
        Texture dead2 = new Texture("die2.png");
        Texture dead3 = new Texture("die3.png");
        Texture dead4 = new Texture("die4.png");
        Texture dead5 = new Texture("die5.png");
        Texture dead6 = new Texture("die6.png");
        Texture dead7 = new Texture("die7.png");
        Texture dead8 = new Texture("die8.png");
        Texture dead9 = new Texture("die9.png");
        Texture dead10 = new Texture("die10.png");

        Array<Texture> dying = new Array<Texture>(10);

        dying.add(dead1);
        dying.add(dead2);
        dying.add(dead3);
        dying.add(dead4);
        dying.add(dead5);
        dying.add(dead6);
        dying.add(dead7);
        dying.add(dead8);
        dying.add(dead9);
        dying.add(dead10);

        ryuDead = new Animation<Texture>(0.2f,dying);
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() /2, b2body.getPosition().y - getHeight() /2);
        setRegion(getFrame(dt));

    }


    public Texture getFrame(float dt){
        currentState = getState();

        TextureRegion region;
        Texture texture = new Texture("running1.png");

        switch(currentState){
            case DEAD:
                texture = ryuDead.getKeyFrame(stateTimer, false);
            break;

            case JUMPING:
                texture =  ryuJump.getKeyFrame(stateTimer, false);
                break;

            case RUNNING:
                texture = ryuRun.getKeyFrame(stateTimer, true);
                break;

            case FALLING:

            case STANDING:

            default:
                texture = ryuStand;
                break;
        }
     //   if((b2body.getLinearVelocity().x < 0 || !runningRight) && !texture.isFlipX()){
       //     texture.
       //     runningRight = false;
      //  }
      //  else if((b2body.getLinearVelocity().x > 0 || runningRight) && texture.flipX()) {
       //     region.flip(true, false);
       //     runningRight = true;
       // }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return texture;

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


    //health method
    public void health(){
        if(hitCounter == 1){
            health -= damage;
        }
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
            marioIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = NinjaRun.GROUND_BIT;
            for (Fixture fixture : b2body.getFixtureList())
                fixture.setFilterData(filter);
            b2body.applyLinearImpulse(new Vector2(-1f,2f), b2body.getWorldCenter(), true);
            hitCounter = 2;
        }
    }

    public boolean isDead(){
        return marioIsDead;
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
