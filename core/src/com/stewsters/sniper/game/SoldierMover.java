package com.stewsters.sniper.game;

import com.stewsters.sniper.map.MapChunk;
import com.stewsters.util.pathing.threeDimention.shared.Mover3d;

public class SoldierMover implements Mover3d {

    private MapChunk map;

    public SoldierMover(MapChunk region) {
        map = region;
    }

    @Override
    public boolean canTraverse(int sx, int sy, int sz, int tx, int ty, int tz) {
        if (map.tiles[tx][ty][tz].blocks)
            return false; // Cant go through walls

        if (map.tiles[tx][ty][tz].floor)
            return true;

        if (map.tiles[tx][ty][tz] == TileType.DOWN_STAIR && map.tiles[sx][sy][sz] == TileType.UP_STAIR)
            return true; // Can go up stairs

        if (map.tiles[tx][ty][tz] == TileType.UP_STAIR && map.tiles[sx][sy][sz] == TileType.DOWN_STAIR)
            return true; // Can go down stairs

        return false;
    }

    @Override
    public boolean canOccupy(int tx, int ty, int tz) {
        return !map.tiles[tx][ty][tz].blocks;
    }

    @Override
    public float getCost(int sx, int sy, int sz, int tx, int ty, int tz) {
        return 1;
    }
}
