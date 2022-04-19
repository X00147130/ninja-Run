package com.deanc.ninjarun.Sprites.TileObjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.deanc.ninjarun.NinjaRun;
import com.deanc.ninjarun.Screens.PlayScreen;
import com.deanc.ninjarun.Sprites.Ryu;

public class Platforms extends InteractiveTileObject {
    public Platforms(PlayScreen screen, MapObject object){
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(NinjaRun.PLATFORM_BIT);
    }

    @Override
    public void onHit(Ryu ryu) {
            NinjaRun.manager.get("audio/sounds/fireworks.mp3", Sound.class).play();
    }
}
