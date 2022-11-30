package com.deanc.ninjarun.Screens;

import static com.badlogic.gdx.graphics.Color.GOLD;
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
    private TextButton.TextButtonStyle buttonStyle;
    private TextButton resume;
    private Label resumeLabel;
    private TextButton quit;
    private Label quitLabel;


    //Admin
    private NinjaRun game;
    private Screen screen;
    private Viewport viewport;
    private Hud hud;
    private Texture background;




    public PauseScreen(final NinjaRun gameplay){

        game = gameplay;
        screen = gameplay.getScreen();
        hud = gameplay.getHud();
        viewport = new FitViewport(NinjaRun.V_WIDTH, NinjaRun.V_HEIGHT,  new OrthographicCamera());
        stage = new Stage(viewport, game.batch);
        batch = new SpriteBatch();

        background = game.manager.get("background.png",Texture.class);


        //Label set up
        style = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/comic/comic-ui_data/font-button-export.fnt")), RED);
        titleLabel = new Label("PAUSED",style);


        table = new Table ();
        table.center();
        table.setFillParent(true);

        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = new BitmapFont(Gdx.files.internal("skins/comic/comic-ui_data/font-button-export.fnt"));


        resume = new TextButton("resume", buttonStyle);
        resume.setColor(WHITE);
        quit = new TextButton("quit", buttonStyle);
        quit.setColor(WHITE);


        table.add(titleLabel).width(70).height(60).center().padRight(38);
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
                game.loadSound("audio/sounds/mixkit-gear-metallic-lock-sound-2858.wav");
                long id = game.sound.play();
                if(game.getSoundVolume() != 0)
                    game.sound.setVolume(id, game.getSoundVolume());
                else{
                    game.sound.setVolume(id,0);
                }


                game.setScreen(screen);
                hud.resume();
                dispose();

            }
        });

        quit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.loadSound("audio/sounds/mixkit-gear-metallic-lock-sound-2858.wav");
                long id = game.sound.play();
                if(game.getSoundVolume() != 0)
                    game.sound.setVolume(id, game.getSoundVolume());
                else{
                    game.sound.setVolume(id,0);
                }


                game.music.stop();
                game.setScreen(new MenuScreen (game));
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
        batch.begin();
        batch.draw(background,0,0);
        batch.end();
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
