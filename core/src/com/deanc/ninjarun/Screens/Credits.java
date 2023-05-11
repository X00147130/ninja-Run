package com.deanc.ninjarun.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.deanc.ninjarun.NinjaRun;

public class Credits implements Screen {

        private final NinjaRun GAME;
        private Viewport viewport;


        /*tables*/
        private Table devTable;
        private Table musicTable;
        private Table graphicsTable;
        private Table helpTable;

        /*Labels*/
        private Label developer;
        private Label music;
        private Label graphics;
        private Label help;

        private Label developerCred;

        private Label musicCred1;
        private Label musicCred2;
        private Label musicCred3;
        private Label musicCred4;

        private Label graphicsCred1;
        private Label graphicsCred2;
        private Label graphicsCred3;

        private Label helpCred1;
        private Label helpCred2;
        private Label helpCred3;

        /*labelStyle*/
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

        public Credits(NinjaRun game) {
            this.GAME = game;
            viewport = new FitViewport(NinjaRun.V_WIDTH, NinjaRun.V_HEIGHT, new OrthographicCamera());
            stage = new Stage(viewport, GAME.batch);
            stage2 = new Stage(viewport, GAME.batch);
            stage3 = new Stage(viewport, GAME.batch);
            stage4 = new Stage(viewport, GAME.batch);

            /*Label Style*/
            devStyle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/comic/raw/font-export.fnt")), Color.GOLD);
            musicStyle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/comic/raw/font-export.fnt")), Color.RED);
            graphicsStyle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/comic/raw/font-export.fnt")), Color.BLUE);
            helpStyle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/comic/raw/font-export.fnt")), Color.GREEN);

            /*Labels*/
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

            /*Graphic Credits*/
            graphicsCred1 = new Label("philippineoutsourcing, GraphicRiver",graphicsStyle);
            graphicsCred2 = new Label("GamespriteZ, GraphicRiver",graphicsStyle);
            graphicsCred3 = new Label("Raymond Raeleus Buckley, gdx-textures", graphicsStyle);

            /*help Credits*/
            helpCred1 = new Label("David Browne", helpStyle);
            helpCred2 = new Label("Check Out His Games By Searching",helpStyle);
            helpCred3 = new Label("davebrowne Games on Anodriod and ios!",helpStyle);


            /*Developer Table*/
            devTable = new Table();
            devTable.setFillParent(true);

            devTable.row();
            devTable.add(developer).center();
            devTable.row();
            devTable.add(developerCred);

            /*Music Table*/
            musicTable = new Table();
            musicTable.setFillParent(true);

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

            graphicsTable.row();
            graphicsTable.add(graphics).center();
            graphicsTable.row();
            graphicsTable.add(graphicsCred1);
            graphicsTable.row();
            graphicsTable.add(graphicsCred2);
            graphicsTable.row();
            graphicsTable.add(graphicsCred3);
            graphicsTable.row();

            /*Help Table*/
            helpTable = new Table();
            helpTable.setFillParent(true);

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


