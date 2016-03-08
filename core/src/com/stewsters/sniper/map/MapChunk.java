package com.stewsters.sniper.map;

import com.stewsters.sniper.entity.Item;
import com.stewsters.sniper.entity.Pawn;
import com.stewsters.sniper.game.TileType;

public class MapChunk {
    public static final int xSize = 64;
    public static final int ySize = 64;
    public static final int zSize = 32;

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

}
