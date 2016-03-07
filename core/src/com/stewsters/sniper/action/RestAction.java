package com.stewsters.sniper.action;


import com.stewsters.sniper.entity.Pawn;

public class RestAction extends Action {


    public RestAction(Pawn pawn) {
        super(pawn);
    }

    @Override
    public ActionResult onPerform() {
        if (pawn.health != null) {
            pawn.health.heal(1);
        }
        return ActionResult.SUCCESS;
    }
}
