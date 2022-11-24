package com.deanc.ninjarun.Screens;

import static com.badlogic.gdx.graphics.Color.GOLD;
import static com.badlogic.gdx.graphics.Color.RED;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
    private final NinjaRun GAME;
    private Viewport viewport;

    private Button backButton;
    private Label page;
    private Label music;
    private Label sound;
    private CheckBox muteMusic;
    private CheckBox muteSound;
    private Skin skin;


    /*Slider music;
    Slider sound;
    Skin skin=new Skin(Gdx.files.internal("skins/comic-ui.json"));*/

    Stage stage;
    TextButton.TextButtonStyle textStyle;
    BitmapFont buttonFont;

    private Texture background;
    float startX;

    public Settings(final NinjaRun game){
        this.GAME = game;
        viewport = new FitViewport(NinjaRun.V_WIDTH, NinjaRun.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);
        /*background = new Texture("settings.jpg");*/

        textStyle = new TextButton.TextButtonStyle();
        buttonFont = new BitmapFont(Gdx.files.internal("skins/comic/comic-ui_data/font-export.fnt"));
        textStyle.font = buttonFont;
        textStyle.fontColor = RED;

        Label.LabelStyle label = new Label.LabelStyle();
        label.font = buttonFont;
        label.fontColor = RED;

        Label.LabelStyle title = new Label.LabelStyle();
        title.font = buttonFont;
        title.fontColor = GOLD;

        page = new Label("SETTINGS",title);
        music = new Label("Mute Music?",label);
        sound = new Label("Mute Sound?", label);

        //skin setup
        skin = new Skin(Gdx.files.internal("skins/comic/comic-ui.json"));

        //CheckBox setup
        muteMusic = new CheckBox("Mute",skin);
        muteMusic.setWidth(30);
        muteMusic.setHeight(25);

        muteSound = new CheckBox("Mute",skin);
        muteSound.setWidth(30);
        muteSound.setHeight(25);

        /*music = new Slider(0f,1f,0.01f,false,skin);
        music.setValue(GAME.getVolume());

        sound = new Slider(0f,1f,0.01f,false,skin);
        sound.setValue(GAME.getSoundVolume());

        music.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!music.isDragging()){
                    GAME.setVolume(music.getValue());
*//*NULL pointer Exception*//**//*GAME.music.setVolume(GAME.getVolume());*//*
                    manager.get("audio/sounds/mixkit-fast-sword-whoosh-2792.wav", Sound.class).play(GAME.getVolume());
                }
            }
        });

        sound.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!sound.isDragging()){
                    GAME.setSoundVolume(sound.getValue());
                    manager.get("audio/sounds/coin.mp3", Sound.class).play(GAME.getSoundVolume());
                }
            }
        });

        Container<Slider> container = new Container<Slider>(music);
        container.setTransform(true); // enables scaling and rotation
        container.setSize(300,100);
        container.setOrigin(container.getWidth() / 2 , container.getHeight() / 2);
        container.setScale(1);
        container.setColor(RED);

        Container<Slider> container1 = new Container<Slider>(sound);
        container1.setTransform(true); // enables scaling and rotation
        container1.setSize(300,100);
        container1.setOrigin(container.getWidth() / 2 , container.getHeight() / 2);
        container1.setScale(1);
        container1.setColor(RED);*/


        backButton = new TextButton("BACK",textStyle);
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        /*table.add(container).center().expandX();
        table.row();
        table.add(container1).center().expandX();
        table.row();*/
        table.add(page).center().expandX().padLeft(125).padBottom(25);
        table.row();
        table.add(music).left().padLeft(105);
        table.add(muteMusic).center();
        table.row();
        table.add(sound).left().padLeft(105);
        table.add(muteSound).center();
        table.row();
        table.row();
        table.add(backButton).center().expandX().padLeft(125).padTop(10);
        table.row();


        muteMusic.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                GAME.music.stop();
                GAME.setVolume(0);
            }
        });

        muteSound.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                GAME.setSoundVolume(0);
            }
        });

        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                GAME.music.stop();
                GAME.setScreen(new MenuScreen(GAME));
            }
        });


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
