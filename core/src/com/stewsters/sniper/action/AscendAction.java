package com.stewsters.sniper.action;


import com.stewsters.sniper.entity.Pawn;
import com.stewsters.sniper.game.TileType;

public class AscendAction extends Action {


    public AscendAction(Pawn pawn) {
        super(pawn);
    }

    @Override
    public ActionResult onPerform() {

        if (worldMap.getCellTypeAt(pawn.pos.current.x, pawn.pos.current.y, pawn.pos.current.z) != TileType.UP_STAIR) {
            return ActionResult.FAILURE;
        }
        if (pawn.pos.current.z == worldMap.getZSize() - 1) {
            return ActionResult.FAILURE;
        }

        // go up to next floor

        worldMap.removePawn(pawn);

        pawn.pos.previous = pawn.pos.current.copy();
        pawn.pos.current.z++;


        worldMap.addPawn(pawn);

        return ActionResult.SUCCESS;

    }
}
