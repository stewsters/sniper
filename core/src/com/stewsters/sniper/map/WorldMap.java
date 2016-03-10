package com.stewsters.sniper.map;


import com.stewsters.sniper.entity.Item;
import com.stewsters.sniper.entity.Pawn;
import com.stewsters.sniper.game.TileType;
import com.stewsters.util.mapgen.CellType;
import com.stewsters.util.mapgen.threeDimension.GeneratedMap3d;
import com.stewsters.util.pathing.threeDimention.shared.TileBasedMap3d;

import java.util.Comparator;
import java.util.PriorityQueue;

public class WorldMap implements GeneratedMap3d, TileBasedMap3d {


    public static final int xSize = 4;
    public static final int ySize = 4;
    public static final int zSize = 1; // must always be one right now

    private MapChunk[][] chunks;

    public Pawn player;
    public PriorityQueue<Pawn> pawnQueue;


    public WorldMap() {
        chunks = new MapChunk[xSize][ySize];
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                chunks[x][y] = new MapChunk();
            }
        }

        pawnQueue = new PriorityQueue<Pawn>(new Comparator<Pawn>() {
            @Override
            public int compare(Pawn o1, Pawn o2) {
                return o1.getGameTurn().compareTo(o2.getGameTurn());
            }
        });
    }


    private static int tile(int globalCoord) {
        return globalCoord % MapChunk.xSize;
    }

    private static int chunk(int globalCoord) {
        return globalCoord / MapChunk.xSize;
    }

    @Override
    public int getXSize() {
        return xSize * MapChunk.xSize;
    }

    @Override
    public int getYSize() {
        return ySize * MapChunk.ySize;
    }

    @Override
    public int getZSize() {
        return zSize * MapChunk.zSize;
    }

    public int getXChunkSize() {
        return xSize;
    }

    public int getYChunkSize() {
        return ySize;
    }

    public MapChunk getChunk(int x, int y) {
        return chunks[x][y];
    }

    @Override
    public void pathFinderVisited(int x, int y, int z) {

    }

    @Override
    public TileType getCellTypeAt(int x, int y, int z) {
        return chunks[chunk(x)][chunk(y)].tiles[tile(x)][tile(y)][z];
    }

    @Override
    public void setCellTypeAt(int x, int y, int z, CellType cellType) {
        chunks[chunk(x)][chunk(y)].tiles[tile(x)][tile(y)][z] = (TileType) cellType;
    }


    // Pawn
    public void addPawn(Pawn pawn) {
        pawn.worldMap = this;
        pawnQueue.add(pawn);
        int x = pawn.pos.current.x;
        int y = pawn.pos.current.y;
        int z = pawn.pos.current.z;

        chunks[chunk(x)][chunk(y)].pawns[tile(x)][tile(y)][z] = pawn;
    }

    public void removePawn(Pawn pawn) {
        pawn.worldMap = null;
        pawnQueue.remove(pawn);

        int x = pawn.pos.current.x;
        int y = pawn.pos.current.y;
        int z = pawn.pos.current.z;

        chunks[chunk(x)][chunk(y)].pawns[tile(x)][tile(y)][z] = null;
    }

    public Pawn pawnAt(int x, int y, int z) {
        return chunks[chunk(x)][chunk(y)].pawns[tile(x)][tile(y)][z];
    }

    public void updatePawnPos(Pawn pawn, int xPos, int yPos, int zPos) {
        removePawn(pawn);
        pawn.pos.setWithTransition(xPos, yPos, zPos);
        addPawn(pawn);
    }

    // Item
    public void addItem(Item item) {
        item.worldMap = this;

        int x = item.pos.x;
        int y = item.pos.y;
        int z = item.pos.z;

        chunks[chunk(x)][chunk(y)].items[tile(x)][tile(y)][z] = item;
    }

    public void removeItem(Item item) {
        item.worldMap = null;

        int x = item.pos.x;
        int y = item.pos.y;
        int z = item.pos.z;

        chunks[chunk(x)][chunk(y)].items[tile(x)][tile(y)][z] = null;
    }

    public Item itemAt(int x, int y, int z) {
        return chunks[chunk(x)][chunk(y)].items[tile(x)][tile(y)][z];
    }


    public boolean isOutsideMap(int x, int y, int z) {
        return (x < 0 || x >= xSize * MapChunk.xSize || y < 0 || y >= ySize * MapChunk.ySize || z < 0 || z >= zSize * MapChunk.zSize);
    }
}
