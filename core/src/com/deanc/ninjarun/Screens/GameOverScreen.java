package com.deanc.ninjarun.Screens;

import static com.badlogic.gdx.graphics.Color.RED;
import static com.badlogic.gdx.graphics.Color.WHITE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
    private NinjaRun GAME;
    private Table table;
    public boolean reset = false;

    private int map = 1;

    private Texture background;

    //buttons
    private Button playAgainButton;
    private Button mainMenuButton;

    public GameOverScreen(NinjaRun game, int level){
        this.GAME = game;
        viewport = new FitViewport(NinjaRun.V_WIDTH, NinjaRun.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, GAME.batch);
        this.map = level;

        background = GAME.manager.get("background.png", Texture.class);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/arcade/raw/font-export.fnt")), RED);

        table = new Table();
        table.center();
        table.setFillParent(true);

        BitmapFont buttonFont;
        TextButton.TextButtonStyle buttonStyle;
        buttonStyle = new TextButton.TextButtonStyle();
        buttonFont = new BitmapFont(Gdx.files.internal("skins/arcade/raw/screen-export.fnt"));
        buttonStyle.font= buttonFont;
        buttonStyle.fontColor = WHITE;
        playAgainButton = new TextButton(" Play Again? ", buttonStyle);
        mainMenuButton = new TextButton(" Main Menu ", buttonStyle);


        Label gameOverLabel = new Label(" YOU DIED!!!!! ", font);
        table.add(gameOverLabel).expandX().center();
        table.row();
        table.row();
        table.add(playAgainButton).expandX().padTop(10).center();
        table.row();
        table.add(mainMenuButton).expandX().padTop(10).center();
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);


       // makes button take us to new playscreen
        playAgainButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                GAME.setScreen(new PlayScreen(GAME,map));
            }
        });

        //Makes Button take us to main menu
        mainMenuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                GAME.setScreen(new MenuScreen(GAME));
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
        GAME.batch.begin();
        GAME.batch.draw(background,0,0);
        GAME.batch.end();
        stage.draw();

        GAME.loadMusic("audio/music/mixkit-piano-horror-671.mp3");
        if(GAME.getVolume() != 0) {
            GAME.music.setVolume(GAME.getVolume());
            GAME.music.play();
        }

        if(mainMenuButton.isPressed() || playAgainButton.isPressed()) {
            GAME.music.stop();
        }
    }




    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,true);
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
        GAME.dispose();
    }
}
