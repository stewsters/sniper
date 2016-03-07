package com.stewsters.sniper.component;

import com.stewsters.util.pathing.threeDimention.shared.FullPath3d;

/**
 * This is an Ai agent's planned path
 */
public class Path {

    public FullPath3d fullPath3d;
    public int step;

    public Path set(FullPath3d path) {

        fullPath3d = path;
        step = 0;
        return this;
    }
}
