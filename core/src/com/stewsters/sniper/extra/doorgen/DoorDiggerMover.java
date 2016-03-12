package com.stewsters.sniper.extra.doorgen;

import com.stewsters.sniper.game.TileType;
import com.stewsters.sniper.map.MapChunk;
import com.stewsters.util.math.geom.RectPrism;
import com.stewsters.util.pathing.threeDimention.shared.Mover3d;

public class DoorDiggerMover implements Mover3d {

    private MapChunk mapChunk;
    private RectPrism limit;

    public DoorDiggerMover(MapChunk mapChunk, RectPrism limit) {
        this.mapChunk = mapChunk;
        this.limit = limit;
    }

    @Override
    public boolean canTraverse(int sx, int sy, int sz, int tx, int ty, int tz) {

        if (limit == null)
            return true;

        return limit.contains(tx, ty, tz);

    }

    @Override
    public boolean canOccupy(int tx, int ty, int tz) {
        if (limit == null)
            return true;

        return limit.contains(tx, ty, tz);

    }

    @Override
    public float getCost(int sx, int sy, int sz, int tx, int ty, int tz) {
        TileType tileType =  mapChunk.tiles[tx][ty][tz];
        if (sz!=tz &&tileType!=TileType.DOWN_STAIR && tileType!=TileType.UP_STAIR){
            return 30f; // stairs
        }

        if (mapChunk.tiles[tx][ty][tz].blocks && tileType!=TileType.CLOSED_DOOR) {

            return 10f;
        }

        return 1;
    }
}
