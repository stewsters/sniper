package com.stewsters.sniper.action;

import com.stewsters.sniper.entity.Pawn;
import com.stewsters.sniper.map.MapChunk;

public abstract class Action {

    protected Pawn pawn;
    protected MapChunk mapChunk;

    protected Action(Pawn pawn) {
        this.pawn = pawn;
        this.mapChunk = pawn.mapChunk;
    }

    public ActionResult onPerform() {
        return null;
    }
}
