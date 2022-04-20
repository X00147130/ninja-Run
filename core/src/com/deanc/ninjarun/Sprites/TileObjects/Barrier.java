package com.deanc.ninjarun.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.deanc.ninjarun.NinjaRun;
import com.deanc.ninjarun.Screens.PlayScreen;
import com.deanc.ninjarun.Sprites.Ryu;

public class Barrier extends InteractiveTileObject {

    public Barrier(PlayScreen screen, MapObject object){
        super(screen,object);
        fixture.setUserData(this);
        setCategoryFilter(NinjaRun.BARRIER_BIT);
    }

    @Override
    public void onHit(Ryu ryu) {
        Gdx.app.log("Finish", "Collision");
    }
}
