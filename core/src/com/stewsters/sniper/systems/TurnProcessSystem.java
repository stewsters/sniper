package com.stewsters.sniper.systems;

import com.badlogic.gdx.Gdx;
import com.stewsters.sniper.action.Action;
import com.stewsters.sniper.action.ActionResult;
import com.stewsters.sniper.entity.Pawn;
import com.stewsters.sniper.map.MapChunk;

import java.util.List;

public class TurnProcessSystem {

    private MapChunk mapChunk;
    private int currentPawn = 0;

    public TurnProcessSystem(MapChunk mapChunk) {
        this.mapChunk = mapChunk;
    }

    public void process() {

        List<Pawn> pawnList = mapChunk.getPawnList();

        if (pawnList.size() <= 0)
            return;

        Pawn current = pawnList.get(currentPawn);
        Action action = current.getAction();
        if (action == null) {
            if (current.aiControl == null && current.playerControl == null) {
                Gdx.app.log("Stage", "Inactive Pawn");
                currentPawn = (currentPawn + 1) % pawnList.size();
            }
            return;
        }

        Action nextAction = null;
        while (true) {
            ActionResult result = action.onPerform();

            if (!result.succeeded) {
                if (pawnList.get(currentPawn).aiControl != null) {
//                    new RestAction(pawnList.get(currentPawn)).onPerform();
                    break;
                }

                return;
            }


            if (result.alternative == null) {
                nextAction = result.nextAction;
                break;
            }
            action = result.alternative;

        }
        if (nextAction != null) {
            current.setNextAction(nextAction);
        }

        if (pawnList.size() == 0) {
            currentPawn = 0;
        } else
            currentPawn = (currentPawn + 1) % pawnList.size();
    }
}
