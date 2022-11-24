package com.deanc.ninjarun.Screens;

import static com.badlogic.gdx.graphics.Color.RED;
import static com.badlogic.gdx.graphics.Color.WHITE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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

public class LevelSelect implements Screen {

    //Game
    private NinjaRun GAME ;

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
    private int level = 1;


    public LevelSelect(final NinjaRun game) {
        super();

        //Admin
        GAME = game;
        viewport = new FitViewport(NinjaRun.V_WIDTH, NinjaRun.V_HEIGHT, new OrthographicCamera());
        screen = new Stage(viewport, GAME.batch);

        //Texture
        background = new Texture("Texture3.jpg");

        //Button initialisation
        textStyle = new TextButton.TextButtonStyle();
        buttonFont = new BitmapFont(Gdx.files.internal("skins/arcade/raw/screen-export.fnt"));
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
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/comic/comic-ui_data/font-export.fnt")), RED);
        Label pageLabel = new Label("Level Select", font);
        pageLabel.setFontScale(2);

        //Table
        Table grid = new Table();
        grid.center();
        grid.setFillParent(true);


        //filling table
        grid.add(pageLabel).padLeft(150).padBottom(10);
        grid.row();
        grid.add(level1).expandX().padRight(110).padLeft(50);
        grid.add(level6).expandX().padRight(100);
        grid.row();
        grid.add(level2).expandX().padRight(110).padLeft(50);
        grid.add(level7).expandX().padRight(100);
        grid.row();
        grid.add(level3).expandX().padRight(110).padLeft(50);
        grid.add(level8).expandX().padRight(100);
        grid.row();
        grid.add(level4).expandX().padRight(110).padLeft(50);
        grid.add(level9).expandX().padRight(100);
        grid.row();
        grid.add(level5).expandX().padRight(110).padLeft(50);
        grid.add(level10).expandX().padRight(100);
        grid.row();
        grid.add(backButton).expandX().padTop(10).padLeft(150);
        screen.addActor(grid);
        Gdx.input.setInputProcessor(screen);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GAME.music.stop();
                GAME.setScreen(new MenuScreen(GAME));
            }
        });

        level1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                GAME.setScreen(new PlayScreen(GAME,1));
                GAME.music.stop();
            }
        });

        level2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                GAME.setScreen(new PlayScreen(GAME,2));
                GAME.music.stop();
            }
        });

        level3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                GAME.setScreen(new PlayScreen(GAME,3));
                GAME.music.stop();
            }
        });

        level4.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                GAME.setScreen(new PlayScreen(GAME,4));
                GAME.music.stop();
            }
        });

        level5.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                GAME.setScreen(new PlayScreen(GAME,5));
                GAME.music.stop();
            }
        });

        level6.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                GAME.setScreen(new PlayScreen(GAME,6));
                GAME.music.stop();
            }
        });

        level7.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                GAME.setScreen(new PlayScreen(GAME,7));
                GAME.music.stop();
            }
        });

        level8.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                GAME.setScreen(new PlayScreen(GAME,8));
                GAME.music.stop();
            }
        });

        level9.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                GAME.setScreen(new PlayScreen(GAME,9));
                GAME.music.stop();
            }
        });

        level10.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                GAME.setScreen(new PlayScreen(GAME,10));
                GAME.music.stop();
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        GAME.batch.begin();
        GAME.batch.draw(background,0,0);
        GAME.batch.end();
        screen.draw();
    }

    @Override
    public void resize(int width, int height) {
        screen.getViewport().update(width,height,true);

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
        screen.dispose();
        GAME.dispose();
        background.dispose();
    }


}
