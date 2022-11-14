package com.deanc.ninjarun.Screens;

import static com.badlogic.gdx.graphics.Color.RED;
import static com.badlogic.gdx.graphics.Color.WHITE;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.deanc.ninjarun.NinjaRun;


public class GameOverScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private final NinjaRun GAME;
    private AssetManager manager;
    public boolean reset = false;

    private int map = 1;


    //buttons
    private Button playAgainButton;
    private Button mainMenuButton;
    private TextButton.TextButtonStyle buttonStyle;
    private BitmapFont buttonFont;

    public GameOverScreen(NinjaRun game, int level){
        this.manager = NinjaRun.getManager();
        this.GAME = game;
        viewport = new FitViewport(NinjaRun.V_WIDTH, NinjaRun.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, GAME.batch);
        this.map = level;


        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), RED);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        buttonStyle = new TextButton.TextButtonStyle();
        buttonFont = new BitmapFont();
        buttonStyle.font= buttonFont;
        buttonStyle.fontColor = WHITE;
        playAgainButton = new TextButton(" Play Again? ", buttonStyle);
        mainMenuButton = new TextButton(" Main Menu ", buttonStyle);


        Label gameOverLabel = new Label(" YOU DIED ", font);
        table.add(gameOverLabel).expandX();
        table.row();
        table.add(playAgainButton).expandX().padTop(10);
        table.row();
        table.add(mainMenuButton).expandX().padTop(10);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);


       // makes button take us to new playscreen
        playAgainButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                GAME.setScreen(new PlayScreen(GAME,map));
                NinjaRun.manager.get("audio/music/mixkit-piano-horror-671.mp3", Music.class).stop();
            }
        });

        //Makes Button take us to main menu
        mainMenuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                GAME.setScreen(new MenuScreen((NinjaRun)GAME));
                NinjaRun.manager.get("audio/music/mixkit-piano-horror-671.mp3", Music.class).stop();
                GAME.music.stop();
            }
        });

    }

    public boolean isReset() {
        return reset;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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

    }
}
