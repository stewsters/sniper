package com.stewsters.sniper.generator;

public enum BuildingTypes {

    BARE(0, true),
    ONE_HIGH(1, true),
    FULL_WITH_WINDOWS(2, false),
    FILL_WALLED_IN(2, false),
    OLD_CHURCH(0, true);
    // Bare with supports - construction
    // one high with supports -
    // full with holes
    // full walled in

    public int maxHeight = 8;

    BuildingTypes(int wallHeight, boolean cornerPillar) {
        this.wall = wallHeight;
        this.cornerPillar = cornerPillar;
    }


    public final int wall;
    public final boolean cornerPillar;
}
