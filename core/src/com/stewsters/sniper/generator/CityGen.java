package com.stewsters.sniper.generator;

import com.badlogic.gdx.Gdx;
import com.stewsters.sniper.component.AiControl;
import com.stewsters.sniper.component.Appearance;
import com.stewsters.sniper.component.Health;
import com.stewsters.sniper.component.Location;
import com.stewsters.sniper.component.PlayerControl;
import com.stewsters.sniper.component.Snipe;
import com.stewsters.sniper.entity.Pawn;
import com.stewsters.sniper.extra.doorgen.DoorDiggerMover;
import com.stewsters.sniper.game.TextureManager;
import com.stewsters.sniper.game.TileType;
import com.stewsters.sniper.map.MapChunk;
import com.stewsters.sniper.map.WorldMap;
import com.stewsters.util.math.MatUtils;
import com.stewsters.util.math.Point2i;
import com.stewsters.util.math.Point3i;
import com.stewsters.util.math.geom.Rect;
import com.stewsters.util.math.geom.RectPrism;
import com.stewsters.util.math.geom.RectSubdivider;
import com.stewsters.util.pathing.threeDimention.pathfinder.AStarPathFinder3d;
import com.stewsters.util.pathing.threeDimention.pathfinder.PathFinder3d;
import com.stewsters.util.pathing.threeDimention.shared.FullPath3d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.stewsters.sniper.game.TileType.AIR;
import static com.stewsters.sniper.game.TileType.CLOSED_DOOR;
import static com.stewsters.sniper.game.TileType.CONCRETE_FLOOR;
import static com.stewsters.sniper.game.TileType.CONCRETE_WALL;
import static com.stewsters.sniper.game.TileType.DIRT_WALL;
import static com.stewsters.sniper.game.TileType.DOWN_STAIR;
import static com.stewsters.sniper.game.TileType.GLASS;
import static com.stewsters.sniper.game.TileType.GRASS;
import static com.stewsters.sniper.game.TileType.OPEN_DOOR;
import static com.stewsters.sniper.game.TileType.ROAD_FLOOR;
import static com.stewsters.sniper.game.TileType.SIDEWALK_FLOOR;
import static com.stewsters.sniper.game.TileType.UP_STAIR;
import static com.stewsters.util.math.MatUtils.d;

public class CityGen {

    public static final int groundHeight = 2;

    public static WorldMap gen() {

        WorldMap worldMap = new WorldMap();

        List<MapChunk> mapChunks = new ArrayList<MapChunk>();

        for (int x = 0; x < worldMap.getXChunkSize(); x++) {
            for (int y = 0; y < worldMap.getYChunkSize(); y++) {
                mapChunks.add(worldMap.getChunk(x, y));
            }
        }

        mapChunks.parallelStream().forEach(chunk ->
                buildChunk(chunk)
        );


        return worldMap;
    }

    private static void buildChunk(MapChunk mapChunk) {

        int lotChoice = d(10);
        if (lotChoice == 1) {
            flattenWorld(mapChunk, DIRT_WALL, GRASS, ROAD_FLOOR, AIR);
            constructPark(mapChunk);

        } else if (lotChoice < 3) {
            flattenWorld(mapChunk, DIRT_WALL, SIDEWALK_FLOOR, ROAD_FLOOR, AIR);
            constructSkyscraper(mapChunk);

        } else {
            flattenWorld(mapChunk, DIRT_WALL, SIDEWALK_FLOOR, ROAD_FLOOR, AIR);
            constructBuildings(mapChunk, 8);
        }

    }

