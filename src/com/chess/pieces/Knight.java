package com.chess.pieces;

import com.chess.Alliance;
import com.chess.board.Board;
import com.chess.board.BoardUtils;
import com.chess.board.Move;
import com.chess.board.Move.MajorMove;
import com.chess.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

import static com.chess.board.Move.*;

public class Knight extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDS = {-17, -15, -10, -6, 6, 10, 15, 17};

    public Knight(final int piecePosition, final Alliance pieceAlliance) {
        super(PieceType.KNIGHT, piecePosition, pieceAlliance);
    }

    @Override
    public List<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDS) {
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {

                if (isFirstFileExclusion(this.piecePosition, currentCandidateOffset) ||
                        isSecondFileExclusion(this.piecePosition, currentCandidateOffset) ||
                        isSeventhFileExclusion(this.piecePosition, currentCandidateOffset) ||
                        isEighthFileExclusion(this.piecePosition, currentCandidateOffset)) {
                    continue;
                }
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if (!candidateDestinationTile.isOccupied()) {
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));   // Valid, non-attacking move
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                    if (this.pieceAlliance != pieceAlliance) {
                        legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate,  pieceAtDestination));   // Valid, attacking move
                    }
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Knight movePiece(Move move) {
        return new Knight(move.getDestinationCoordinate(), move.getMovedPiece().getPieceAlliance());
    }


    @Override
    public String toString() {
        return PieceType.KNIGHT.toString();
    }

    // Edge cases for when knight can't move as usual (1st, 2nd and 7th, 8th ranks)
    private static boolean isFirstFileExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_FILE[currentPosition] && ((candidateOffset == -17) || (candidateOffset == -10)
        || (candidateOffset == 6) || (candidateOffset == 15));
    }

    private static boolean isSecondFileExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.SECOND_FILE[currentPosition] && ((candidateOffset == -10) || (candidateOffset == 6));
    }

    private static boolean isSeventhFileExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.SEVENTH_FILE[currentPosition] && ((candidateOffset == -6) || (candidateOffset == 10));
    }

    private static boolean isEighthFileExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHTH_FILE[currentPosition] && ((candidateOffset == -15) || (candidateOffset == -6)
        || (candidateOffset == 10) || (candidateOffset == 17));
    }

}
