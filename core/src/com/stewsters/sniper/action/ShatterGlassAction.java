package com.stewsters.sniper.action;


import com.stewsters.sniper.entity.Pawn;
import com.stewsters.sniper.map.TileType;
import com.stewsters.util.math.Point3i;

public class ShatterGlassAction extends Action {

    private Point3i pos;

    public ShatterGlassAction(Pawn pawn, Point3i pos) {
        super(pawn);
        this.pos = pos;

    }

    @Override
    public ActionResult onPerform() {

        if (!pawn.smasher) {
            return ActionResult.FAILURE;
        }
        if (worldMap.getCellTypeAt(pos.x, pos.y, pos.z) != TileType.GLASS) {
            return ActionResult.FAILURE;
        }

        worldMap.setCellTypeAt(pos.x, pos.y, pos.z, TileType.CONCRETE_FLOOR);
        return ActionResult.SUCCESS;

    }
}
