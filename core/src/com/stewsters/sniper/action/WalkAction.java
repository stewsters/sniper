package com.stewsters.sniper.action;


import com.stewsters.sniper.entity.Pawn;
import com.stewsters.sniper.game.TileType;
import com.stewsters.util.math.Point2i;
import com.stewsters.util.math.Point3i;

public class WalkAction extends Action {

    private final Point2i offset;


    public WalkAction(Pawn pawn, Point2i offset) {
        super(pawn);
        this.offset = offset;
    }

    @Override

    public ActionResult onPerform() {

        if (offset.x == 0 && offset.y == 0) {
            return new ActionResult(new RestAction(pawn));
        }

        int xPos = pawn.pos.current.x + offset.x;
        int yPos = pawn.pos.current.y + offset.y;
        int zPos = pawn.pos.current.z;

        if (worldMap.isOutsideMap(xPos, yPos, zPos)) {
            return ActionResult.FAILURE;
        }

        //See if there is an actor there
        Pawn target = worldMap.pawnAt(xPos, yPos, zPos);
        if (target != null && target != pawn) {
            return new ActionResult(new AttackAction(pawn, target));
        }

        TileType targetTileType = worldMap.getCellTypeAt(xPos, yPos, zPos);
        if (targetTileType == TileType.CLOSED_DOOR) {
            return new ActionResult(new OpenDoorAction(pawn, new Point3i(xPos, yPos, zPos)));
        }

        // See if we can walk there.
        if (!pawn.canTraverse(pawn.pos.current.x, pawn.pos.current.y, pawn.pos.current.z, xPos, yPos, zPos)) {
            return ActionResult.FAILURE;
        }


        //TODO: fix this
        pawn.worldMap.updatePawnPos(pawn, xPos, yPos, zPos);


        // See if the hero stepped on anything interesting that would cause them to react.

        if (targetTileType == TileType.UP_STAIR) {
            return new ActionResult(true, new AscendAction(pawn));
        }
        if (targetTileType == TileType.DOWN_STAIR) {
            return new ActionResult(true, new DescendAction(pawn));
        }

        return ActionResult.SUCCESS;
    }
}
