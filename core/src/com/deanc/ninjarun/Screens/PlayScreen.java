package com.deanc.ninjarun.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.deanc.ninjarun.NinjaRun;
import com.deanc.ninjarun.Scenes.Hud;
import com.deanc.ninjarun.Sprites.Enemies.Enemy;
import com.deanc.ninjarun.Sprites.Items.Coins;
import com.deanc.ninjarun.Sprites.Items.Item;
import com.deanc.ninjarun.Sprites.Items.ItemDef;
import com.deanc.ninjarun.Sprites.Items.health;
import com.deanc.ninjarun.Sprites.Ryu;
import com.deanc.ninjarun.Tools.B2WorldCreator;
import com.deanc.ninjarun.Tools.Controller;
import com.deanc.ninjarun.Tools.WorldContactListener;

import java.util.concurrent.LinkedBlockingQueue;

public class PlayScreen implements Screen {
    private NinjaRun game;
    private TextureAtlas atlas;

    //basic variables
    private Hud hud;
    private OrthographicCamera gamecam;
    private Viewport gamePort;

    //tiled map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2D Variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    //Player variable
    private Ryu player;


    //Sprite Variable
    private Array<Item> items;
    public LinkedBlockingQueue<ItemDef> itemToSpawn;
    private int coins;

    //finish level variable
    public boolean complete = false;

    //level variable
    private int level = 1;

    //controller creation
    Controller controller;

    public PlayScreen(NinjaRun g, int level) {

        //admin
        atlas = new TextureAtlas("ryu_and_enemies.pack");

        this.game = g;
        this.level = level;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(NinjaRun.V_WIDTH / NinjaRun.PPM, NinjaRun.V_HEIGHT / NinjaRun.PPM, gamecam);
        hud = new Hud(game.batch, game,game.getScreen(),this);
        controller = new Controller();


        mapLoader = new TmxMapLoader();
        map = mapLoader.load("levels/Level"+level+".tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / NinjaRun.PPM);

        //initiating game cam
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

        creator = new B2WorldCreator(this);

        //Player creation
        player = new Ryu(this);

        world.setContactListener(new WorldContactListener());

        NinjaRun.manager.get("audio/music/yoitrax-warrior.mp3", Music.class).play();

        items = new Array<Item>();
        itemToSpawn = new LinkedBlockingQueue<ItemDef>();
        coins = 0;
    }

    public void spawnItem(ItemDef idef) {
        itemToSpawn.add(idef);
    }

    public void handleSpawningItems() {
        if (!itemToSpawn.isEmpty()) {
            ItemDef idef = itemToSpawn.poll();
            if (idef.type == health.class) {
                items.add(new health(this, idef.position.x, idef.position.y));
            }
        }
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt) {
       // int count = 0;  //for jump limiter but not ready yet
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            if (player.currentState != Ryu.State.DEAD) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                    player.b2body.applyLinearImpulse(new Vector2(0, 3f), player.b2body.getWorldCenter(), true);
                    NinjaRun.manager.get("audio/sounds/soundnimja-jump.wav", Sound.class).play();
                }

                if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                    player.attack();
                }


                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2) {
                    player.b2body.applyLinearImpulse(new Vector2(0.3f, 0), player.b2body.getWorldCenter(), true);

                }

                if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2) {
                    player.b2body.applyLinearImpulse(new Vector2(-0.3f, 0), player.b2body.getWorldCenter(), true);
                }
            } else {
                player.b2body.setLinearVelocity(new Vector2(0, 0));
            }
        }
        else if(Gdx.app.getType() == Application.ApplicationType.Android){
            if (player.currentState != Ryu.State.DEAD) {
                if (controller.isUpPressed() == true) {
                    player.b2body.applyLinearImpulse(new Vector2(0, 3f), player.b2body.getWorldCenter(), true);
                    NinjaRun.manager.get("audio/sounds/soundnimja-jump.wav", Sound.class).play();
                }

                if (controller.isDownPressed() == true) {
                    player.attack();
                }


                if (controller.isRightPressed() && player.b2body.getLinearVelocity().x <= 2) {
                    player.b2body.applyLinearImpulse(new Vector2(0.25f, 0), player.b2body.getWorldCenter(), true);

                }

                if (controller.isLeftPressed() && player.b2body.getLinearVelocity().x >= -2) {
                    player.b2body.applyLinearImpulse(new Vector2(-0.25f, 0), player.b2body.getWorldCenter(), true);
                }
            } else {
                player.b2body.setLinearVelocity(new Vector2(0, 0));
            }
        }
    }


    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void update(float dt) {
        handleInput(dt);
        handleSpawningItems();

        world.step(1 / 60f, 6, 2);

        player.update(dt);
        for (Enemy enemy : creator.getNinjas()) {
            enemy.update(dt);
            if (enemy.getX() < player.getX() + 224 / NinjaRun.PPM)
                enemy.b2body.setActive(true);
        }

        for (Item item : creator.getCoins())
            item.update(dt);

        for (Item item : creator.getVials())
            item.update(dt);


        hud.update(dt);

        if (player.currentState != Ryu.State.DEAD) {
            gamecam.position.x = player.b2body.getPosition().x;
        }

        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        update(delta);

        //Clear Game Screen With Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //game map
        renderer.render();

        //box2d debug lines
        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        for (Enemy enemy : creator.getNinjas())
            enemy.draw(game.batch);

        for (Item item : items)
            item.draw(game.batch);

        if (creator.getCoins().notEmpty()) {
            for (Item item : creator.getCoins()) {
                item.draw(game.batch);
            }
        }

        if (creator.getVials().notEmpty()) {
            for (Item item : creator.getVials()) {
                item.draw(game.batch);
            }
        }

        game.batch.end();

        //Set to draw what hud sees
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            controller.draw();
        }

        hud.stage.draw();
        hud.draw(game.batch, delta);

        if (gameOver()) {
            game.setScreen(new GameOverScreen(game, level));
            dispose();
        }

        if (complete == true) {
            if (player.currentState == Ryu.State.COMPLETE && player.getStateTimer() > 1.5) {
                game.setScreen(new LevelComplete(game, level));
                dispose();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        controller.resize(width, height);
    }

    public boolean gameOver() {
        if (player.currentState == Ryu.State.DEAD && player.getStateTimer() > 3) {
            return true;
        } else {
            return false;
        }
    }

    public Ryu getPlayer(){
        return player;
    }

    public void setLevelComplete(boolean level) {
        complete = level;
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {

    }


    public void coins (){
        if(creator.getCoins().size > 0){
            coins = creator.getCoins().size;
        }
    }
    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
