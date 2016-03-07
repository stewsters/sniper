package com.stewsters.sniper.component;

import com.stewsters.util.math.Point3i;

public class Destination {

    public Point3i destination = new Point3i(0, 0, 0);
    public int distance = 0;

    public Destination set(int x, int y, int z, int distance) {
        destination.x = x;
        destination.y = y;
        destination.z = z;

        this.distance = distance;
        return this;
    }

    public Destination set(int x, int y, int z) {
        return set(x, y, z, 0);
    }

    public Destination set(Point3i target) {
        return set(target.x, target.y, target.z);
    }

    public Destination set(Point3i target, int distance) {
        return set(target.x, target.y, target.z, distance);
    }
}
