package com.chess.pieces;

import com.chess.Alliance;
import com.chess.board.Board;
import com.chess.board.BoardUtils;
import com.chess.board.Move;
import com.chess.board.Move.AttackMove;
import com.chess.board.Move.MajorMove;
import com.chess.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class King extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDS = {-9, -8, -7, -1, 1, 7, 8, 9};

    King(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDS) {
            final int candidateDestinationCoordinate =  this.piecePosition + currentCandidateOffset;

            if (isFirstFileExclusion(this.piecePosition, currentCandidateOffset) ||
                    isEighthFileExclusion(this.piecePosition, currentCandidateOffset)) {
                continue;
            }
            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if (!candidateDestinationTile.isOccupied()) {
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));   // Valid, non-attacking move
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                    if (this.pieceAlliance != pieceAlliance) {
                        legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));   // Valid, attacking move
                    }
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    private static boolean isFirstFileExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_FILE[currentPosition] && ((candidateOffset == -9) || (candidateOffset == -1)
                || (candidateOffset == 7));
    }

    private static boolean isEighthFileExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHTH_FILE[currentPosition] && ((candidateOffset == -7) || (candidateOffset == 1)
                || (candidateOffset == 9));
    }
}
