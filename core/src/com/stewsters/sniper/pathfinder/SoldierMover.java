package com.stewsters.sniper.pathfinder;

import com.stewsters.sniper.map.TileType;
import com.stewsters.sniper.map.WorldMap;
import com.stewsters.util.pathing.threeDimention.shared.Mover3d;

public class SoldierMover implements Mover3d {

    private WorldMap map;

    public SoldierMover(WorldMap region) {
        map = region;
    }

    @Override
    public boolean canTraverse(int sx, int sy, int sz, int tx, int ty, int tz) {
        TileType targetTileType = map.getCellTypeAt(tx, ty, tz);

        if (targetTileType.blocks)
            return false; // Cant go through walls

        if (targetTileType.floor)
            return true;

        TileType startTileType = map.getCellTypeAt(sx, sy, sz);

        if (targetTileType == TileType.DOWN_STAIR && startTileType == TileType.UP_STAIR)
            return true; // Can go up stairs

        return targetTileType == TileType.UP_STAIR && startTileType == TileType.DOWN_STAIR;

    }

    @Override
    public boolean canOccupy(int tx, int ty, int tz) {
        return !map.getCellTypeAt(tx, ty, tz).blocks;
    }

    @Override
    public float getCost(int sx, int sy, int sz, int tx, int ty, int tz) {
        return 1;
    }
}
