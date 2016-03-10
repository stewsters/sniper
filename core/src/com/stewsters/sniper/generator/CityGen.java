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
import com.stewsters.sniper.map.MapChunk;
import com.stewsters.sniper.map.WorldMap;
import com.stewsters.util.math.MatUtils;
import com.stewsters.util.math.Point3i;
import com.stewsters.util.math.geom.Rect;
import com.stewsters.util.math.geom.RectPrism;
import com.stewsters.util.math.geom.RectSubdivider;

import java.util.List;

import static com.stewsters.util.math.MatUtils.d;

public class CityGen {

    public static final int groundHeight = 4;

    public static WorldMap gen() {

        WorldMap worldMap = new WorldMap();

        for (int x = 0; x < worldMap.getXChunkSize(); x++) {
            for (int y = 0; y < worldMap.getYChunkSize(); y++) {

                MapChunk mapChunk = worldMap.getChunk(x, y);
                buildChunk(mapChunk);
            }
        }


        return worldMap;
    }

    private static void buildChunk(MapChunk mapChunk) {

        LotType lotType = MatUtils.randVal(LotType.values());
        switch (lotType) {

            case PARK:
                flattenWorld(mapChunk, TileType.DIRT_WALL, TileType.GRASS, TileType.ROAD_FLOOR, TileType.AIR);
                // TODO: make trees
                break;

            case CITY:
                flattenWorld(mapChunk, TileType.DIRT_WALL, TileType.SIDEWALK_FLOOR, TileType.ROAD_FLOOR, TileType.AIR);
                constructBuildings(mapChunk, 8);
                break;

            case SKYSCRAPER:
                flattenWorld(mapChunk, TileType.DIRT_WALL, TileType.SIDEWALK_FLOOR, TileType.ROAD_FLOOR, TileType.AIR);
                constructSkyscraper(mapChunk);
                break;

        }

    }


    private static void constructSkyscraper(MapChunk mapChunk) {

        Rect lot = new Rect(2, 2, mapChunk.xSize - 3, mapChunk.ySize - 3);

        genBuilding(mapChunk, lot);
    }

    private static void constructBuildings(MapChunk mapChunk, int minSize) {

        // make a lot representing the whole, except for the street
        Rect whole = new Rect(2, 2, mapChunk.xSize - 3, mapChunk.ySize - 3);

        List<Rect> lots = RectSubdivider.divide(whole, minSize);

        for (Rect lot : lots) {
            genBuilding(mapChunk, lot);
        }

    }

    private static void genBuilding(MapChunk mapChunk, Rect lot) {

        // ySize in floors
        int totalFloors = MatUtils.getIntInRange(2, (mapChunk.zSize - groundHeight - 2));

        // This gives you the separation level around the base
        int extendedWalk = MatUtils.getIntInRange(1, 2);


        Rect foundation = new Rect(
                lot.x1 + extendedWalk,
                lot.y1 + extendedWalk,
                lot.x2 - extendedWalk,
                lot.y2 - extendedWalk);

        for (int floor = 0; floor < totalFloors; floor++) {

            solidLevel(mapChunk, foundation, groundHeight + floor, TileType.CONCRETE_FLOOR);

            if (MatUtils.d(5) != 1) {
                wallWithWindows(mapChunk, foundation, groundHeight + (floor), 1, 3, TileType.CONCRETE_WALL, TileType.CONCRETE_FLOOR);
            }

        }

        solidLevel(mapChunk, foundation, groundHeight + totalFloors, TileType.SIDEWALK_FLOOR);

        int stairX = MatUtils.getIntInRange(foundation.x1 + 1, foundation.x2 - 1);
        int stairY = MatUtils.getIntInRange(foundation.y1 + 1, foundation.y2 - 2);

        for (int floor = 0; floor < totalFloors; floor++) {
            mapChunk.tiles[stairX][stairY + (floor % 2)][groundHeight + floor] = TileType.UP_STAIR;
            mapChunk.tiles[stairX][stairY + (floor % 2)][groundHeight + floor + 1] = TileType.DOWN_STAIR;
        }

        int top = (totalFloors) + groundHeight;
        fillColumn(mapChunk, foundation.x1, foundation.y1, groundHeight, top, TileType.CONCRETE_WALL);
        fillColumn(mapChunk, foundation.x2, foundation.y1, groundHeight, top, TileType.CONCRETE_WALL);
        fillColumn(mapChunk, foundation.x1, foundation.y2, groundHeight, top, TileType.CONCRETE_WALL);
        fillColumn(mapChunk, foundation.x2, foundation.y2, groundHeight, top, TileType.CONCRETE_WALL);
    }


