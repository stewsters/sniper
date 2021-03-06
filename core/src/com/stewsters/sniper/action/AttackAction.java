package com.stewsters.sniper.action;


import com.stewsters.sniper.entity.Pawn;

public class AttackAction extends Action {

    Pawn target;

    public AttackAction(Pawn pawn, Pawn target) {
        super(pawn);
        this.target = target;
    }

    @Override
    public ActionResult onPerform() {

        target.health.damage(1);

        if (target.health.getHP() <= 0) {
            target.worldMap.removePawn(target);
            pawn.kills++;
        }

        return ActionResult.SUCCESS;
    }


}
