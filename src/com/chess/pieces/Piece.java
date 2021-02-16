package com.chess.pieces;

import com.chess.Alliance;
import com.chess.board.Board;
import com.chess.board.Move;

import java.util.Collection;

public abstract class Piece {
    protected final int piecePosition;
    protected final Alliance pieceAlliance;

    Piece(int piecePosition, Alliance pieceAlliance) {
        this.pieceAlliance = pieceAlliance;
        this.piecePosition = piecePosition;
    }

    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }

    public abstract Collection<Move> calculateLegalMoves(final Board board);

}
