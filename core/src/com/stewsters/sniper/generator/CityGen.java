package com.stewsters.sniper.generator;

import com.badlogic.gdx.Gdx;
import com.stewsters.sniper.component.AiControl;
import com.stewsters.sniper.component.Appearance;
import com.stewsters.sniper.component.Health;
import com.stewsters.sniper.component.Location;
import com.stewsters.sniper.component.PlayerControl;
import com.stewsters.sniper.entity.Pawn;
import com.stewsters.sniper.game.TextureManager;
import com.stewsters.sniper.game.TileType;
import com.stewsters.sniper.map.WorldMap;
import com.stewsters.util.math.Point3i;

import static com.stewsters.sniper.game.TileType.AIR;
import static com.stewsters.sniper.game.TileType.CONCRETE_FLOOR;
import static com.stewsters.util.math.MatUtils.d;

public class CityGen {


    public static WorldMap gen() {

        WorldMap worldMap = new WorldMap();

        for (int x = 0; x < worldMap.getXSize(); x++) {
            for (int y = 0; y < worldMap.getYSize(); y++) {
                for (int z = 0; z < worldMap.getZSize(); z++) {

                    TileType t;
                    if (z == 0) {
                        t = CONCRETE_FLOOR;
                    } else {
                        t = AIR;
                    }

                    worldMap.setCellTypeAt(x, y, z, t);
                }
            }
        }


        return worldMap;
    }


    public static WorldMap populate(WorldMap worldMap) {


        // Set up player
        int x = worldMap.getXSize() / 2;
        int y = worldMap.getYSize() / 2;
        int z = 0;

        addPlayer(worldMap, x, y, z);


        addEnemy(worldMap, d(worldMap.getXSize()) - 1, d(worldMap.getYSize()) - 1, 0);
//        addEnemy(mapChunk, d(mapChunk.getXSize()) - 1, d(mapChunk.getYSize()) - 1, 0);
//        addEnemy(mapChunk, d(mapChunk.getXSize()) - 1, d(mapChunk.getYSize()) - 1, 0);

        addDog(worldMap, d(worldMap.getXSize()) - 1, d(worldMap.getYSize()) - 1, 0);
//        addDog(mapChunk, d(mapChunk.getXSize()) - 1, d(mapChunk.getYSize()) - 1, 0);
//        addDog(mapChunk, d(mapChunk.getXSize()) - 1, d(mapChunk.getYSize()) - 1, 0);
//        addDog(mapChunk, d(mapChunk.getXSize()) - 1, d(mapChunk.getYSize()) - 1, 0);

        return worldMap;
    }


    public static WorldMap addPlayer(WorldMap worldMap, int x, int y, int z) {

        Pawn player = new Pawn();

        player.pos = new Location(new Point3i(x, y, z));
        player.health = new Health(1);
        player.appearance = new Appearance(TextureManager.player);
        player.doorOpener = true;

        player.playerControl = new PlayerControl(player);
        Gdx.input.setInputProcessor(player.playerControl);

        player.gameTurn = 0;

        worldMap.addPawn(player);
        worldMap.player = player;

        return worldMap;

    }

    public static WorldMap addEnemy(WorldMap worldMap, int x, int y, int z) {

        Pawn player = new Pawn();

        player.pos = new Location(new Point3i(x, y, z));
        player.health = new Health(1);
        player.appearance = new Appearance(TextureManager.soldier);
        player.doorOpener = true;
        player.chaser = false;

        player.aiControl = new AiControl(player);

        player.gameTurn = d(99);

        worldMap.addPawn(player);
        return worldMap;

    }


    public static WorldMap addDog(WorldMap worldMap, int x, int y, int z) {

        Pawn player = new Pawn();

        player.pos = new Location(new Point3i(x, y, z));
        player.health = new Health(1);
        player.appearance = new Appearance(TextureManager.dog);
        player.doorOpener = false;
        player.chaser = true;

        player.aiControl = new AiControl(player);

        player.gameTurn = d(99);

        worldMap.addPawn(player);
        return worldMap;

    }

}
