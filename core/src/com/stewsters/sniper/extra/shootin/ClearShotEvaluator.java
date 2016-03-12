package com.stewsters.sniper.extra.shootin;


import com.stewsters.sniper.game.TileType;
import com.stewsters.sniper.map.WorldMap;

public class ClearShotEvaluator implements Evaluator3d {

    private WorldMap map;

    public ClearShotEvaluator(WorldMap map) {
        this.map = map;
    }

    @Override
    public boolean isGood(int sx, int sy, int sz, int tx, int ty, int tz) {
        TileType tileType = map.getCellTypeAt(tx, ty, tz);

        if (sz > tz && map.getCellTypeAt(sx, sy, sz).floor) {//go down
            return false;
        }
        if (sz < tz && tileType.floor) { // go up
            return false;
        }

        return !tileType.blocks;
    }
}
