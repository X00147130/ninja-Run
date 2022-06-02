package com.deanc.ninjarun.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.deanc.ninjarun.NinjaRun;
import com.deanc.ninjarun.Screens.PauseScreen;
import com.deanc.ninjarun.Screens.PlayScreen;
import com.deanc.ninjarun.Sprites.Items.Coins;
import com.deanc.ninjarun.Sprites.Items.Item;
import com.deanc.ninjarun.Sprites.Ryu;

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    private int worldTimer;
    private Coins coin;

    Label coinpouchLabel;

    Label timeLabel;

    //health bar
    private ShapeRenderer border;
    private ShapeRenderer background;
    private ShapeRenderer health;
    Label healthLabel;
    Label coinLabel;
    static private boolean projectionMatrixSet;


    //Image Button Variable
    private ImageButton pause;
    private Texture image;
    private Drawable draw;
    private NinjaRun gameplay;
    public final Screen play;
    private boolean isPaused = false;


    public Hud(SpriteBatch sb, final NinjaRun game, final Screen paused){
        worldTimer=250;
        gameplay = game;
        play = paused;


        //Image button
        image = new Texture("pause.png");
        draw = new TextureRegionDrawable(image);
        pause = new ImageButton(draw);


        viewport = new FitViewport(NinjaRun.V_WIDTH,NinjaRun.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        coinpouchLabel = new Label(String.format("%03d",worldTimer), new Label.LabelStyle(new BitmapFont(), Color.RED));
        timeLabel = new Label("TIME:", new Label.LabelStyle(new BitmapFont(), Color.RED));
        healthLabel = new Label("HEALTH", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        coinLabel = new Label("COINS:", new Label.LabelStyle(new BitmapFont(), Color.GOLD));

        //group for health label scaling

        table.add(healthLabel).expandX().left().padLeft(20).top();
        table.add(coinLabel).padRight(10).right().top();
        table.add(coinpouchLabel).padRight(10).right().top().spaceRight(11);

        pause.setPosition(175,165);
        pause.setSize(50,50);
        stage.addActor(table);
        stage.addActor(pause);



        pause.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameplay.setScreen(new PauseScreen(gameplay));
            }
       });

        // health bar initialisation
        border = new ShapeRenderer();
        background = new ShapeRenderer();
        health = new ShapeRenderer();
        projectionMatrixSet = false;


        Gdx.input.setInputProcessor(stage);
    }

    public void update(float dt) {
    }

    public void draw(SpriteBatch batch, float alpha){
        if(!projectionMatrixSet){
            border.setProjectionMatrix(batch.getProjectionMatrix());
            health.setProjectionMatrix(batch.getProjectionMatrix());
            background.setProjectionMatrix(batch.getProjectionMatrix());
        }
        border.begin(ShapeRenderer.ShapeType.Filled);
        border.setColor(Color.WHITE);
        border.rect(4,184,101,8);
        border.end();

        background.begin(ShapeRenderer.ShapeType.Filled);
        background.setColor(Color.RED);
        background.rect(5, 185, 99, 6);
        background.end();

        health.begin(ShapeRenderer.ShapeType.Filled);
        health.setColor(Color.GREEN);
        if(Ryu.getHitCounter() == 0) {
            health.rect(5, 185, 99, 6);
        }
        else if (Ryu.getHitCounter() == 1){
            health.rect(5,185,66,6);
        }
        else if (Ryu.getHitCounter() == 2){
            health.rect(5,185,33,6);
        }
        else if (Ryu.getHitCounter() == 3){
            health.rect(5,185,0,6);
        }
        health.end();



    }
    public Screen getPlayScreen(){
        return play;
    }


    @Override
    public void dispose() {
        stage.dispose();
    }
}
