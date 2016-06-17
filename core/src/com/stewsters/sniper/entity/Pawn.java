package com.stewsters.sniper.entity;


import com.stewsters.sniper.action.Action;
import com.stewsters.sniper.component.AiControl;
import com.stewsters.sniper.component.Appearance;
import com.stewsters.sniper.component.Health;
import com.stewsters.sniper.component.Location;
import com.stewsters.sniper.component.PlayerControl;
import com.stewsters.sniper.component.Snipe;
import com.stewsters.sniper.map.WorldMap;
import com.stewsters.util.pathing.threeDimention.shared.Mover3d;


public class Pawn implements Mover3d {

    public WorldMap worldMap;
    public Location pos;
    public int xSize = 1;
    public int ySize = 1;
    public int zSize = 1;

    public Health health;
    public Appearance appearance;
    public Snipe snipe;

    public PlayerControl playerControl;
    public AiControl aiControl;

    private Action nextAction;
    public long gameTurn;

    public boolean doorOpener = false;
    public boolean chaser = false;
    public boolean shooter = false;
    public boolean smasher = false;

    public double stealth = 0;
    public int kills = 0;


    public void setNextAction(Action nextAction) {

        this.nextAction = nextAction;
    }

    public Action getAction() {
        if (nextAction == null && aiControl != null) {
            return aiControl.act();
        }

        Action action = nextAction;
        nextAction = null;
        return action;
    }

    @Override
    public boolean canTraverse(int sx, int sy, int sz, int tx, int ty, int tz) {
        return canOccupy(tx, ty, tz);
    }

    @Override
    public boolean canOccupy(int tx, int ty, int tz) {

        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                for (int z = 0; z < zSize; z++) {

                    if (worldMap.getCellTypeAt(tx + x, ty + y, tz + z).blocks) {
                        return false;
                    }

                }
            }
        }
        return true;
    }

    @Override
    public float getCost(int sx, int sy, int sz, int tx, int ty, int tz) {
        return 1;
    }

    public Long getGameTurn() {
        return gameTurn;
    }
}
