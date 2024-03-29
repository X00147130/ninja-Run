package com.deanc.ninjarun.Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.deanc.ninjarun.Screens.PlayScreen;

public abstract class Enemy extends Sprite {

    //world and screen generation
    protected World world;
    protected PlayScreen screen;

    //body
    public Body b2body;

    //movement
    public Vector2 velocity;

    public Enemy(PlayScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x,y);
        defineEnemy();
        velocity = new Vector2(-1.3f, -2);
        b2body.setActive(false);
    }
    protected abstract void defineEnemy();
    public abstract void update(float dt);
    public abstract void attacked();

    public void reverseVelocity(boolean x, boolean y){
        if(x)
            velocity.x = -velocity.x;
        if(y)
            velocity.y = -velocity.y;
    }
}
