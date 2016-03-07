package com.stewsters.sniper.component;

import com.stewsters.util.math.MatUtils;
import com.stewsters.util.math.Point3i;

/**
 * This is a location that can slowly transition graphically.
 */
public class Location {

    public int timeToMove = 100000000; // 1/10 second

    public Point3i current;
    public Point3i previous;

    private long lastUpdatedNanoTime = 0;

    public Location(Point3i location) {
        current = location;
        previous = new Point3i(location.x, location.y, location.z);
    }

    public void setWithTransition(int x, int y, int z) {
        previous.x = current.x;
        previous.y = current.y;
        previous.z = current.z;

        current.x = x;
        current.y = y;
        current.z = z;

        lastUpdatedNanoTime = System.nanoTime();
    }

    public float getRenderedX() {
        long now = System.nanoTime();
        if (lastUpdatedNanoTime + timeToMove < now) {
            //we do nothing
            return current.x;
        } else {
            float d = MatUtils.smootherStep(1, 0, (float) (lastUpdatedNanoTime + timeToMove - now) / timeToMove);
            return (1 - d) * previous.x + (d) * current.x;
        }
    }

    public float getRenderedY() {
        long now = System.nanoTime();
        if (lastUpdatedNanoTime + timeToMove < now) {
            //we do nothing
            return current.y;
        } else {
            float d = MatUtils.smootherStep(1, 0, (float) (lastUpdatedNanoTime + timeToMove - now) / timeToMove);
            return (1 - d) * previous.y + (d) * current.y;
        }
    }


}
