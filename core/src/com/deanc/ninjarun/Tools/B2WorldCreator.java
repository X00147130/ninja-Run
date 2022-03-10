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
import com.deanc.ninjarun.Sprites.TileObjects.Brick;
import com.deanc.ninjarun.Sprites.TileObjects.coin;

public class B2WorldCreator {
    private Array<Ninja> goombas;



    public B2WorldCreator(PlayScreen screen) {

        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        //temp body creation
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //create ground bodies/fixtures
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / NinjaRun.PPM, (rect.getY() + rect.getHeight() / 2) / NinjaRun.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / NinjaRun.PPM, rect.getHeight() / 2 / NinjaRun.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //create ground pipes/fixtures
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / NinjaRun.PPM, (rect.getY() + rect.getHeight() / 2) / NinjaRun.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / NinjaRun.PPM, rect.getHeight() / 2 / NinjaRun.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = NinjaRun.OBJECT_BIT;
            body.createFixture(fdef);
        }

        //create ground bricks/fixtures
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {

            // creation of Brick Object
            new Brick(screen, object);
        }

        //create ground coins/fixtures
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {

            // creation of coin object
            new coin(screen, object);
        }

        // create all goombas e.g. multiple enemies
        goombas = new Array<Ninja>();
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            goombas.add(new Ninja(screen, rect.x / NinjaRun.PPM, rect.y / NinjaRun.PPM));
        }
    }

    public Array<Ninja> getGoombas() {
        return goombas;
    }
}
