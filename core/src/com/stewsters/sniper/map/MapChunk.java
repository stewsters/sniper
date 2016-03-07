package com.stewsters.sniper.map;


import com.stewsters.sniper.entity.Item;
import com.stewsters.sniper.entity.Pawn;
import com.stewsters.sniper.game.TileType;
import com.stewsters.util.mapgen.CellType;
import com.stewsters.util.mapgen.threeDimension.GeneratedMap3d;
import com.stewsters.util.pathing.threeDimention.shared.TileBasedMap3d;

import java.util.ArrayList;
import java.util.List;

public class MapChunk implements GeneratedMap3d, TileBasedMap3d {
    public static final int xSize = 64;
    public static final int ySize = 64;
    public static final int zSize = 32;


    public TileType[][][] tiles;

    private Pawn[][][] pawns;
    private List<Pawn> pawnList;
    private Item[][][] items;

    public Pawn player;


    public MapChunk() {
        tiles = new TileType[xSize][ySize][zSize];
        pawns = new Pawn[xSize][ySize][zSize];
        pawnList = new ArrayList<Pawn>();
        items = new Item[xSize][ySize][zSize];
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

    @Override
    public TileType getCellTypeAt(int x, int y, int z) {
        return tiles[x][y][z];
    }

    @Override
    public void setCellTypeAt(int x, int y, int z, CellType cellType) {
        tiles[x][y][z] = (TileType) cellType;
    }


    // Pawn
    public void addPawn(Pawn pawn) {
        pawn.mapChunk = this;
        pawnList.add(pawn);
        pawns[pawn.pos.current.x][pawn.pos.current.y][pawn.pos.current.z] = pawn;
    }

    public void removePawn(Pawn pawn) {
        pawn.mapChunk = null;
        pawnList.remove(pawn);
        pawns[pawn.pos.current.x][pawn.pos.current.y][pawn.pos.current.z] = null;
    }

    public Pawn pawnAt(int xPos, int yPos, int zPos) {
        return pawns[xPos][yPos][zPos];
    }

    public void updatePawnPos(Pawn pawn, int xPos, int yPos, int zPos) {
        removePawn(pawn);
        pawn.pos.setWithTransition(xPos, yPos, zPos);
        addPawn(pawn);
    }

    public List<Pawn> getPawnList() {
        return pawnList;
    }

    // Item
    public void addItem(Item item) {
        item.mapChunk = this;
        items[item.pos.x][item.pos.y][item.pos.z] = item;
        //        itemList.add(item);
    }

    public void removeItem(Item item) {
        item.mapChunk = null;
        items[item.pos.x][item.pos.y][item.pos.z] = null;
    }

    public Item itemAt(int x, int y, int z) {
        return items[x][y][z];
    }


}
