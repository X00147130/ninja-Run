package com.deanc.ninjarun.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.deanc.ninjarun.NinjaRun;
import com.deanc.ninjarun.Screens.PlayScreen;
import com.deanc.ninjarun.Sprites.Ryu;

public class Hud implements Disposable {

    public Stage stage;
    private Viewport viewport;

    private int coinPouch;

    Label coinpouchLabel;

    Label timeLabel;

    //health bar
    private ShapeRenderer border;
    private ShapeRenderer background;
    private ShapeRenderer health;
    Label healthLabel;
    Label coinLabel;
    private Skin skin;

    static private boolean projectionMatrixSet;

    private NinjaRun gameplay;
    public final Screen play;
    private PlayScreen playScreen;


    public Hud(SpriteBatch sb, final NinjaRun game, final Screen paused, final PlayScreen playScreen){
        gameplay = game;
        play = paused;

        this.playScreen = playScreen;

        coinPouch = playScreen.getCoins();
        viewport = new FitViewport(NinjaRun.V_WIDTH,NinjaRun.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        skin = new Skin(Gdx.files.internal("skins/comic/comic-ui.json"));
        coinpouchLabel = new Label(String.format("%04d",coinPouch), new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/arcade/raw/screen-export.fnt")), Color.RED));
        timeLabel = new Label("TIME:", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/arcade/raw/screen-export.fnt")), Color.RED));
        healthLabel = new Label("HEALTH:", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/arcade/raw/screen-export.fnt")), Color.WHITE));
        coinLabel = new Label("COINS:", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/arcade/raw/screen-export.fnt")), Color.GOLD));

        //group for health label scaling

        table.add(healthLabel).expandX().left().padLeft(20).top();
        table.add(coinLabel).padRight(10).right().top();
        table.add(coinpouchLabel).padRight(10).right().top().spaceRight(11);


              stage.addActor(table);



        // health bar initialisation
        border = new ShapeRenderer();
        background = new ShapeRenderer();
        health = new ShapeRenderer();
        projectionMatrixSet = false;


    }

    public void update(float dt) {
        coinPouch = playScreen.getCoins();
        coinpouchLabel.setText(String.format("%04d",coinPouch));
    }

    public void resume(){
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
        background.setColor(Color.BLACK);
        background.rect(5, 185, 99, 6);
        background.end();

        health.begin(ShapeRenderer.ShapeType.Filled);
        if(Ryu.getHitCounter() == 0) {
            health.rect(5, 185, 99, 6);
            health.setColor(Color.GREEN);
        }
        else if (Ryu.getHitCounter() == 1){
            health.rect(5,185,66,6);
            health.setColor(Color.YELLOW);
        }
        else if (Ryu.getHitCounter() == 2){
            health.rect(5,185,33,6);
            health.setColor(Color.RED);
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
