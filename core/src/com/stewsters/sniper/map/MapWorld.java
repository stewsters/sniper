package com.stewsters.sniper.map;


public class MapWorld {

    public final int worldX;
    public final int worldY;
    MapChunk[][] mapChunks;


    public MapWorld(int x, int y) {
        worldX = x;
        worldY = y;
        mapChunks = new MapChunk[x][y];
    }


}
