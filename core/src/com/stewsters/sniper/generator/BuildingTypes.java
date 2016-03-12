package com.stewsters.sniper.generator;

public enum BuildingTypes {

    BARE(false, true),
    //    ONE_HIGH(1, true),
    FULL_WITH_WINDOWS(true, false),
    FILL_WALLED_IN(true, false),
    OLD_CHURCH(true, true); //bell tower

    // Bare with supports - construction
    // one high with supports -
    // full with holes
    // full walled in

    public int maxHeight = 8;

    BuildingTypes(boolean wall, boolean cornerPillar) {
        this.wall = wall;
        this.cornerPillar = cornerPillar;
    }


    public final boolean wall;
    public final boolean cornerPillar;
}
