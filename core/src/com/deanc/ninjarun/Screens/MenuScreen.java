package com.deanc.ninjarun.Screens;

import static com.badlogic.gdx.graphics.Color.RED;
import static com.badlogic.gdx.graphics.Color.WHITE;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
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


public class MenuScreen implements Screen  {

    private Viewport viewport;
    private Stage stage;
    private final Game GAME ;

    //Buttons
    Button playButton;
    Button quitButton;
    TextButton.TextButtonStyle buttonStyle;
    BitmapFont buttonFont;


    public MenuScreen(final Game game) {
        this.GAME = game;
        viewport = new FitViewport(NinjaRun.V_WIDTH, NinjaRun.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((NinjaRun) game).batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), RED);

        Table table = new Table();
        table.center();
        table.setFillParent(true);


        //Buttons
        buttonStyle = new TextButton.TextButtonStyle();
        buttonFont = new BitmapFont();
        buttonStyle.font = buttonFont;
        buttonStyle.fontColor = RED;
        playButton  = new TextButton("Play",buttonStyle );
        quitButton = new TextButton("Quit",buttonStyle);

        Label titleLabel = new Label(" Ninja Run", font);
        table.add(titleLabel).expandX();
        table.row();
        table.add(playButton).expandX().padTop(10);
        table.row();
        table.add(quitButton).expandX().padTop(20);
        table.row();
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GAME.setScreen(new PlayScreen((NinjaRun)GAME));
                NinjaRun.manager.get("audio/music/yoitrax-ronin.mp3",Music.class).stop();
            }
        });

        quitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                System.exit(-1);
            }
        });
    }
    @Override
    public void show() {

    }
    @Override
    public void render(float delta){
            NinjaRun.manager.get("audio/music/yoitrax-ronin.mp3", Music.class).play();
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            stage.draw();
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
