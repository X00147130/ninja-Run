package com.deanc.ninjarun.Screens;

import static com.badlogic.gdx.graphics.Color.RED;
import static com.badlogic.gdx.graphics.Color.WHITE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

public class PauseScreen implements Screen {

    //Display tools
    private Table table;
    private Stage stage;
    private SpriteBatch batch;

    //Labels and Buttons
    private Label titleLabel;
    private Label.LabelStyle style;
    private Button resume;
    private Button quit;
    private BitmapFont font;
    private TextButton.TextButtonStyle buttonStyle;


    //Admin
    private NinjaRun game;
    private Screen screen;
    private Viewport viewport;
    //private Texture background;




    public PauseScreen(NinjaRun gameplay){

        game = gameplay;
        screen = gameplay.getScreen();
        viewport = new FitViewport(NinjaRun.V_WIDTH, NinjaRun.V_HEIGHT,  new OrthographicCamera());
        stage = new Stage(viewport);
        batch = new SpriteBatch();
        /*background = new Texture("settings.jpg");*/

        //Label set up
        style = new Label.LabelStyle(new BitmapFont(), RED);
        titleLabel = new Label("PAUSED",style);

        //Button Set Up
        font = new BitmapFont();
        buttonStyle= new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.fontColor = WHITE;

        resume = new TextButton("RESUME",buttonStyle);
        quit = new TextButton("QUIT",buttonStyle);


        table = new Table ();
        table.center();
        table.setFillParent(true);

        table.add(titleLabel).width(70).height(60).center().padLeft(10);
        table.row();
        table.row();
        table.add(resume).width(30).height(20).center();
        table.row();
        table.add(quit).width(30).height(20).center();

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);


        resume.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(screen);
            }
        });

        quit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new MenuScreen (game));
                NinjaRun.manager.get("audio/music/yoitrax-warrior.mp3",Music.class).stop();
            }
        });

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        /*batch.begin();
        batch.draw(background,0,0);
        batch.end();*/
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
    public void resume(){
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
