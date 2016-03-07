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

        //TODO: attack the target with currently equipped melee weapon

        //TODO: add in stats
        target.health.damage(1);
        //TODO: death?

        if (target.health.getHP() <= 0) {
            target.mapChunk.removePawn(target);
        }

        return ActionResult.SUCCESS;
    }


}
