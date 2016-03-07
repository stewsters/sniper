package com.stewsters.sniper.systems;

import com.badlogic.gdx.Gdx;
import com.stewsters.sniper.action.Action;
import com.stewsters.sniper.action.ActionResult;
import com.stewsters.sniper.entity.Pawn;
import com.stewsters.sniper.map.MapChunk;

public class TurnProcessSystem {

    private MapChunk mapChunk;

    public TurnProcessSystem(MapChunk mapChunk) {
        this.mapChunk = mapChunk;
    }

    //TODO: something is jacked here, may want to replace with a priorityQueue
    public void process() {

        // Break early if there is no one to work on
        if (mapChunk.pawnQueue.size() <= 0)
            return;

        Pawn current = mapChunk.pawnQueue.peek();
        Action action = current.getAction();

        if (action == null) {
            if (current.aiControl == null && current.playerControl == null) {
                Gdx.app.log("Error", "Inactive Pawn - removed");
                mapChunk.pawnQueue.poll();
            }

            return;
        }

        // Do action
        while (true) {
            ActionResult result = action.onPerform();

            // if it was not successful, then
            if (!result.succeeded) {
                if (current.aiControl != null) {
                    // AI player if action failed
                    Gdx.app.log("Error", "AI player action failed");
                    break;
                }
                return;
            }


            if (result.alternative == null) {
                current.setNextAction(result.nextAction);
                break;
            }
            action = result.alternative;

        }


        mapChunk.pawnQueue.poll();
        // increment time,
        current.gameTurn += 100;
        mapChunk.pawnQueue.add(current);
    }
}