    private static void flattenWorld(MapChunk mapChunk, TileType ground, TileType border, TileType road, TileType air) {

        for (int x = 0; x < mapChunk.xSize; x++) {
            for (int y = 0; y < mapChunk.ySize; y++) {
                for (int z = 0; z < mapChunk.zSize; z++) {
                    TileType t;

                    if (z < groundHeight) {
                        t = ground;
                    } else if (z == groundHeight) {
                        if (x <= 1 || x >= mapChunk.xSize - 2 || y <= 1 || y >= mapChunk.ySize - 2)
                            t = road;
                        else
                            t = border;

                    } else {
                        t = air;
                    }

                    mapChunk.tiles[x][y][z] = t;

                }
            }
        }
    }


    private static void fillColumn(MapChunk mapChunk, int x, int y, int z, int height, TileType tileType) {
        fillBlock(mapChunk, new RectPrism(x, y, z, x, y, height), tileType);
    }


    private static void fillBlock(MapChunk mapChunk, RectPrism prism, TileType tileType) {

        for (int x = prism.x1; x <= prism.x2; x++) {
            for (int y = prism.y1; y <= prism.y2; y++) {
                for (int z = prism.z1; z <= prism.z2; z++) {

                    mapChunk.tiles[x][y][z] = tileType;
                }
            }
        }
    }


    private static void solidLevel(MapChunk mapChunk, Rect prism, int z, TileType tileType) {

        for (int x = prism.x1; x <= prism.x2; x++) {
            for (int y = prism.y1; y <= prism.y2; y++) {

                mapChunk.tiles[x][y][z] = tileType;
            }
        }
    }

    // Draws a wall around the area
    private static void wall(MapChunk mapChunk, Rect prism, int z, int wallHeight, TileType tileType) {

        for (int x = prism.x1; x <= prism.x2; x++) {
            for (int y = prism.y1; y <= prism.y2; y++) {
                for (int zp = 0; zp < wallHeight; zp++) {

                    if ((x == prism.x1 || x == prism.x2) ||
                            (y == prism.y1 || y == prism.y2))
                        mapChunk.tiles[x][y][z + zp] = tileType;
                }
            }
        }
    }


    // Draws a wall around the area
    private static void wallWithWindows(MapChunk mapChunk, Rect prism, int z, int wallHeight, int windowSpacing, TileType wallType, TileType windowType) {

        for (int x = prism.x1; x <= prism.x2; x++) {
            for (int y = prism.y1; y <= prism.y2; y++) {
                for (int zp = 0; zp < wallHeight; zp++) {

                    if ((x == prism.x1 || x == prism.x2)) {
                        if ((y - prism.y1) % windowSpacing == 0) {
                            mapChunk.tiles[x][y][z + zp] = windowType;
                        } else {
                            mapChunk.tiles[x][y][z + zp] = wallType;
                        }
                    } else if (y == prism.y1 || y == prism.y2) {
                        if ((x - prism.x1) % windowSpacing == 0) {
                            mapChunk.tiles[x][y][z + zp] = windowType;
                        } else {
                            mapChunk.tiles[x][y][z + zp] = wallType;
                        }
                    }
                }
            }
        }
    }


    public static WorldMap populate(WorldMap worldMap) {


        // Set up player
        int px = worldMap.getXSize() / 2;
        int py = worldMap.getYSize() / 2;
        int pz = groundHeight;

        addPlayer(worldMap, px, py, pz);


        for (int i = 0; i < 10; i++) {
            int x = d(worldMap.getXSize()) - 1;
            int y = d(worldMap.getYSize()) - 1;
            int z = groundHeight;

            if (!worldMap.getCellTypeAt(x, y, z).blocks) {
                addEnemy(worldMap, x, y, z);
            }
        }


        for (int i = 0; i < 10; i++) {
            int x = d(worldMap.getXSize()) - 1;
            int y = d(worldMap.getYSize()) - 1;
            int z = groundHeight;

            if (!worldMap.getCellTypeAt(x, y, z).blocks) {
                addDog(worldMap, x, y, z);
            }
        }

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
