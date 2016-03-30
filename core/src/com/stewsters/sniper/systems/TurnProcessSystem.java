package com.stewsters.sniper.systems;

import com.stewsters.sniper.action.Action;
import com.stewsters.sniper.action.ActionResult;
import com.stewsters.sniper.entity.Pawn;
import com.stewsters.sniper.map.WorldMap;

public class TurnProcessSystem {

    private WorldMap worldMap;
    SnipeSystem snipeSystem;
    GravitySystem gravitySystem;

    public TurnProcessSystem(WorldMap worldMap) {
        this.worldMap = worldMap;
        snipeSystem = new SnipeSystem(worldMap);
        gravitySystem = new GravitySystem(worldMap);
    }

    public void process() {


        while (worldMap.player.health.hp > 0) {
            // Break early if there is no one to work on
            if (worldMap.pawnQueue.size() <= 0)
                return;

            Pawn current = worldMap.pawnQueue.peek();
            Action action = current.getAction();

            if (action == null) {
//            if (current.aiControl == null && current.playerControl == null) {
//                Gdx.app.log("Error", "Inactive Pawn - removed");
//                worldMap.pawnQueue.poll();
//            }

                gravitySystem.processSystem();
                snipeSystem.processSystem();
                return; // Looks like the player needs to choose what to do
            }

            // Do action until it succeeds
            while (true) {
                ActionResult result = action.onPerform();

                // if it was not successful, then
                if (!result.succeeded) {
                    if (current.aiControl != null)
                        break;
                    return;
                }

                if (result.alternative == null) {
                    current.setNextAction(result.nextAction);
                    break;
                }

                action = result.alternative;
            }

            current = worldMap.pawnQueue.poll();
            // increment time,
            current.gameTurn += 100;
            worldMap.pawnQueue.add(current);
        }
    }
}
