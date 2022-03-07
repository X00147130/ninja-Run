package com.deanc.ninjarun.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.deanc.ninjarun.NinjaRun;
import com.deanc.ninjarun.Scenes.Hud;
import com.deanc.ninjarun.Screens.PlayScreen;
import com.deanc.ninjarun.Sprites.Items.ItemDef;
import com.deanc.ninjarun.Sprites.Items.Mushrooms;
import com.deanc.ninjarun.Sprites.Ryu;

public class coin extends InteractiveTileObject {

    private static TiledMapTileSet tileSet;

    /*      This variable finds a paticular tile in our tileset
    ** Note that tiled starts at the 0 index where libgdx starts at the 1 index **
            Useful for tile changes such as drops or pickups   */
    private final int BLANK_COIN = 28;

    public coin (PlayScreen screen, MapObject object){
        super(screen,object);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(NinjaRun.COIN_BIT);
    }

    @Override
    public void onHeadHit(Ryu mario) {
        Gdx.app.log("Coin", "Collision");
        if(getCell().getTile().getId() == BLANK_COIN)
            NinjaRun.manager.get("audio/sounds/fireworks.mp3", Sound.class).play();
        else {
            if(object.getProperties().containsKey("mushroom")){
                screen.spawnItem(new ItemDef( new Vector2(body.getPosition().x, body.getPosition().y + 16 / NinjaRun.PPM),
                        Mushrooms.class));
                NinjaRun.manager.get("audio/sounds/powerup_spawn.wav", Sound.class).play();
            }
            else
                NinjaRun.manager.get("audio/sounds/coin.mp3", Sound.class).play();

        }
        getCell().setTile(tileSet.getTile(BLANK_COIN));
        Hud.addScore(100);

    }
}
