package com.stewsters.sniper.component;

import com.stewsters.sniper.action.Action;
import com.stewsters.sniper.action.WalkAction;
import com.stewsters.sniper.entity.Pawn;
import com.stewsters.util.math.MatUtils;
import com.stewsters.util.math.Point2i;
import com.stewsters.util.pathing.threeDimention.pathfinder.ChebyshevHeuristic3d;

import java.awt.*;

public class AiControl extends Component {

    private Pawn pawn;
    ChebyshevHeuristic3d distance;

    public AiControl(Pawn player) {
        this.pawn = player;
    }

    public Action act() {


//        pawn.
        if (pawn.chaser) {

            //TODO: if we try to move and hit a failure, our ai loops on that failure

            //find a nearby player

            Pawn player = pawn.mapChunk.player;


            if (player != null) {

                int dx = player.pos.current.x - pawn.pos.current.x;
                int dy = player.pos.current.y - pawn.pos.current.y;
                int x, y = 0;

                if (dx == 0 && dy == 0) {
                    x = MatUtils.getIntInRange(-1, 1);
                    y = MatUtils.getIntInRange(-1, 1);

                } else if (Math.abs(dx) > Math.abs(dy)) {
                    x = MatUtils.limit(player.pos.current.x - pawn.pos.current.x, -1, 1);
                    y = 0;

                } else {
                    x = 0;
                    y = MatUtils.limit(player.pos.current.y - pawn.pos.current.y, -1, 1);
                }

                return new WalkAction(pawn, new Point2i(x, y));
            }
        }
        // AI stuff goes here
        return new WalkAction(pawn, new Point2i(MatUtils.getIntInRange(-1, 1), MatUtils.getIntInRange(-1, 1)));
    }

}
