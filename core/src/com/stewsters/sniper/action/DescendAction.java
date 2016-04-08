package com.stewsters.sniper.action;


import com.stewsters.sniper.entity.Pawn;
import com.stewsters.sniper.map.TileType;

public class DescendAction extends Action {


    public DescendAction(Pawn pawn) {
        super(pawn);
    }

    @Override
    public ActionResult onPerform() {

        if (worldMap.getCellTypeAt(pawn.pos.current.x, pawn.pos.current.y, pawn.pos.current.z) != TileType.DOWN_STAIR) {
            // We are not on a downstair
            return ActionResult.FAILURE;
        }
        if (pawn.pos.current.z == 0) {
            return ActionResult.FAILURE;
        }

        // go up to next floor

        worldMap.removePawn(pawn);

        pawn.pos.previous = pawn.pos.current.copy();
        pawn.pos.current.z--;

        worldMap.addPawn(pawn);

        return ActionResult.SUCCESS;

    }
}
