package com.deanc.ninjarun.Scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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
import com.deanc.ninjarun.Screens.MenuScreen;
import com.deanc.ninjarun.Screens.PauseScreen;
import com.deanc.ninjarun.Screens.PlayScreen;
import com.deanc.ninjarun.Sprites.Ryu;

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    private int worldTimer;
    private float timeCount;

    Label countdownLabel;

    Label timeLabel;
    private SpriteBatch batch;
    //health bar

    private ShapeRenderer border;
    private ShapeRenderer background;
    private ShapeRenderer health;
    Label healthLabel;
    Label coinLabel;
    Group group;
    static private boolean projectionMatrixSet;
    //Image Button Variable

    private ImageButton pause;
    private Texture image;
    private Drawable draw;
    private NinjaRun gameplay;

    public Hud(SpriteBatch sb, final NinjaRun game){
        worldTimer=250;
        gameplay = game;

        //Image button
        image = new Texture("pause.png");
        draw = new TextureRegionDrawable(image);
        pause = new ImageButton(draw);


        viewport = new FitViewport(NinjaRun.V_WIDTH,NinjaRun.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%03d",worldTimer), new Label.LabelStyle(new BitmapFont(), Color.RED));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.RED));
        healthLabel = new Label("HEALTH:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        coinLabel = new Label("COINS:", new Label.LabelStyle(new BitmapFont(), Color.GOLD));

        //group for health label scaling
        group = new Group();
        group.addActor(healthLabel);
        group.addAction(Actions.sequence(Actions.scaleTo(2f,2f,1f), Actions.scaleTo(1f,1f,1f)));

        table.add(timeLabel).expandX().padTop(10).right().padRight(20);
        table.row();
        table.add(countdownLabel).expandX().right().padRight(24);
        table.row();


        stage.addActor(table);
        stage.addActor(pause);
        pause.setPosition(0,165);
        pause.setSize(30,30);

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

    public void update(float dt){
        timeCount += dt;
        if(timeCount >= 1){
            worldTimer --;
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }

    public void draw(SpriteBatch batch, float alpha){
        if(!projectionMatrixSet){
            border.setProjectionMatrix(batch.getProjectionMatrix());
            health.setProjectionMatrix(batch.getProjectionMatrix());
            background.setProjectionMatrix(batch.getProjectionMatrix());
        }
        border.begin(ShapeRenderer.ShapeType.Filled);
        border.setColor(Color.WHITE);
        border.rect(4,193,72,8);
        border.end();

        background.begin(ShapeRenderer.ShapeType.Filled);
        background.setColor(Color.RED);
        background.rect(5, 194, 70, 6);
        background.end();

        health.begin(ShapeRenderer.ShapeType.Filled);
        health.setColor(Color.GREEN);
        if(Ryu.getHitCounter() == 0) {
            health.rect(5, 194, 70, 6);
        }
        else if (Ryu.getHitCounter() == 1){
            health.rect(5,194,35,6);
        }
        else if (Ryu.getHitCounter() ==2){
            health.rect(5,194,0,6);
        }
        health.end();



    }
    @Override
    public void dispose() {
        stage.dispose();
    }
}
