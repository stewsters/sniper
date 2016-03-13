package com.stewsters.sniper.systems;

import com.stewsters.sniper.entity.Pawn;
import com.stewsters.sniper.map.WorldMap;

import java.util.ArrayList;

public class GravitySystem {

    private WorldMap worldMap;

    public GravitySystem(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    public void processSystem() {
        ArrayList<Pawn> toDelete = new ArrayList<>();

        for (Pawn pawn : worldMap.pawnQueue) {

            int fall = 0;
            while (!worldMap.getCellTypeAt(pawn.pos.current).floor
                    && !worldMap.getCellTypeAt(pawn.pos.current.x, pawn.pos.current.y, pawn.pos.current.z - 1).blocks) {

                worldMap.updatePosZ(pawn, -1);

                fall++;
            }

            if (fall >= 2) {
                pawn.health.damage(fall - 1);
                if (pawn.health.getHP() <= 0) {
                    toDelete.add(pawn);
                }
            }

        }
        for (Pawn pawn : toDelete) {
            pawn.worldMap.removePawn(pawn);
        }
    }
}
