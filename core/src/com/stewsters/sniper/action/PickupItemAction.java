package com.stewsters.sniper.action;


import com.stewsters.sniper.entity.Item;
import com.stewsters.sniper.entity.Pawn;

public class PickupItemAction extends Action {


    public PickupItemAction(Pawn pawn) {
        super(pawn);
    }

    @Override
    public ActionResult onPerform() {

        Item item = mapChunk.itemAt(pawn.pos.current.x, pawn.pos.current.y, pawn.pos.current.z);
        if (item == null) {
            return ActionResult.FAILURE;
        }

        mapChunk.removeItem(item);

        //TODO: add to player

        //TODO: Auto Equip?

//        HashMap params = new HashMap();
//        params.put("item", item);
//        pawn.fireEvent(new Event(EventType.pickUpItem, params));

        return ActionResult.SUCCESS;

    }
}
