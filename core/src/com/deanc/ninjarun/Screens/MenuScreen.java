package com.deanc.ninjarun.Screens;

import static com.badlogic.gdx.graphics.Color.RED;
import static com.badlogic.gdx.graphics.Color.WHITE;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

    private AssetManager manager;
    private Viewport viewport;
    private Stage stage;
    private final NinjaRun GAME ;
    private Texture background;
    private TextureRegion mainBackground;

    //Buttons
    Button playButton;
    Button levelButton;
    Button settingsButton;
    Button quitButton;
    TextButton.TextButtonStyle buttonStyle;
    BitmapFont buttonFont;


    public MenuScreen(final NinjaRun game) {
        this.GAME = game;
        this.manager = NinjaRun.getManager();
        viewport = new FitViewport(NinjaRun.V_WIDTH, NinjaRun.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, GAME.batch);

        //make sure to credit Sebatian Schulz for the art
        background = new Texture("backgroundimg.jpg");
        mainBackground = new TextureRegion(background);
        mainBackground.setRegionHeight(viewport.getScreenHeight());
        mainBackground.setRegionWidth(viewport.getScreenWidth());


        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), RED);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        //Buttons
        buttonStyle = new TextButton.TextButtonStyle();
        buttonFont = new BitmapFont();
        buttonStyle.font = buttonFont;
        buttonStyle.fontColor = WHITE;
        playButton  = new TextButton("Start",buttonStyle );
        levelButton  = new TextButton("Level Select",buttonStyle );
        settingsButton  = new TextButton("Settings",buttonStyle );
        quitButton = new TextButton("Quit",buttonStyle);

        Label titleLabel = new Label(" Ninja Run", font);
        titleLabel.setSize(90,110);
        table.add(titleLabel).expandX().setActorHeight(110);
        table.row();
        table.add(playButton).expandX().padTop(10);
        table.row();
        table.add(levelButton).expandX().padTop(10);
        table.row();
        table.add(settingsButton).expandX().padTop(10);
        table.row();
        table.add(quitButton).expandX().padTop(10);
        table.row();
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
               if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                   GAME.setScreen(new Controls(GAME));
               }else if (Gdx.app.getType() == Application.ApplicationType.Android){
                   GAME.setScreen(new PlayScreen(GAME,1));
               }
                NinjaRun.manager.get("audio/music/yoitrax - Ronin.mp3",Music.class).stop();
            }
        });

        levelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GAME.setScreen(new LevelSelect(GAME));
            }
        });

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GAME.setScreen(new Settings(GAME));
            }
        });

        quitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                System.exit(0);
            }
        });
    }
    @Override
    public void show() {

    }
    @Override
    public void render(float delta){
        NinjaRun.manager.get("audio/music/yoitrax - Ronin.mp3", Music.class).play();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        GAME.batch.begin();
        GAME.batch.draw(mainBackground,0,0);
        GAME.batch.end();
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
    background.dispose();
    }
}
