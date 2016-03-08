package com.stewsters.sniper.action;

import com.stewsters.sniper.entity.Pawn;
import com.stewsters.sniper.game.TileType;
import com.stewsters.util.math.Point3i;

public class CloseDoorAction extends Action {

    private Point3i pos;

    public CloseDoorAction(Pawn pawn, Point3i pos) {
        super(pawn);
        this.pos = pos;

    }

    @Override
    public ActionResult onPerform() {

        if (!pawn.doorOpener) {
            return ActionResult.FAILURE;
        }
        if (pawn.canOccupy(pos.x, pos.y, pos.z)) {
            return ActionResult.FAILURE;
        }
        if (worldMap.getCellTypeAt(pos.x, pos.y, pos.z) != TileType.OPEN_DOOR) {
            return ActionResult.FAILURE;
        }

        worldMap.setCellTypeAt(pos.x,pos.y,pos.z, TileType.CLOSED_DOOR);
        return ActionResult.SUCCESS;
    }
}
