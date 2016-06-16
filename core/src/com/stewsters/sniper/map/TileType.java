package com.stewsters.sniper.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stewsters.sniper.assets.TextureManager;
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

    GLASS(false, true),

    UP_STAIR(true, false),
    DOWN_STAIR(true, false),

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

        for (TileType tileType : TileType.values()) {

            tileType.texture = TextureManager.atlas.findRegion(tileType.name().toLowerCase());
            assert tileType.texture != null;

        }
    }
}
