package com.deanc.ninjarun.Screens;

import static com.badlogic.gdx.graphics.Color.RED;
import static com.badlogic.gdx.graphics.Color.WHITE;

import com.badlogic.gdx.Game;
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
import com.deanc.ninjarun.Sprites.Ryu;

public class LevelSelect implements Screen {

    //Game
    private Game GAME ;

    //buttons
    Button level1;
    Button level2;
    Button level3;
    Button level4;
    Button level5;
    Button level6;
    Button level7;
    Button level8;
    Button level9;
    Button level10;
    Button backButton;

    TextButton.TextButtonStyle textStyle;
    BitmapFont buttonFont;


    //Background
    Texture background;

    //admin
    private Viewport viewport;
    private Stage screen;
    private SpriteBatch batch;
    private Ryu player;
    private int level = 1;


    public LevelSelect(final Game game) {
        super();

        //Admin
        GAME = game;
        viewport = new FitViewport(NinjaRun.V_WIDTH, NinjaRun.V_HEIGHT, new OrthographicCamera());
        screen = new Stage(viewport, ((NinjaRun) game).batch);
        batch = new SpriteBatch();

        NinjaRun.manager.get("audio/music/yoitrax-ronin.mp3",Music.class).play();

        //Texture
        //background = new Texture("Texture1.jpg");

        //Button initialisation
        textStyle = new TextButton.TextButtonStyle();
        buttonFont = new BitmapFont();
        textStyle.font = buttonFont;
        textStyle.fontColor = WHITE;

        level1 = new TextButton("Level 1", textStyle);
        level2 = new TextButton("Level 2", textStyle);
        level3 = new TextButton("Level 3", textStyle);
        level4 = new TextButton("Level 4", textStyle);
        level5 = new TextButton("Level 5", textStyle);
        level6 = new TextButton("Level 6", textStyle);
        level7 = new TextButton("Level 7", textStyle);
        level8 = new TextButton("Level 8", textStyle);
        level9 = new TextButton("Level 9", textStyle);
        level10 = new TextButton("Level 10", textStyle);
        backButton = new TextButton("Back", textStyle);

        //Label
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), RED);
        Label pageLabel = new Label("Level Select", font);

        //Table
        Table grid = new Table();
        grid.center();
        grid.setFillParent(true);

        //filling table
        grid.add(pageLabel).expandX().padLeft(150);
        grid.row();
        grid.add(level1).expandX().padRight(90).padLeft(50);
        grid.add(level6).expandX().padRight(50);
        grid.row();
        grid.add(level2).expandX().padRight(90).padLeft(50);
        grid.add(level7).expandX().padRight(50);
        grid.row();
        grid.add(level3).expandX().padRight(90).padLeft(50);
        grid.add(level8).expandX().padRight(50);
        grid.row();
        grid.add(level4).expandX().padRight(90).padLeft(50);
        grid.add(level9).expandX().padRight(50);
        grid.row();
        grid.add(level5).expandX().padRight(90).padLeft(50);
        grid.add(level10).expandX().padRight(50);
        grid.row();
        grid.add(backButton).expandX().padTop(10).padLeft(150);
        screen.addActor(grid);
        Gdx.input.setInputProcessor(screen);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GAME.setScreen(new MenuScreen((NinjaRun) GAME));
            }
        });

        level1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                GAME.setScreen(new PlayScreen((NinjaRun)GAME,1));
                NinjaRun.manager.get("audio/music/yoitrax-ronin.mp3",Music.class).stop();
            }
        });

        level2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                GAME.setScreen(new PlayScreen((NinjaRun)GAME,2));
                NinjaRun.manager.get("audio/music/yoitrax-ronin.mp3",Music.class).stop();
            }
        });

        level3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                GAME.setScreen(new PlayScreen((NinjaRun)GAME,3));
                NinjaRun.manager.get("audio/music/yoitrax-ronin.mp3",Music.class).stop();
            }
        });

        level4.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                GAME.setScreen(new PlayScreen((NinjaRun)GAME,4));
                NinjaRun.manager.get("audio/music/yoitrax-ronin.mp3",Music.class).stop();
            }
        });

        level5.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                GAME.setScreen(new PlayScreen((NinjaRun)GAME,5));
                NinjaRun.manager.get("audio/music/yoitrax-ronin.mp3",Music.class).stop();
            }
        });

        level6.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                GAME.setScreen(new PlayScreen((NinjaRun)GAME,6));
                NinjaRun.manager.get("audio/music/yoitrax-ronin.mp3",Music.class).stop();
            }
        });

        level7.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                GAME.setScreen(new PlayScreen((NinjaRun)GAME,1));
                NinjaRun.manager.get("audio/music/yoitrax-ronin.mp3",Music.class).stop();
            }
        });

        level8.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                GAME.setScreen(new PlayScreen((NinjaRun)GAME,1));
                NinjaRun.manager.get("audio/music/yoitrax-ronin.mp3",Music.class).stop();
            }
        });

        level9.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                GAME.setScreen(new PlayScreen((NinjaRun)GAME,1));
                NinjaRun.manager.get("audio/music/yoitrax-ronin.mp3",Music.class).stop();
            }
        });

        level10.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                GAME.setScreen(new PlayScreen((NinjaRun)GAME,1));
                NinjaRun.manager.get("audio/music/yoitrax-ronin.mp3",Music.class).stop();
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        screen.draw();
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
    public void show() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        screen.dispose();
        GAME.dispose();
        background.dispose();
    }


}
