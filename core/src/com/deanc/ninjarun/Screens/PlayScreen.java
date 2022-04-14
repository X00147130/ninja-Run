package com.deanc.ninjarun.Screens;

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
import com.deanc.ninjarun.Sprites.Enemies.Ninja;
import com.deanc.ninjarun.Sprites.Items.Item;
import com.deanc.ninjarun.Sprites.Items.ItemDef;
import com.deanc.ninjarun.Sprites.Items.health;
import com.deanc.ninjarun.Sprites.Ryu;
import com.deanc.ninjarun.Tools.B2WorldCreator;
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

    //Audio variables
    private Music music;

    //Sprite Variable
    private Array<Item> items;
    private LinkedBlockingQueue<ItemDef> itemToSpawn;

    public PlayScreen(NinjaRun game) {
        atlas = new TextureAtlas("ryu_and_enemies.pack");

        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(NinjaRun.V_WIDTH / NinjaRun.PPM, NinjaRun.V_HEIGHT / NinjaRun.PPM, gamecam);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("lvl1-1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / NinjaRun.PPM);

        //initiating game cam
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

        creator = new B2WorldCreator(this);

        //Player creation
        player = new Ryu(this);

        world.setContactListener(new WorldContactListener());

        music = NinjaRun.manager.get("audio/music/yoitrax-warrior.mp3", Music.class);
        music.setLooping(true);
        music.play();

        items = new Array<Item>();
        itemToSpawn = new LinkedBlockingQueue<ItemDef>();
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
        if (player.currentState != Ryu.State.DEAD) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                player.b2body.applyLinearImpulse(new Vector2(0, 3f), player.b2body.getWorldCenter(), true);
                NinjaRun.manager.get("audio/sounds/soundnimja-jump.wav", Sound.class).play();
            }

            if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                player.attack();
            }


            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
                player.b2body.applyLinearImpulse(new Vector2(0.5f, 0), player.b2body.getWorldCenter(), true);

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
                player.b2body.applyLinearImpulse(new Vector2(-0.5f, 0), player.b2body.getWorldCenter(), true);
        }
    }

    public void update(float dt){
        handleInput(dt);
        handleSpawningItems();

        world.step(1/60f, 6, 2);

        player.update(dt);
        for(Enemy enemy :  creator.getGoombas()) {
            enemy.update(dt);
            if(enemy.getX() <player.getX() + 224 / NinjaRun.PPM)
                enemy.b2body.setActive(true);
        }

        for(Item item : items)
            item.update(dt);

        hud.update(dt);
        if(player.currentState != Ryu.State.DEAD) {
            gamecam.position.x = player.b2body.getPosition().x;
        }

        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        update(delta);

        //Clear Game Screen With Black
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //game map
        renderer.render();

        //box2d debug lines
        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        for(Enemy enemy :  creator.getGoombas())
            enemy.draw(game.batch);

        for(Item item : items)
            item.draw(game.batch);

        game.batch.end();

        //Set to draw what hud sees
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        hud.draw(game.batch,delta );

        if(gameOver()){
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
    }


    public boolean gameOver(){
        if(player.currentState == Ryu.State.DEAD && player.getStateTimer() > 3){
            return true;
        }
        else{
            return false;
        }
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

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
