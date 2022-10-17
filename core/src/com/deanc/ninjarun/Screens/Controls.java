package com.deanc.ninjarun.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.deanc.ninjarun.NinjaRun;

public class Controls implements Screen {

    private final Game GAME;
    private SpriteBatch batch;
    private Viewport viewport;
//    private Texture background;
//    private Image backgroundIMG;

    /*Labels*/
    private Label title;
    private Label pause;
    private Label forward;
    private Label backward;
    private Label attack;
    private Label jump;

    /*labelStyle*/
    private Label.LabelStyle titleStyle;
    private Label.LabelStyle style;

    private Stage stage;
    private float timeSeconds = 0f;
    private float period = 8f;

    public Controls(Game game) {
        this.GAME = game;
        viewport = new FitViewport(NinjaRun.V_WIDTH, NinjaRun.V_HEIGHT, new OrthographicCamera());
        batch = new SpriteBatch();
        stage = new Stage(viewport);

        /*Label Style*/
        titleStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        style = new Label.LabelStyle(new BitmapFont(), Color.GOLD);

        /*Labels*/
        title = new Label("Controls",titleStyle);

        /*Desktop*/
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            pause = new Label("Pause: esc ", style);
            forward = new Label("Forward: Right Arrow Key", style);
            backward = new Label("Backward: Left Arrow Key", style);
            attack = new Label("Attack: Space Bar ", style);
            jump = new Label("Jump: Up Arrow Key", style);
        }

        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            pause = new Label("Pause: Pause Button ", style);
            forward = new Label("Forward: Right Arrow ", style);
            backward = new Label("Backward: Left Arrow ", style);
            attack = new Label("Attack: Attack Button ", style);
            jump = new Label("Jump: Up Arrow ", style);
        }

//        background = new Texture("logos/logo_white_background.jpg");
//        backgroundIMG = new Image(background);

        Table table = new Table();
        table.setFillParent(true);

        table.add(title).center().top().padBottom(20);
        table.row();
        table.row();
        table.add(forward).center();
        table.row();
        table.add(backward).center();
        table.row();
        table.add(jump).center();
        table.row();
        table.add(attack).center();
        table.row();
        table.add(pause).center();

//        table.add(backgroundIMG);


        stage.addActor(table);


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        timeSeconds += Gdx.graphics.getRawDeltaTime();
        if (timeSeconds > period) {
            timeSeconds -= period;
            handleEvent();
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }
    public void handleEvent(){
        GAME.setScreen(new PlayScreen ((NinjaRun)GAME,1));
        dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        stage.dispose();
    }
}


