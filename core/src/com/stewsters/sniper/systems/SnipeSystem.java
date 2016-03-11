package com.stewsters.sniper.systems;

import com.badlogic.gdx.Gdx;
import com.stewsters.sniper.entity.Pawn;
import com.stewsters.sniper.extra.Bresenham3d;
import com.stewsters.sniper.extra.ClearShotEvaluator;
import com.stewsters.sniper.extra.Snipe;
import com.stewsters.sniper.map.WorldMap;
import com.stewsters.util.math.Point3i;

import java.util.ArrayList;

/**
 * Created by stewsters on 3/10/16.
 */
public class SnipeSystem {

    private final WorldMap worldMap;
    private final ClearShotEvaluator clearShotEvaluator;

    private final int maxRange = 50;

    public SnipeSystem(WorldMap worldMap) {
        this.worldMap = worldMap;
        this.clearShotEvaluator=new ClearShotEvaluator(worldMap);
    }

    long lastPlayerTurn = 0;

    public void process() {

        if(lastPlayerTurn == worldMap.player.gameTurn){
            return;
        }
        lastPlayerTurn =  worldMap.player.gameTurn;

        int numTargets = 0;
        int shots = 0;
        int vulnerable = 0;

        for (Pawn pawn : worldMap.pawnQueue) {
            if (pawn != worldMap.player && pawn.snipe!=null) {

                Point3i p1 = worldMap.player.pos.current;
                Point3i p2 = pawn.pos.current;

                int dx = Math.abs(p1.x - p2.x);
                int dy = Math.abs(p1.y - p2.y);
                int dz = Math.abs(p1.z - p2.z);

                if (Math.max(dz, Math.max(dx, dy)) <= maxRange && Bresenham3d.open(p1.x, p1.y, p1.z, p2.x, p2.y, p2.z, clearShotEvaluator)) {
                        pawn.snipe.percentageToKill = Math.min(pawn.snipe.percentageToKill + 0.2, 1);
                        pawn.snipe.returnPercentage = Math.min(pawn.snipe.returnPercentage + 0.2, 1);
//                    Gdx.app.log("shot", "player to " + (p2.x - p1.x) + " " + (p2.y - p1.y) + " " + (p2.z - p1.z));
                        numTargets++;
                }else{
                    pawn.snipe.percentageToKill  = Math.min(pawn.snipe.percentageToKill - 0.4, 1);
                    pawn.snipe.returnPercentage= Math.min(pawn.snipe.percentageToKill - 0.4, 1);
                }


            }
        }
        Gdx.app.log("targets", "targets: " + numTargets + " shots:" + shots + " returns: " + vulnerable);
    }
}
