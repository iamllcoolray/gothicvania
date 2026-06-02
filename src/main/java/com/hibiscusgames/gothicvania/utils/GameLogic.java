package com.hibiscusgames.gothicvania.utils;

import com.hibiscusgames.gothicvania.entities.Player;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.Spawnpoint;
import de.gurkenlabs.litiengine.graphics.Camera;
import de.gurkenlabs.litiengine.graphics.LocationLockCamera;

public final class GameLogic {
    private GameLogic(){

    }

    public static void init(){
        Camera camera = new LocationLockCamera(Player.instance());
        camera.setClampToMap(true);
        Game.world().setCamera(camera);

        Game.world().setGravity(120);

        Game.world().onLoaded(e -> {
            Spawnpoint enter = e.getSpawnpoint("enter");
            if (enter != null){
                enter.spawn(Player.instance());
            }
        });
    }
}
