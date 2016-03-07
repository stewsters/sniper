package com.stewsters.sniper.generator;

import com.stewsters.sniper.component.AiControl;
import com.stewsters.sniper.component.Appearance;
import com.stewsters.sniper.component.Health;
import com.stewsters.sniper.component.Location;
import com.stewsters.sniper.component.PlayerControl;
import com.stewsters.sniper.entity.Pawn;
import com.stewsters.sniper.game.TextureManager;
import com.stewsters.sniper.game.TileType;
import com.stewsters.sniper.map.MapChunk;
import com.stewsters.util.math.Point3i;

import static com.stewsters.sniper.game.TileType.AIR;
import static com.stewsters.sniper.game.TileType.CONCRETE_FLOOR;

public class CityGen {


    public static MapChunk gen() {

        MapChunk mapChunk = new MapChunk();

        for (int x = 0; x < mapChunk.getXSize(); x++) {
            for (int y = 0; y < mapChunk.getYSize(); y++) {
                for (int z = 0; z < mapChunk.getZSize(); z++) {

                    TileType t;
                    if (z == 0) {
                        t = CONCRETE_FLOOR;
                    } else {
                        t = AIR;
                    }

                    mapChunk.setCellTypeAt(x, y, z, t);
                }
            }
        }


        return mapChunk;
    }


    public static MapChunk populate(MapChunk mapChunk) {


        // Set up player
        int x = mapChunk.getXSize() / 2;
        int y = mapChunk.getYSize() / 2;
        int z = 0;

        Pawn player = new Pawn();

        player.pos = new Location(new Point3i(x, y, z));
        player.health = new Health(1);
        player.appearance = new Appearance(TextureManager.player);
        player.doorOpener = true;

        player.playerControl = new PlayerControl(player);
        mapChunk.addPawn(player);

        return mapChunk;


    }


    public static MapChunk addEnemy(MapChunk mapChunk, int x, int y, int z) {

        Pawn player = new Pawn();

        player.pos = new Location(new Point3i(x, y, z));
        player.health = new Health(1);
        player.appearance = new Appearance(TextureManager.player);
        player.doorOpener = true;

        player.aiControl = new AiControl(player);
        mapChunk.addPawn(player);
        return mapChunk;

    }

}
