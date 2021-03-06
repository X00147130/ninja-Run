package com.deanc.ninjarun.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.deanc.ninjarun.NinjaRun;
import com.deanc.ninjarun.Screens.PlayScreen;
import com.deanc.ninjarun.Sprites.Ryu;

public class Finish extends InteractiveTileObject {

    public Finish(PlayScreen screen, MapObject object){
        super(screen,object);
        fixture.setUserData(this);
        setCategoryFilter(NinjaRun.FINISH_BIT);
    }

    @Override
    public void onHit(Ryu ryu) {
        Gdx.app.log("Finish", "Collision");
        screen.setLevelComplete(true);
        NinjaRun.manager.get("audio/sounds/Mission Accomplished Fanfare 1.mp3", Sound.class).play();
        NinjaRun.manager.get("audio/music/yoitrax-warrior.mp3", Music.class).stop();
        }



}
