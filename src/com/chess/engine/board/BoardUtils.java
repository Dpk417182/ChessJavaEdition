package com.chess.engine.board;

public class BoardUtils {

    public static final boolean[] FIRST_FILE = initFile(0);
    public static final boolean[] SECOND_FILE = initFile(1);
    public static final boolean[] SEVENTH_FILE = initFile(6);
    public static final boolean[] EIGHTH_FILE = initFile(7);

    public static final boolean[] SECOND_RANK = initRank(8);
    public static final boolean[] SEVENTH_RANK = initRank(48);

    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_ROW = 8;

    private BoardUtils() {
        throw new RuntimeException("Cannot instantiate BoardUtils!");
    }

    public static boolean isValidTileCoordinate(final int coordinate) {
        return coordinate >= 0 && coordinate < NUM_TILES;
    }

    private static boolean[] initFile(int fileNumber) {
        final boolean[] file = new boolean[NUM_TILES];
        do {
            file[fileNumber] = true;
            fileNumber += NUM_TILES_PER_ROW;
        } while(fileNumber < NUM_TILES);

        return file;
    }

    private static boolean[] initRank(int rankNumber) {
        final boolean[] rank = new boolean[NUM_TILES];
        do {
            rank[rankNumber] = true;
            rankNumber++;
        } while(rankNumber % NUM_TILES_PER_ROW != 0);

        return rank;
    }
}
