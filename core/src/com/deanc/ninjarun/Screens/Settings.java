package com.deanc.ninjarun.Screens;

import static com.badlogic.gdx.graphics.Color.RED;
import static com.badlogic.gdx.utils.compression.CRC.Table;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Settings implements Screen {
    private Button backButton;
    public Button mute;
    private Texture image;
    private TextureRegionDrawable draw;
    Slider volume;
    Skin skin=new Skin(Gdx.files.internal("skin/uiskin.json"));

    Stage stage = new Stage();
    TextButton.TextButtonStyle textStyle;
    BitmapFont buttonFont;

    public Settings(){

        textStyle = new TextButton.TextButtonStyle();
        buttonFont = new BitmapFont();
        textStyle.font = buttonFont;
        textStyle.fontColor = RED;
        image = new Texture("mute.png");
        draw = new TextureRegionDrawable(image);

        volume = new Slider(0,100,1,false,skin);

        backButton = new TextButton("BACK",textStyle);
        mute = new ImageButton(draw);
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        table.add(volume).center().expandX();
        table.row();
        table.add(backButton).center().expandX();
        table.row();

        stage.addActor(table);



    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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
    public void dispose() {

    }
}
