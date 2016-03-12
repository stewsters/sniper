package com.stewsters.sniper.component;

import com.stewsters.sniper.action.Action;
import com.stewsters.sniper.action.ShootAction;
import com.stewsters.sniper.action.WalkAction;
import com.stewsters.sniper.entity.Pawn;
import com.stewsters.util.math.MatUtils;
import com.stewsters.util.math.Point2i;

import java.awt.*;

public class AiControl extends Component {

    private Pawn pawn;


    public AiControl(Pawn pawn) {
        this.pawn = pawn;
    }

    public Action act() {

        if (pawn.shooter && pawn.snipe != null) {

            if (pawn.snipe.notice >= 1 && pawn.snipe.returnPercentage >= 1) {
                return new ShootAction(pawn, pawn.worldMap.player);
            }
        }

        if (pawn.chaser) {

            //TODO: if we try to move and hit a failure, our ai loops on that failure

            //find a nearby player

            Pawn player = pawn.worldMap.player;


            if (player != null) {

                int dx = player.pos.current.x - pawn.pos.current.x;
                int dy = player.pos.current.y - pawn.pos.current.y;
                int x, y = 0;

                if (dx == 0 && dy == 0) {
                    x = MatUtils.getIntInRange(-1, 1);
                    y = MatUtils.getIntInRange(-1, 1);

                } else {
                    x = MatUtils.limit(player.pos.current.x - pawn.pos.current.x, -1, 1);
                    y = MatUtils.limit(player.pos.current.y - pawn.pos.current.y, -1, 1);
                }

                return new WalkAction(pawn, new Point2i(x, y));
            }
        }
        // AI stuff goes here
        return new WalkAction(pawn, new Point2i(MatUtils.getIntInRange(-1, 1), MatUtils.getIntInRange(-1, 1)));
    }

}
