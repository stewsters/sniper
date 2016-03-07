package com.stewsters.sniper.action;

public class ActionResult {

    public static final ActionResult SUCCESS = new ActionResult(true);
    public static final ActionResult FAILURE = new ActionResult(false);

    /// An alternate [Action] that should be performed instead of
    /// the one that failed.
    public final Action alternative;
    public final Action nextAction;

    /// `true` if the [Action] was successful and energy should
    /// be consumed.
    public final boolean succeeded;

    ActionResult(boolean succeeded) {
        this.succeeded = succeeded;
        alternative = null;
        nextAction = null;
    }

    public ActionResult(boolean succeeded, Action nextAction) {
        this.succeeded = succeeded;
        this.alternative = null;
        this.nextAction = nextAction;
    }


    public ActionResult(Action alternative) {
        this.alternative = alternative;
        this.succeeded = true;
        this.nextAction = null;
    }

}
