package com.chess.board;

import com.chess.pieces.Piece;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public abstract class Tile {

    // protected so that only subclasses get access
    // final to maintain immutability
    protected final int TileCoordinate;

    private static final Map<Integer, emptyTile> EMPTY_TILES_CACHE = createAllEmptyTiles();

    private static Map<Integer,emptyTile> createAllEmptyTiles() {
        final Map<Integer, emptyTile> emptyTileMap = new HashMap<>();

        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            emptyTileMap.put(i, new emptyTile(i));
        }

        return ImmutableMap.copyOf(emptyTileMap);
    }

    public static Tile createTile(final int tileCoordinate, final Piece piece) {
        return piece != null ? new occupiedTile(tileCoordinate, piece) : EMPTY_TILES_CACHE.get(tileCoordinate);
    }

    private Tile(final int TileCoordinate) {
        this.TileCoordinate = TileCoordinate;
    }

    public abstract boolean isOccupied();

    public abstract Piece getPiece();

    public static final class emptyTile extends Tile {
        private emptyTile(final int coordinate) {
            super(coordinate);
        }

        @Override
        public boolean isOccupied() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }
    }

    public static final class occupiedTile extends Tile {
        private final Piece pieceOnTile;

        private occupiedTile(final int coordinate, final Piece pieceOnTile) {
            super(coordinate);
            this.pieceOnTile = pieceOnTile;
        }

        @Override
        public boolean isOccupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return this.pieceOnTile;
        }
    }
}
