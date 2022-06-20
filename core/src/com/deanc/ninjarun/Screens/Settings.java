package com.deanc.ninjarun.Screens;

import static com.badlogic.gdx.graphics.Color.RED;
import static com.badlogic.gdx.scenes.scene2d.ui.Table.Debug.actor;
import static com.badlogic.gdx.utils.compression.CRC.Table;
import static com.deanc.ninjarun.NinjaRun.manager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.deanc.ninjarun.NinjaRun;

public class Settings implements Screen {
    private final Game GAME;
    private Viewport viewport;
    private SpriteBatch batch;

    private Button backButton;
    public Button mute;
    private Texture image;
    private TextureRegionDrawable draw;
    Slider music;
    Slider sound;
    Skin skin=new Skin(Gdx.files.internal("skins/comic-ui.json"));

    Stage stage;
    TextButton.TextButtonStyle textStyle;
    BitmapFont buttonFont;

    public Settings(final Game game){
        this.GAME = game;
        viewport = new FitViewport(NinjaRun.V_WIDTH, NinjaRun.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((NinjaRun) game).batch);
        batch = new SpriteBatch();

        textStyle = new TextButton.TextButtonStyle();
        buttonFont = new BitmapFont();
        textStyle.font = buttonFont;
        textStyle.fontColor = RED;
        image = new Texture("mute.jpg");
        draw = new TextureRegionDrawable(image);

        music = new Slider(0,100,1,false,skin);
        sound = new Slider(0,100,1,false,skin);

        backButton = new TextButton("BACK",textStyle);
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        table.add(music).center().expandX();
        table.row();
        table.add(sound).center().expandX();
        table.row();
        table.add(backButton).center().expandX();
        table.row();

        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                GAME.setScreen(new MenuScreen(GAME));
            }
        });

       /* music.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!music.isDragging()) {
                    NinjaRun.setVolume(music.getValue());
                    manager.get("audio/sounds/coin.wav", Sound.class).play(NinjaRun.getVolume());
                    NinjaRun.music.setVolume(NinjaRun.getVolume());
                }
            }
        });*/

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);



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
