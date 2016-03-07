package com.stewsters.sniper.component;


public class Health {

    public int hp;
    public int max;


    public Health(int max) {
        this.max = max;
        this.hp = max;
    }

    public void heal(int i) {
        hp = Math.min(hp + i, max);
    }

    public void damage(int i) {
        hp = Math.max(hp - i, 0);
    }

    public int getHP() {
        return hp;
    }
}
