package com.deanc.ninjarun.Screens;

import static com.badlogic.gdx.graphics.Color.GOLD;
import static com.badlogic.gdx.graphics.Color.RED;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.deanc.ninjarun.NinjaRun;
import com.deanc.ninjarun.Scenes.Hud;

public class PauseScreen implements Screen {

    //Display tools
    private Table table;
    private Stage stage;
    private SpriteBatch batch;

    //Labels and Buttons
    private Label titleLabel;
    private Label.LabelStyle style;
    private TextButton resume;
    private Label resumeLabel;
    private TextButton quit;
    private Label quitLabel;

    //Skin setup
    private Skin skin;


    //Admin
    private NinjaRun game;
    private Screen screen;
    private Viewport viewport;
    private Hud hud;
    //private Texture background;




    public PauseScreen(final NinjaRun gameplay){

        game = gameplay;
        screen = gameplay.getScreen();
        batch = new SpriteBatch();
        hud = gameplay.getHud();
        viewport = new FitViewport(NinjaRun.V_WIDTH, NinjaRun.V_HEIGHT,  new OrthographicCamera());
        stage = new Stage(viewport);

        /*background = new Texture("settings.jpg");*/


        //skin setup
        skin = new Skin(Gdx.files.internal("skins/comic-ui.json"));


        //Label set up
        style = new Label.LabelStyle(new BitmapFont(), RED);
        titleLabel = new Label("PAUSED",style);

        //UI Setup
        resumeLabel = new Label("Resume",skin);
        quitLabel = new Label(" QUIT ",skin);

        table = new Table ();
        table.center();
        table.setFillParent(true);

        resume = new TextButton("resume", skin,"default");
        resume.getLabel().setFontScale(1);
        resume.setColor(RED);
        quit = new TextButton("quit", skin,"default");
        quit.getLabel().setFontScale(1);
        quit.setColor(RED);


        table.add(titleLabel).width(70).height(60).center().padLeft(10);
        table.row();
        table.row();
        table.add(resume).width(110).height(50).center();
        table.row();
        table.add(quit).width(110).height(50).center();

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

        resume.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(screen);
                hud.resume();
                dispose();

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
