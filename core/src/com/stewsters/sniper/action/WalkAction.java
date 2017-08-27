package com.stewsters.sniper.action;


import com.stewsters.sniper.entity.Pawn;
import com.stewsters.sniper.map.TileType;
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

        int xCur = pawn.pos.current.x;
        int yCur = pawn.pos.current.y;
        int zCur = pawn.pos.current.z;


        int xPos = xCur + offset.x;
        int yPos = yCur + offset.y;
        int zPos = zCur;


        if (worldMap.isOutsideMap(xPos, yPos, zPos)) {
            return ActionResult.FAILURE;
        }

        //See if there is an actor there
        boolean canTraverse = pawn.canTraverse(xCur, yCur, zCur, xPos, yPos, zPos);

        Pawn target = worldMap.pawnInSquare(xPos, yPos, zPos, xPos + pawn.xSize - 1, yPos + pawn.ySize - 1, zPos + pawn.zSize - 1);

        if (target != null && target != pawn) {
            if ((pawn.aiControl != null) != (target.aiControl != null))
                return new ActionResult(new AttackAction(pawn, target));
            else
                return ActionResult.FAILURE;
        }

        TileType targetTileType = worldMap.getCellTypeAt(xPos, yPos, zPos);
        if (targetTileType == TileType.CLOSED_DOOR) {
            return new ActionResult(new OpenDoorAction(pawn, new Point3i(xPos, yPos, zPos)));
        }

        if (targetTileType == TileType.GLASS) {
            return new ActionResult(new ShatterGlassAction(pawn, new Point3i(xPos, yPos, zPos)));
        }

        // See if we can walk there.
        if (!canTraverse) {
            return ActionResult.FAILURE;
        }

        // At this point we know that we can walk, lets do it
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
