package com.stewsters.sniper.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.stewsters.sniper.action.AscendAction;
import com.stewsters.sniper.action.CloseAdjacentDoors;
import com.stewsters.sniper.action.DescendAction;
import com.stewsters.sniper.action.PickupItemAction;
import com.stewsters.sniper.action.RestAction;
import com.stewsters.sniper.action.ShootAction;
import com.stewsters.sniper.action.WalkAction;
import com.stewsters.sniper.entity.Pawn;
import com.stewsters.util.math.Point2i;

import java.util.ArrayList;

public class PlayerControl implements InputProcessor {

    private Pawn pawn;

    public ArrayList<Pawn> validTargets = new ArrayList<>();

    public PlayerControl(Pawn pawn) {
        this.pawn = pawn;
    }


    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
            case Input.Keys.NUMPAD_4:
                pawn.setNextAction(new WalkAction(pawn, new Point2i(-1, 0)));
                break;
            case Input.Keys.RIGHT:
            case Input.Keys.NUMPAD_6:
                pawn.setNextAction(new WalkAction(pawn, new Point2i(1, 0)));
                break;
            case Input.Keys.UP:
            case Input.Keys.NUMPAD_8:
                pawn.setNextAction(new WalkAction(pawn, new Point2i(0, 1)));
                break;
            case Input.Keys.DOWN:
            case Input.Keys.NUMPAD_2:
                pawn.setNextAction(new WalkAction(pawn, new Point2i(0, -1)));
                break;
            case Input.Keys.NUMPAD_7:
                pawn.setNextAction(new WalkAction(pawn, new Point2i(-1, 1)));
                break;
            case Input.Keys.NUMPAD_9:
                pawn.setNextAction(new WalkAction(pawn, new Point2i(1, 1)));
                break;
            case Input.Keys.NUMPAD_3:
                pawn.setNextAction(new WalkAction(pawn, new Point2i(1, -1)));
                break;
            case Input.Keys.NUMPAD_1:
                pawn.setNextAction(new WalkAction(pawn, new Point2i(-1, -1)));
                break;
            case Input.Keys.C:
                pawn.setNextAction(new CloseAdjacentDoors(pawn));
                break;
            case Input.Keys.G:
                pawn.setNextAction(new PickupItemAction(pawn));
                break;
            case Input.Keys.SPACE:
            case Input.Keys.NUMPAD_5:
                pawn.setNextAction(new RestAction(pawn));
                break;
            case Input.Keys.COMMA:
                pawn.setNextAction(new AscendAction(pawn));
                break;
            case Input.Keys.PERIOD:
                pawn.setNextAction(new DescendAction(pawn));
                break;
            case Input.Keys.NUM_1:
            case Input.Keys.NUM_2:
            case Input.Keys.NUM_3:
            case Input.Keys.NUM_4:
            case Input.Keys.NUM_5:
            case Input.Keys.NUM_6:
            case Input.Keys.NUM_7:
            case Input.Keys.NUM_8:
            case Input.Keys.NUM_9:
                int offset = keycode - Input.Keys.NUM_1;

                if (validTargets.size() > offset && validTargets.get(offset).snipe.percentageToKill >= 1) {
                    Pawn target = validTargets.get(offset);
                    pawn.setNextAction(new ShootAction(pawn, target));
                }
                break;
            default:
                Gdx.app.log(this.getClass().getName(), "Key not recognized by player");
                break;
        }
        Gdx.app.log("info", "keydown " + keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
