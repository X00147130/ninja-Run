package com.deanc.ninjarun.Tools;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.deanc.ninjarun.NinjaRun;
import com.deanc.ninjarun.Screens.PauseScreen;
import com.deanc.ninjarun.Sprites.Enemies.Ninja;
import com.deanc.ninjarun.Sprites.Ryu;

public class Controller {
    private Viewport view;
    private boolean upPressed = false,attackPressed = false,leftPressed = false,rightPressed = false;
    public Stage stage;
    private OrthographicCamera cam;
    private NinjaRun gameplay;

    //Image Button Variable
    private ImageButton pause;
    private Texture image;
    private Drawable draw;
    private long jump = 0;

    public Controller(final NinjaRun game){
        cam = new OrthographicCamera(480,320);
        cam.position.set(480/2f, 320/2f,0);
        view = new FitViewport(NinjaRun.V_WIDTH,NinjaRun.V_HEIGHT,cam);
        stage = new Stage(view);
        Gdx.input.setInputProcessor(stage);
        gameplay = game;

        final Image upImg = new Image(new Texture("jumpArrow.png"));
        upImg.setSize(30,30);
        upImg.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (Gdx.input.justTouched()) {
                    upPressed = true;
                    Gdx.app.log("Jumped", "worked");
                }
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = false;
            }
        });

        Image attackImg = new Image(new Texture("attack button.png"));
        attackImg.setSize(50,30);
        attackImg.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                attackPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                attackPressed = false;
            }
        });

        Image rightImg = new Image(new Texture("rightArrow.png"));
        rightImg.setSize(30,30);
        rightImg.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
        });

        Image leftImg = new Image(new Texture("LTd5arLKc.png"));
        leftImg.setSize(30,30);
        leftImg.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
            }
        });
        //Image button
        image = new Texture("pause.png");
        draw = new TextureRegionDrawable(image);
        pause = new ImageButton(draw);
        pause.setSize(70,70);
        pause.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event,float x, float y){
                gameplay.setScreen(new PauseScreen(gameplay));
            }

        });

        Table paused = new Table();
        paused.left();
        paused.setFillParent(true);

        Table table = new Table();
        table.left().bottom();
        table.setFillParent(true);

        Table action = new Table();
        action.right().bottom();
        action.setFillParent(true);

        paused.add(pause).maxSize(70,70).left();

        stage.addActor(paused);

        table.row().pad(5,5,5,5);
        table.add(leftImg).size(leftImg.getWidth(),leftImg.getHeight());
        table.add();
        table.add(rightImg).size(rightImg.getWidth(),rightImg.getHeight());
        table.row().padBottom(5);

        stage.addActor(table);

        action.add(upImg).size(upImg.getWidth(),upImg.getHeight()).padRight(10);
        action.add();
        action.add(attackImg).size(attackImg.getWidth(),attackImg.getHeight()).padLeft(10);

        stage.addActor(action);

        stage.addListener(new InputListener(){

            @Override
            public boolean keyDown(InputEvent event, int keycode){
                switch(keycode){
                    case Input.Keys.UP:
                       upPressed = true;
                        break;
                    case Input.Keys.DOWN:
                        attackPressed = true;
                        break;
                    case Input.Keys.LEFT:
                        leftPressed = true;
                    case Input.Keys.RIGHT:
                        rightPressed = true;
                        break;
                }
                return true;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode){
                switch(keycode){
                    case Input.Keys.UP:
                        upPressed = false;
                        break;
                    case Input.Keys.DOWN:
                        attackPressed = false;
                        break;
                    case Input.Keys.LEFT:
                        leftPressed = false;
                    case Input.Keys.RIGHT:
                        rightPressed = false;
                        break;
                }
                return true;
            }

        });
    }
    public void draw(){
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            Gdx.input.setInputProcessor(stage);
        }
        stage.draw();
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return attackPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void resize(int width, int height){
        view.update(width,height);
    }

    public void dispose(){
        stage.dispose();
    }
}

