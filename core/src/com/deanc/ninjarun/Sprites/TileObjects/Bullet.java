package com.deanc.ninjarun.Sprites.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.deanc.ninjarun.Screens.PlayScreen;
import com.deanc.ninjarun.Sprites.Ryu;

public class Bullet extends InteractiveTileObject{
    public Bullet (PlayScreen screen, MapObject object){
        super(screen,object);
    }

    @Override
    public void onHit(Ryu ryu) {

    }
}
