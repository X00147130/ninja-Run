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


    //Joint Variables
    public RevoluteJoint sword;


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
        ryuStand = new TextureRegion(screen.getAtlas().findRegion("Custom Edited - Ninja Gaiden Customs - Ryu Hayabusa"), 0,319,16,16);
        setBounds(0,0,16 / NinjaRun.PPM, 16 / NinjaRun.PPM);
        setRegion(ryuStand);

        //Creating Animation loop for Mario running
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i =1; i<4; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Custom Edited - Ninja Gaiden Customs - Ryu Hayabusa"), i * 7,319,16,16 ));
        ryuRun = new Animation <TextureRegion>(0.1f, frames);
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
        //ryuJump = (new Animation<TextureRegion>(0.1f,jumping));

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

        //ryuDead = new Animation<Texture>(0.2f,dying);

        //Attack Texture
        Texture attack1 = new Texture("attack1.png");
        Texture attack2 = new Texture("attack2.png");
        Texture attack3 = new Texture("attack3.png");
        Texture attack4 = new Texture("attack4.png");
        Texture attack5 = new Texture("attack5.png");

        Array <Texture> attack= new Array<>(5);
        attack.add(attack1);
        attack.add(attack2);
        attack.add(attack3);
        attack.add(attack4);
        attack.add(attack5);

        //ryuAttack = new Animation<Texture>(0.1f,attack);
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
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(5 / PPM,6/ PPM);
        fdef.filter.categoryBits = NinjaRun.RYU_BIT;
        fdef.filter.maskBits = NinjaRun.GROUND_BIT |
                NinjaRun.COIN_BIT |
                NinjaRun.BRICK_BIT|
                NinjaRun.ENEMY_BIT|
                NinjaRun.OBJECT_BIT|
                NinjaRun.ITEM_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);


    //Joint Creation
    //Body for the joint to connect
        BodyDef bdef2 = new BodyDef();
        bdef2.position.set(33 / PPM,32 / PPM);
        bdef2.type = BodyDef.BodyType.DynamicBody;
        arm = world.createBody(bdef);

        FixtureDef fdef2 = new FixtureDef();
        PolygonShape shape1 = new PolygonShape();
        shape1.setAsBox((float)0.1 * PPM,(float)0.2 * PPM);
        fdef2.shape = shape1;
        arm.createFixture(fdef).setUserData(this);

    //Joint
        RevoluteJointDef rDef = new RevoluteJointDef();
        rDef.enableMotor =true;
        rDef.bodyA = b2body;
        rDef.bodyB = arm;
        rDef.collideConnected  = false;
        rDef.localAnchorA.set(2 / PPM,2 / PPM);
        sword = (RevoluteJoint) world.createJoint(rDef);


    //Ryus Head(Used for colliding with bricks
        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / PPM, 6 / PPM), new Vector2(2 / PPM, 6 / PPM));
        fdef.filter.categoryBits=NinjaRun.RYU_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
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
