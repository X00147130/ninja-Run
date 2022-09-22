package com.deanc.ninjarun.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.deanc.ninjarun.NinjaRun;

public class Credits implements Screen {

        private final Game GAME;
        private SpriteBatch batch;
        private Viewport viewport;
//    private Texture background;
//    private Image backgroundIMG;

        /*tables*/
        private Table devTable;
        private Table musicTable;
        private Table graphicsTable;
        private Table helpTable;

        /*Labels*/
        private Label title;
        private Label developer;
        private Label music;
        private Label graphics;
        private Label help;

        private Label developerCred;

        private Label musicCred1;
        private Label musicCred2;
        private Label musicCred3;
        private Label musicCred4;

        private Label helpCred1;
        private Label helpCred2;
        private Label helpCred3;

        /*labelStyle*/
        private Label.LabelStyle titleStyle;
        private Label.LabelStyle devStyle;
        private Label.LabelStyle musicStyle;
        private Label.LabelStyle graphicsStyle;
        private Label.LabelStyle helpStyle;


        private Stage stage;
        private Stage stage2;
        private Stage stage3;
        private Stage stage4;
        private float timeSeconds = 0f;
        private float period = 20f;

        public Credits(Game game) {
            this.GAME = game;
            viewport = new FitViewport(NinjaRun.V_WIDTH, NinjaRun.V_HEIGHT, new OrthographicCamera());
            batch = new SpriteBatch();
            stage = new Stage(viewport);
            stage2 = new Stage(viewport);
            stage3 = new Stage(viewport);
            stage4 = new Stage(viewport);

            /*Label Style*/
            titleStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
            devStyle = new Label.LabelStyle(new BitmapFont(), Color.GOLD);
            musicStyle = new Label.LabelStyle(new BitmapFont(), Color.RED);
            graphicsStyle = new Label.LabelStyle(new BitmapFont(), Color.BLUE);
            helpStyle = new Label.LabelStyle(new BitmapFont(), Color.GREEN);

            /*Labels*/
            title = new Label("Credits",titleStyle);
            developer = new Label("Created By ", devStyle);
            music = new Label("Music By ", musicStyle);
            graphics = new Label("Graphics By ", graphicsStyle);
            help = new Label("With Special Thanks To ", helpStyle);

            /*Developer Credits*/
            developerCred = new Label("Dean Conway ", devStyle);

            /*Music Credits*/
            musicCred1 = new Label("Warrior by yoitrax | https://soundcloud.com/yoitrax", musicStyle);
            musicCred2 = new Label("Jade Dragon by yoitrax | https://soundcloud.com/yoitrax", musicStyle);
            musicCred3 = new Label("Ronin by yoitrax | https://soundcloud.com/yoitrax", musicStyle);
            musicCred4 = new Label("Fuji by yoitrax | https://soundcloud.com/yoitrax", musicStyle);

            /*help Credits*/
            helpCred1 = new Label("David Browne", helpStyle);
            helpCred2 = new Label("Check Out His Games By Searching",helpStyle);
            helpCred3 = new Label("davebrowne Games on Anodriod and ios!",helpStyle);


//        background = new Texture("logos/logo_white_background.jpg");
//        backgroundIMG = new Image(background);


            /*Developer Table*/
            devTable = new Table();
            devTable.setFillParent(true);

            devTable.add(title).center().top().padBottom(20);
            devTable.row();
            devTable.add(developer).center();
            devTable.row();
            devTable.add(developerCred);

            /*Music Table*/
            musicTable = new Table();
            musicTable.setFillParent(true);

            musicTable.add(title).center().top().padBottom(20);
            musicTable.row();
            musicTable.add(music).center().padBottom(10);
            musicTable.row();
            musicTable.add(musicCred1).center();
            musicTable.row();
            musicTable.add(musicCred2).center();
            musicTable.row();
            musicTable.add(musicCred3).center();
            musicTable.row();
            musicTable.add(musicCred4).center();

            /*Graphics Table*/
            graphicsTable = new Table();
            graphicsTable.setFillParent(true);

            graphicsTable.add(title).center().top().padBottom(20);
            graphicsTable.row();
            graphicsTable.add(graphics).center();
            graphicsTable.row();
            graphicsTable.add();

            /*Help Table*/
            helpTable = new Table();
            helpTable.setFillParent(true);

            helpTable.add(title).center().top().padBottom(15);
            helpTable.row();
            helpTable.add(help).center().padBottom(10);
            helpTable.row();
            helpTable.add(helpCred1);
            helpTable.row();
            helpTable.add(helpCred2);
            helpTable.row();
            helpTable.add(helpCred3);

            stage.addActor(devTable);
            stage2.addActor(musicTable);
            stage3.addActor(graphicsTable);
            stage4.addActor(helpTable);



        }

        @Override
        public void show() {

        }

        @Override
        public void render(float delta) {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


            timeSeconds += Gdx.graphics.getRawDeltaTime();
            if (timeSeconds < period && timeSeconds < 5) {
                stage.getActors();
                stage.act();
                stage.draw();
            }

            else if (timeSeconds < period && timeSeconds < 10 && timeSeconds > 5) {
                stage2.getActors();
                stage2.act();
                stage2.draw();
            }

            else if (timeSeconds < period && timeSeconds < 15 && timeSeconds > 10) {
                stage3.getActors();
                stage3.act();
                stage3.draw();
            }

            else if (timeSeconds < period && timeSeconds > 15) {
                stage4.getActors();
                stage4.act();
                stage4.draw();
            }
            else if(timeSeconds > period) {
                timeSeconds -= period;
                GAME.setScreen(new MenuScreen((NinjaRun)GAME));
            }

        }

        @Override
        public void resize(int width, int height) {
            viewport.update(width, height);
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
        }
}

