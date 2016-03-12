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

                if (!worldMap.isOutsideMap(x, y, z)
                        && worldMap.getCellTypeAt(x, y, z) == TileType.OPEN_DOOR
                        && worldMap.pawnAt(x, y, z) == null) {

                    worldMap.setCellTypeAt(x, y, z, TileType.CLOSED_DOOR);
                    return ActionResult.SUCCESS;
                }
            }
        }

        return ActionResult.FAILURE;

    }

}
