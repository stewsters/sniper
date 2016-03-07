package com.stewsters.sniper.action;


import com.stewsters.sniper.entity.Pawn;
import com.stewsters.sniper.game.TileType;

public class CloseAdjacentDoors extends Action {
    public CloseAdjacentDoors(Pawn pawn) {
        super(pawn);
    }

    @Override
    public ActionResult onPerform() {

        int z = pawn.pos.current.z;
        for (int x = pawn.pos.current.x - 1; x <= pawn.pos.current.x + 1; x++) {
            for (int y = pawn.pos.current.y - 1; y <= pawn.pos.current.y + 1; y++) {

                if (!mapChunk.isOutsideMap(x, y, z)
                        && mapChunk.getCellTypeAt(x, y, z) == TileType.OPEN_DOOR
                        && !pawn.canOccupy(x, y, z)
                        && mapChunk.pawnAt(x, y, z) == null) {

                    mapChunk.setCellTypeAt(x, y, z, TileType.CLOSED_DOOR);
                    return ActionResult.SUCCESS;
                }
            }
        }

        return ActionResult.FAILURE;

    }

}
