package com.deanc.ninjarun.Sprites.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.deanc.ninjarun.NinjaRun;
import com.deanc.ninjarun.Screens.PlayScreen;
import com.deanc.ninjarun.Sprites.Ryu;


public class Shuriken extends InteractiveTileObject {
        private boolean todestroy = false;
        private boolean destroyed = false;
        public Shuriken(PlayScreen screen, MapObject object) {
                super(screen, object);
                setCategoryFilter(NinjaRun.WEAPON_BIT);
                fixture.setSensor(true);
                fixture.setUserData(this);

        }

        @Override
        public void onHit(Ryu ryu) {
                destroy();
                screen.getPlayer().setAmmo(2);
        }

        @Override
        public void setCategoryFilter(short filterBit) {
                super.setCategoryFilter(filterBit);
        }

        @Override
        public TiledMapTileLayer.Cell getCell() {
                return super.getCell();
        }

        public void update(){
                if(todestroy && !destroyed){
                        world.destroyBody(body);
                        destroyed = true;
                }

        }

        public void destroy() {
                todestroy = true;
        }
}
