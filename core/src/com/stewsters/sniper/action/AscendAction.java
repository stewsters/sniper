package com.stewsters.sniper.action;


import com.stewsters.sniper.entity.Pawn;
import com.stewsters.sniper.game.TileType;

public class AscendAction extends Action {


    public AscendAction(Pawn pawn) {
        super(pawn);
    }

    @Override
    public ActionResult onPerform() {

        if (mapChunk.getCellTypeAt(pawn.pos.current.x, pawn.pos.current.y, pawn.pos.current.z) != TileType.UP_STAIR) {
            return ActionResult.FAILURE;
        }
        if (pawn.pos.current.z == mapChunk.getZSize() - 1) {
            return ActionResult.FAILURE;
        }

        // go up to next floor

        mapChunk.removePawn(pawn);

        pawn.pos.current.z = pawn.pos.current.z + 1;
        pawn.pos.previous = pawn.pos.current;

        mapChunk.addPawn(pawn);

        return ActionResult.SUCCESS;

    }
}
