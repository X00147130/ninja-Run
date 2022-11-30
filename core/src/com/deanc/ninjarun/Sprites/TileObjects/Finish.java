package com.deanc.ninjarun.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.deanc.ninjarun.NinjaRun;
import com.deanc.ninjarun.Screens.PlayScreen;
import com.deanc.ninjarun.Sprites.Ryu;

public class Finish extends InteractiveTileObject {
    private NinjaRun ninja;
    public Finish(NinjaRun ninja, PlayScreen screen, MapObject object){
        super(screen,object);
        this.ninja = ninja;
        fixture.setUserData(this);
        setCategoryFilter(NinjaRun.FINISH_BIT);
    }

    @Override
    public void onHit(Ryu ryu) {
        Gdx.app.log("Finish", "Collision");
        screen.setLevelComplete(true);
        ninja.loadSound("audio/sounds/Mission Accomplished Fanfare 1.mp3");
        long id = ninja.sound.play();
        if(ninja.getSoundVolume() != 0)
            ninja.sound.setVolume(id, ninja.getSoundVolume());
        else{
            ninja.sound.setVolume(id,0);
        }
        ninja.music.stop();
        }



}
