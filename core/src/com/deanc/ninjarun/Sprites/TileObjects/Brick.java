package com.deanc.ninjarun.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.deanc.ninjarun.NinjaRun;
import com.deanc.ninjarun.Scenes.Hud;
import com.deanc.ninjarun.Screens.PlayScreen;
import com.deanc.ninjarun.Sprites.Ryu;

public class Brick extends InteractiveTileObject {
    public Brick(PlayScreen screen, MapObject object){
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(NinjaRun.BRICK_BIT);
    }

    @Override
    public void onHeadHit(Ryu ryu) {
            NinjaRun.manager.get("audio/sounds/fireworks.mp3", Sound.class).play();
    }
}
