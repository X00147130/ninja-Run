package com.deanc.ninjarun.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.deanc.ninjarun.NinjaRun;
import com.deanc.ninjarun.Sprites.Ryu;

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    private int worldTimer;
    private float timeCount;


    @Override
    public void dispose() {
        stage.dispose();
    }

    Label countdownLabel;
    Label timeLabel;
    private SpriteBatch batch;

    //health bar
    private ShapeRenderer border;
    private ShapeRenderer background;
    private ShapeRenderer health;
    Label healthLabel;
    Group group;
    static private boolean projectionMatrixSet;

    public Hud(SpriteBatch sb){
        worldTimer=250;

        viewport = new FitViewport(NinjaRun.V_WIDTH,NinjaRun.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%03d",worldTimer), new Label.LabelStyle(new BitmapFont(), Color.RED));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.RED));
        healthLabel = new Label("HEALTH:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //group for health label scaling
        group = new Group();
        group.addActor(healthLabel);
        group.addAction(Actions.sequence(Actions.scaleTo(2f,2f,1f), Actions.scaleTo(1f,1f,1f)));


        table.add(timeLabel).expandX().padTop(10).right().padRight(20);
        table.row();
        table.add(countdownLabel).expandX().right().padRight(24);

        stage.addActor(table);

        // health bar initialisation
        border = new ShapeRenderer();
        background = new ShapeRenderer();
        health = new ShapeRenderer();
        projectionMatrixSet = false;

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

        //batch.begin();
        //group.draw(batch,worldTimer);
        //batch.end();

    }
}
