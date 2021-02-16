package com.chess.pieces;

import com.chess.Alliance;
import com.chess.board.Board;
import com.chess.board.BoardUtils;
import com.chess.board.Move;
import com.chess.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

    // These moves might be valid, but need to be checked
    private final static int[] CANDIDATE_MOVE_COORDS = {-17, -15, -10, -6, 6, 10, 15, 17};

    Knight(final int piecePosition, final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public List<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDS) {
            final int candidateDestinationCoord = this.piecePosition + currentCandidateOffset;
            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoord)) {

                if (isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                        isSecondColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                        isSeventhColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                        isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
                    continue;
                }
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoord);
                if (!candidateDestinationTile.isOccupied()) {
                    legalMoves.add(new Move);   // Valid, non-attacking move
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                    if (this.pieceAlliance != pieceAlliance) {
                        legalMoves.add(new Move);   // Valid, attacking move
                    }
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    // Edge cases for when knight can't move as usual (1st and 8th rank)
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && ((candidateOffset == -17) || (candidateOffset == -10)
        || (candidateOffset == 6) || (candidateOffset == 15));
    }

    private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.SECOND_COLUMN[currentPosition] && ((candidateOffset == -10) || (candidateOffset == 6));
    }

    private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && ((candidateOffset == -6) || (candidateOffset == 10));
    }

    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && ((candidateOffset == -15) || (candidateOffset == -6)
        || (candidateOffset == 10) || (candidateOffset == 17));
    }

}
