package com.stewsters.sniper.map;

import com.stewsters.sniper.entity.Item;
import com.stewsters.sniper.entity.Pawn;
import com.stewsters.util.pathing.threeDimention.shared.TileBasedMap3d;

public class MapChunk implements TileBasedMap3d {
    public static final int xSize = 32;
    public static final int ySize = 32;
    public static final int zSize = 16;

    public TileType[][][] tiles;
    public Pawn[][][] pawns;
    public Item[][][] items;

    public MapChunk() {

        tiles = new TileType[xSize][ySize][zSize];
        pawns = new Pawn[xSize][ySize][zSize];
        items = new Item[xSize][ySize][zSize];

//        for (int x = 0; x < xSize; x++) {
//            for (int y = 0; y < ySize; y++) {
//                for (int z = 0; z < zSize; z++) {
//
//                }
//            }
//        }
    }


    public boolean isOutsideMap(int x, int y, int z) {
        return (x < 0 || x >= xSize || y < 0 || y >= ySize || z < 0 || z >= zSize);
    }

    @Override
    public int getXSize() {
        return xSize;
    }

    @Override
    public int getYSize() {
        return ySize;
    }

    @Override
    public int getZSize() {
        return zSize;
    }

    @Override
    public void pathFinderVisited(int x, int y, int z) {

    }

}
