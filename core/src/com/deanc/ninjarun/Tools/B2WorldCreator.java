package com.deanc.ninjarun.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.deanc.ninjarun.NinjaRun;
import com.deanc.ninjarun.Screens.PlayScreen;
import com.deanc.ninjarun.Sprites.Enemies.Ninja;
import com.deanc.ninjarun.Sprites.Items.Coins;
import com.deanc.ninjarun.Sprites.TileObjects.Shuriken;
import com.deanc.ninjarun.Sprites.Items.health;
import com.deanc.ninjarun.Sprites.TileObjects.Barrier;
import com.deanc.ninjarun.Sprites.TileObjects.Platforms;
import com.deanc.ninjarun.Sprites.TileObjects.Finish;
import com.deanc.ninjarun.Sprites.TileObjects.Sky;

public class B2WorldCreator {
    private Array<Ninja> ninjas;
    private Array<Coins> coins;
    private Array<health> vials;
    private Array<Shuriken> shurikens;


    public B2WorldCreator(PlayScreen screen) {

        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        //temp body creation
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //create ground bodies fixtures
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / NinjaRun.PPM, (rect.getY() + rect.getHeight() / 2) / NinjaRun.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / NinjaRun.PPM, rect.getHeight() / 2 / NinjaRun.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //create ground fixtures
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / NinjaRun.PPM, (rect.getY() + rect.getHeight() / 2) / NinjaRun.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / NinjaRun.PPM, rect.getHeight() / 2 / NinjaRun.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = NinjaRun.BARRIER_BIT;
            body.createFixture(fdef);
        }

        //create Platforms fixtures
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {

            // creation of Platform Objects
            new Platforms(screen, object);
        }

        //create finish fixtures
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {

            // creation of end tree object
            new Finish(screen, object);
        }

        // create all ninjas e.g. multiple enemies
        ninjas = new Array<Ninja>();
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            ninjas.add(new Ninja(screen, rect.x / NinjaRun.PPM, rect.y / NinjaRun.PPM));
        }

        //create health fixtures
        vials = new Array<health>();
        for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            // creation of health vials objects
            vials.add(new health( screen,rect.x / NinjaRun.PPM, rect.y / NinjaRun.PPM));
        }

        //create Coins fixtures
        coins = new Array<Coins>();
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            // creation of coin objects
            coins.add(new Coins(screen,rect.x / NinjaRun.PPM, rect.y / NinjaRun.PPM));

        }

        //Create barriers
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            new Barrier(screen,object);
        }

        //Sky limit
        for(MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)){
            new Sky(screen,object);
        }
        //create Coins fixtures
        shurikens = new Array<Shuriken>();
        for (MapObject object : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)) {
            shurikens.add(new Shuriken(screen,object));
        }

    }

    public Array<Ninja> getNinjas() {
        return ninjas;
    }
    public Array<health> getVials(){return vials;}
    public Array<Coins> getCoins(){return coins;}
    public Array<Shuriken> getShurikens(){return shurikens;}

}
