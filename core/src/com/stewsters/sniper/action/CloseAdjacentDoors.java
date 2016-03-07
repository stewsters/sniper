package com.stewsters.sniper.action;


import com.stewsters.sniper.entity.Pawn;
import com.stewsters.sniper.game.TileType;

public class CloseAdjacentDoors extends Action {
    public CloseAdjacentDoors(Pawn pawn) {
        super(pawn);
    }

    @Override
    public ActionResult onPerform() {

        for (int x = pawn.pos.current.x - 1; x <= pawn.pos.current.x + 1; x++) {
            for (int y = pawn.pos.current.y - 1; y <= pawn.pos.current.y + 1; y++) {
                if (mapChunk.getCellTypeAt(x, y, pawn.pos.current.z) == TileType.OPEN_DOOR
                        && !pawn.canOccupy(x, y, pawn.pos.current.z)
                        && mapChunk.pawnAt(x, y, pawn.pos.current.z) == null) {

                    mapChunk.setCellTypeAt(x, y, pawn.pos.current.z, TileType.CLOSED_DOOR);
                    return ActionResult.SUCCESS;
                }
            }
        }

        return ActionResult.FAILURE;

    }

}
