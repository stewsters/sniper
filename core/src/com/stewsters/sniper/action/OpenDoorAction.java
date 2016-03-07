package com.stewsters.sniper.action;


import com.stewsters.sniper.entity.Pawn;
import com.stewsters.sniper.game.TileType;
import com.stewsters.util.math.Point3i;

public class OpenDoorAction extends Action {

    private Point3i pos;

    public OpenDoorAction(Pawn pawn, Point3i pos) {
        super(pawn);
        this.pos = pos;

    }

    @Override
    public ActionResult onPerform() {

        if (!pawn.doorOpener) {
            return ActionResult.FAILURE;
        }
        if (mapChunk.getCellTypeAt(pos.x, pos.y, pos.z) != TileType.CLOSED_DOOR) {
            return ActionResult.FAILURE;
        }

        mapChunk.setCellTypeAt(pos.x, pos.y, pos.z, TileType.OPEN_DOOR);
        return ActionResult.SUCCESS;

    }
}
