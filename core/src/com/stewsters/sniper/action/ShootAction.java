package com.stewsters.sniper.action;


import com.stewsters.sniper.entity.Pawn;

public class ShootAction extends Action {

    Pawn target;

    public ShootAction(Pawn pawn, Pawn target) {
        super(pawn);
        this.target = target;
    }

    @Override
    public ActionResult onPerform() {

        target.health.damage(1);

        if (target.health.getHP() <= 0) {
            pawn.worldMap.removePawn(target);
        }

        return ActionResult.SUCCESS;
    }


}
