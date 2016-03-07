package com.stewsters.sniper.action;


import com.stewsters.sniper.entity.Pawn;
import com.stewsters.sniper.game.TileType;

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

        pawn.pos.current.z = pawn.pos.current.z - 1;
        pawn.pos.previous = pawn.pos.current.copy();

        worldMap.addPawn(pawn);

        return ActionResult.SUCCESS;

    }
}
