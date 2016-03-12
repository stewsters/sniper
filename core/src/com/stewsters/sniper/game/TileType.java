package com.stewsters.sniper.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stewsters.util.mapgen.CellType;

public enum TileType implements CellType {

    AIR(false, false),
    WATER(false, false),

    GRASS(true, false),
    CONCRETE_FLOOR(true, false),
    CONCRETE_WALL(false, true),

    SIDEWALK_FLOOR(true, false),

    ROAD_FLOOR(true, false),
    DIRT_WALL(false, true),
    WOOD_FLOOR(true, false),

    GLASS(false,true),

    UP_STAIR(false, false),
    DOWN_STAIR(false, false),

    OPEN_DOOR(true, false),
    CLOSED_DOOR(true, true);

    public final boolean floor;
    public final boolean blocks;
    public TextureRegion texture = null;

    TileType(boolean floor, boolean blocks) {
        this.floor = floor;
        this.blocks = blocks;
    }


    public static void setupTextures() {

        WATER.texture = TextureManager.atlas.findRegion("water");

        GRASS.texture = TextureManager.atlas.findRegion("grass");
        CONCRETE_FLOOR.texture = TextureManager.atlas.findRegion("concrete_floor");
        CONCRETE_WALL.texture = TextureManager.atlas.findRegion("concrete_wall");

        SIDEWALK_FLOOR.texture = TextureManager.atlas.findRegion("sidewalk");

        ROAD_FLOOR.texture = TextureManager.atlas.findRegion("cobble");
        DIRT_WALL.texture = TextureManager.atlas.findRegion("dirt");
        WOOD_FLOOR.texture = TextureManager.atlas.findRegion("wood");

        GLASS.texture = TextureManager.atlas.findRegion("glass");

        UP_STAIR.texture = TextureManager.atlas.findRegion("up_stair");
        DOWN_STAIR.texture = TextureManager.atlas.findRegion("down_stair");

        OPEN_DOOR.texture = TextureManager.atlas.findRegion("open_door");
        CLOSED_DOOR.texture = TextureManager.atlas.findRegion("closed_door");

    }
}
