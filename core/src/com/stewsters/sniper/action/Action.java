package com.stewsters.sniper.action;

import com.stewsters.sniper.entity.Pawn;
import com.stewsters.sniper.map.WorldMap;

public abstract class Action {

    protected Pawn pawn;
    protected WorldMap worldMap;

    protected Action(Pawn pawn) {
        this.pawn = pawn;
        this.worldMap = pawn.worldMap;
    }

    public ActionResult onPerform() {
        return null;
    }
}
