package com.deanc.ninjarun.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.deanc.ninjarun.NinjaRun;
import com.deanc.ninjarun.Screens.PlayScreen;
import com.deanc.ninjarun.Sprites.Ryu;

public class Sky extends InteractiveTileObject{

    public Sky(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(NinjaRun.SKY_BIT);
    }

    @Override
    public void onHit(Ryu ryu) {
        Gdx.app.log("sky","limit hit");
        ryu.reverseVelocity(false,true);
    }

}
