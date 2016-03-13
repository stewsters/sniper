package com.stewsters.sniper.systems;

import com.badlogic.gdx.Gdx;
import com.stewsters.sniper.entity.Pawn;
import com.stewsters.sniper.extra.shootin.Bresenham3d;
import com.stewsters.sniper.extra.shootin.ClearShotEvaluator;
import com.stewsters.sniper.game.TileType;
import com.stewsters.sniper.map.WorldMap;
import com.stewsters.util.math.Point3i;

public class SnipeSystem {

    private final WorldMap worldMap;
    private final ClearShotEvaluator clearShotEvaluator;

    private final int maxRange = 50;
    private long lastPlayerTurn = 0;


    public SnipeSystem(WorldMap worldMap) {
        this.worldMap = worldMap;
        this.clearShotEvaluator = new ClearShotEvaluator(worldMap);
    }

    public void processSystem() {

        if (lastPlayerTurn == worldMap.player.gameTurn) {
            return;
        }
        lastPlayerTurn = worldMap.player.gameTurn;

        int numTargets = 0;
        int shots = 0;
        int vulnerable = 0;

        if (worldMap.player.health.getHP() > 0)
            worldMap.player.stealth = stealth(worldMap.player);

        worldMap.player.playerControl.validTargets.clear();

        for (Pawn pawn : worldMap.pawnQueue) {
            if (pawn != worldMap.player && pawn.snipe != null) {

                Point3i p1 = worldMap.player.pos.current;
                Point3i p2 = pawn.pos.current;

                int dx = Math.abs(p1.x - p2.x);
                int dy = Math.abs(p1.y - p2.y);
                int dz = Math.abs(p1.z - p2.z);

                if (Math.max(dz, Math.max(dx, dy)) <= maxRange && Bresenham3d.open(p1.x, p1.y, p1.z, p2.x, p2.y, p2.z, clearShotEvaluator)) {
                    pawn.snipe.percentageToKill = Math.min(pawn.snipe.percentageToKill + 0.2, 1);
                    pawn.snipe.notice = Math.min(pawn.snipe.returnPercentage + 0.1, 1);
                    pawn.snipe.returnPercentage = Math.min(pawn.snipe.returnPercentage + 0.1, 1);

                    worldMap.player.playerControl.validTargets.add(pawn);
//                    Gdx.app.log("shot", "player to " + (p2.x - p1.x) + " " + (p2.y - p1.y) + " " + (p2.z - p1.z));
                    numTargets++;
                } else {
                    pawn.snipe.percentageToKill = Math.max(pawn.snipe.percentageToKill - 0.4, 0);
                    pawn.snipe.notice = Math.max(pawn.snipe.returnPercentage - 0.2, 0);
                    pawn.snipe.returnPercentage = Math.max(pawn.snipe.percentageToKill - 0.4, 0);
                }


            }
        }
        Gdx.app.log("targets", "targets: " + numTargets + " shots:" + shots + " returns: " + vulnerable);
    }

    public double stealth(Pawn pawn) {

        double walls = 0;

        for (int xd = -1; xd < 2; xd++) {
            for (int yd = -1; yd < 2; yd++) {
//                for(int zd = -1; zd<=2; zd++){
                if (pawn.worldMap.isOutsideMap(pawn.pos.current.x + xd, pawn.pos.current.y + yd, pawn.pos.current.z))
                    break;

                TileType tileType = pawn.worldMap.getCellTypeAt(pawn.pos.current.x + xd, pawn.pos.current.y + yd, pawn.pos.current.z);
                if (tileType.blocks)
                    walls++;
//                }
            }
        }
        return walls / 9f;

    }
}