    private static void constructPark(MapChunk mapChunk) {
        Rect lot = new Rect(2, 2, mapChunk.xSize - 3, mapChunk.ySize - 3);

//        genBuilding(mapChunk, lot);
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


        DoorDiggerMover doorDiggerMover = new DoorDiggerMover(mapChunk, new RectPrism(foundation.x1, foundation.y1, groundHeight, foundation.x2, foundation.y2, groundHeight + totalFloors));
        PathFinder3d p = new AStarPathFinder3d(mapChunk, 1000, false);

        for (int floor = 0; floor < totalFloors; floor++) {

            List<Point3i> roomCenters = new ArrayList<Point3i>();

            int z = groundHeight + floor;

            solidLevel(mapChunk, foundation, z, CONCRETE_FLOOR);

            if (MatUtils.d(5) != 1) {
                wallWithWindows(mapChunk, foundation, z, 1, 3, CONCRETE_WALL, GLASS);
            } else {
                wall(mapChunk, foundation, z, 1, CONCRETE_WALL);
            }


            List<Rect> rooms = RectSubdivider.divide(foundation, d(4) + 2);
            for (Rect room : rooms) {

                Point2i center = room.center();
                roomCenters.add(new Point3i(center.x, center.y, z));

                for (int x = room.x1; x <= room.x2; x++) {
                    for (int y = room.y1; y <= room.y2; y++) {

                        if (
                                (x == room.x1 || x == room.x2 + 1) ||
                                        (y == room.y1 || y == room.y2 + 1) &&
                                                mapChunk.tiles[x][y][z] == CONCRETE_FLOOR
                                )
                            mapChunk.tiles[x][y][z] = CONCRETE_WALL;
                    }
                }
            }
            Collections.shuffle(roomCenters);


            for (Point3i room1 : roomCenters) {
                for (Point3i room2 : roomCenters) {
                    FullPath3d path = p.findPath(doorDiggerMover, room1.x, room1.y, room1.z, room2.x, room2.y, room2.z);

                    if (path != null) {
                        for (int i = 1; i < path.getLength(); i++) {
                            if (mapChunk.tiles[path.getX(i)][path.getY(i)][path.getZ(i)].blocks) {
                                mapChunk.tiles[path.getX(i)][path.getY(i)][path.getZ(i)] = CLOSED_DOOR;
                            }

                        }
                    }
                    // if we are going up, make a stairs
                    //otherwise make door if solid
                }
            }


        }

        // Roof
        solidLevel(mapChunk, foundation, groundHeight + totalFloors, SIDEWALK_FLOOR);

        // stairs up
        for (int floor = 0; floor < totalFloors; floor++) {

            int stairsX = MatUtils.getIntInRange(foundation.x1 + 1, foundation.x2 - 1);
            int stairsY = MatUtils.getIntInRange(foundation.y1 + 1, foundation.y2 - 1);
            int z = groundHeight + floor;

            mapChunk.tiles[stairsX][stairsY][z] = UP_STAIR;
            mapChunk.tiles[stairsX][stairsY][z + 1] = DOWN_STAIR;
        }

        // Corners
        int top = (totalFloors) + groundHeight - 1;
        fillColumn(mapChunk, foundation.x1, foundation.y1, groundHeight, top, CONCRETE_WALL);
        fillColumn(mapChunk, foundation.x2, foundation.y1, groundHeight, top, CONCRETE_WALL);
        fillColumn(mapChunk, foundation.x1, foundation.y2, groundHeight, top, CONCRETE_WALL);
        fillColumn(mapChunk, foundation.x2, foundation.y2, groundHeight, top, CONCRETE_WALL);


        //Doors
        randDoor(mapChunk, MatUtils.getIntInRange(foundation.x1 + 1, foundation.x2 - 1), foundation.y1, groundHeight);
        randDoor(mapChunk, MatUtils.getIntInRange(foundation.x1 + 1, foundation.x2 - 1), foundation.y2, groundHeight);
        randDoor(mapChunk, foundation.x1, MatUtils.getIntInRange(foundation.y1 + 1, foundation.y2 - 1), groundHeight);
        randDoor(mapChunk, foundation.x2, MatUtils.getIntInRange(foundation.y1 + 1, foundation.y2 - 1), groundHeight);


    }

    private static void randDoor(MapChunk mapChunk, int x, int y, int z) {
        mapChunk.tiles[x][y][z] = MatUtils.getBoolean() ? CLOSED_DOOR : OPEN_DOOR;
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
        player.shooter = true;
        player.smasher = true;

        player.playerControl = new PlayerControl(player);
        Gdx.input.setInputProcessor(player.playerControl);

        player.gameTurn = 0;

        worldMap.addPawn(player);
        worldMap.player = player;

        return worldMap;

    }

    public static WorldMap addEnemy(WorldMap worldMap, int x, int y, int z) {

        Pawn pawn = new Pawn();

        pawn.pos = new Location(new Point3i(x, y, z));
        pawn.health = new Health(1);
        pawn.appearance = new Appearance(TextureManager.soldier);
        pawn.doorOpener = true;
        pawn.chaser = false;
        pawn.shooter = true;
        pawn.smasher = true;
        pawn.snipe = new Snipe();
        pawn.aiControl = new AiControl(pawn);

        pawn.gameTurn = d(99);

        worldMap.addPawn(pawn);
        return worldMap;

    }


    public static WorldMap addDog(WorldMap worldMap, int x, int y, int z) {

        Pawn pawn = new Pawn();

        pawn.pos = new Location(new Point3i(x, y, z));
        pawn.health = new Health(1);
        pawn.appearance = new Appearance(TextureManager.dog);
        pawn.doorOpener = false;
        pawn.chaser = true;
        pawn.shooter = false;
        pawn.snipe = new Snipe();

        pawn.aiControl = new AiControl(pawn);

        pawn.gameTurn = d(99);

        worldMap.addPawn(pawn);
        return worldMap;

    }

}
