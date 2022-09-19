package com.deanc.ninjarun.Screens;

import static com.badlogic.gdx.graphics.Color.RED;
import static com.badlogic.gdx.graphics.Color.WHITE;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
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

public class LevelComplete implements Screen {

    //Admin
    private NinjaRun run;
    private Viewport screen;
    private Stage stage;
    private int score = 0;
    private SpriteBatch batch;


    //Next level button variables
    private int map;


    //Buttons
    private Button menuButton;
    private Button nextLevelButton;
    private Button levelSelectButton;
    private TextButton.TextButtonStyle buttonstyle;
    private BitmapFont font;

    Label title;
    Label Coins;
    private Texture background;

    public LevelComplete(NinjaRun game, int level){
        super();
        //admin setup
        this.run = game;
        screen = new FitViewport(NinjaRun.V_WIDTH,NinjaRun.V_HEIGHT,new OrthographicCamera());
        stage = new Stage(screen,((NinjaRun) game).batch);
        map = level + 1;
        batch =  new SpriteBatch();

         score = run.getCoins();

        /*background = new Texture("levelcomplete.png");
*/
        //TextButton Style Admin
        buttonstyle = new TextButton.TextButtonStyle();
        font = new BitmapFont();
        buttonstyle.font = font;
        buttonstyle.font.setColor(Color.WHITE);

        //Setting up the TextButtons
        menuButton = new TextButton("Main Menu", buttonstyle);
        nextLevelButton = new TextButton("Next Level", buttonstyle);
        levelSelectButton = new TextButton("Level Select", buttonstyle);

        //Label Admin
        Label.LabelStyle style = new Label.LabelStyle(new BitmapFont(), Color.RED);
        title = new Label("Level Complete",style);
        Coins = new Label(String.format("Score: %4d" ,score),style);

        //Table Setup
        Table table = new Table();
        table.center();
        table.setFillParent(true);


        table.add(title).expandX().top();
        table.row().padBottom(10);
        table.add(Coins).center().padBottom(10);
        table.row();

        if(map != 11) {
            table.add(nextLevelButton).expandX().padTop(20);
            table.row();
        }
        table.add(levelSelectButton).expandX().center();
        table.row();
        table.add(menuButton).expandX().center();
        table.row();

        //Setting up the stage
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

        //Setting up ClickListners for buttons
      if(map != 11) {
          nextLevelButton.addListener(new ClickListener() {
              @Override
              public void clicked(InputEvent event, float x, float y) {
                  run.setScreen(new PlayScreen((NinjaRun) run, map));
                  run.setCoins(0);
              }
          });
      }

        menuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                run.setScreen(new MenuScreen((NinjaRun)run));
                NinjaRun.manager.get("audio/music/yoitrax-warrior.mp3", Music.class).stop();
            }
        });

        levelSelectButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                run.setScreen(new LevelSelect((NinjaRun)run));
                NinjaRun.manager.get("audio/music/yoitrax-warrior.mp3", Music.class).stop();
            }
        });
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
    }
}
