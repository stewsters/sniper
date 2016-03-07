package com.stewsters.sniper.entity;


import com.stewsters.sniper.action.Action;
import com.stewsters.sniper.component.AiControl;
import com.stewsters.sniper.component.Appearance;
import com.stewsters.sniper.component.Health;
import com.stewsters.sniper.component.Location;
import com.stewsters.sniper.component.PlayerControl;
import com.stewsters.sniper.map.MapChunk;
import com.stewsters.util.pathing.threeDimention.shared.Mover3d;


public class Pawn implements Mover3d {

    public MapChunk mapChunk;
    public Location pos;
    public Health health;
    public Appearance appearance;

    public PlayerControl playerControl;
    public AiControl aiControl;
    // ai control
    private Action nextAction;
    public boolean doorOpener;
    public boolean chaser;

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
        return mapChunk.getCellTypeAt(tx, ty, tz).blocks;
    }

    @Override
    public boolean canOccupy(int tx, int ty, int tz) {
        return mapChunk.getCellTypeAt(tx, ty, tz).blocks;
    }

    @Override
    public float getCost(int sx, int sy, int sz, int tx, int ty, int tz) {
        return 0;
    }

}
