package com.deanc.ninjarun.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.deanc.ninjarun.NinjaRun;

public class LogoScreen implements Screen {
    private final Game GAME;
    private SpriteBatch batch;
    private Viewport viewport;
    private Texture background;
    private Image backgroundIMG;
    private Stage stage;
    private float timeSeconds = 0f;
    private float period = 6f;

    public LogoScreen(Game game) {
        this.GAME = game;
        viewport = new FitViewport(NinjaRun.V_WIDTH, NinjaRun.V_HEIGHT, new OrthographicCamera());
        batch = new SpriteBatch();
        stage = new Stage(viewport);

        background = new Texture("logo_size_invert.jpg");
        backgroundIMG = new Image(background);
        backgroundIMG.setHeight(100);
        backgroundIMG.setWidth(100);

        Table table = new Table();
        table.setFillParent(true);

        table.add();
        table.add();
        table.add();
        table.row().pad(5,5,5,5);
        table.add();
        table.add(backgroundIMG);
        table.add();
        table.row().pad(5,5,5,5);
        table.add();
        table.add();
        table.add();


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

            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        }stage.draw();
    }
    public void handleEvent(){
        GAME.setScreen(new MenuScreen(GAME));
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
