package com.chess.board;

import com.chess.Alliance;
import com.chess.pieces.*;
import com.chess.player.BlackPlayer;
import com.chess.player.Player;
import com.chess.player.WhitePlayer;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import java.util.*;

public class Board {

     private final List<Tile> gameBoard;
     private final Collection<Piece> whitePieces;
     private final Collection<Piece> blackPieces;

     private final WhitePlayer whitePlayer;
     private final BlackPlayer blackPlayer;
     private final Player currentPlayer;

     private Board(final Builder builder) {
          this.gameBoard = createGameBoard(builder);
          this.whitePieces = calculateActivePieces(this.gameBoard, Alliance.WHITE);
          this.blackPieces = calculateActivePieces(this.gameBoard, Alliance.BLACK);
          
          final Collection<Move> whiteStandardLegalMoves = calculateLegalMoves(this.whitePieces);
          final Collection<Move> blackStandardLegalMoves = calculateLegalMoves(this.blackPieces);

          this.whitePlayer = new WhitePlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
          this.blackPlayer = new BlackPlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
          this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.whitePlayer, this.blackPlayer);
     }

     @Override
     public String toString() {
          final StringBuilder builder = new StringBuilder();
          for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
               final String tileText = this.gameBoard.get(i).toString();
               builder.append(String.format("%3s", tileText));
               if ((i+1) % BoardUtils.NUM_TILES_PER_ROW == 0) {
                    builder.append("\n");
               }
          }

          return builder.toString();
     }

     public Player whitePlayer() {
          return this.whitePlayer;
     }

     public Player blackPlayer() {
          return this.blackPlayer;
     }

     public Player currentPlayer() {
          return this.currentPlayer;
     }

     public Collection<Piece> getBlackPieces() {
          return this.blackPieces;
     }

     public Collection<Piece> getWhitePieces() {
          return this.whitePieces;
     }

     private Collection<Move> calculateLegalMoves(final Collection<Piece> pieces) {

          final List<Move> legalMoves = new ArrayList<>();

          for (final Piece piece : pieces) {
               legalMoves.addAll(piece.calculateLegalMoves(this));
          }

          return ImmutableList.copyOf(legalMoves);
     }

     private static Collection<Piece> calculateActivePieces(final List<Tile> gameBoard,
                                                          final Alliance alliance) {
          final List<Piece> activePieces = new ArrayList<>();

          for (final Tile tile : gameBoard) {
               if (tile.isOccupied()) {
                    final Piece piece = tile.getPiece();
                    if (piece.getPieceAlliance() == alliance) {
                         activePieces.add(piece);
                    }
               }
          }

          return ImmutableList.copyOf(activePieces);
     }

     public Tile getTile(final int tileCoordinate) {
          return gameBoard.get(tileCoordinate);
     }

     private static List<Tile> createGameBoard(final Builder builder) {
          final Tile[] tile = new Tile[BoardUtils.NUM_TILES];
          for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
               tile[i] = Tile.createTile(i, builder.boardConfig.get(i));
          }
          return ImmutableList.copyOf(tile);
     }

     public static Board createStandardBoard() {
          final Builder builder = new Builder();
          // Black's layout
          builder.setPiece(new Rook(0, Alliance.BLACK));
          builder.setPiece(new Knight(1, Alliance.BLACK));
          builder.setPiece(new Bishop(2, Alliance.BLACK));
          builder.setPiece(new Queen(3, Alliance.BLACK));
          builder.setPiece(new King(4, Alliance.BLACK));
          builder.setPiece(new Bishop(5, Alliance.BLACK));
          builder.setPiece(new Knight(6, Alliance.BLACK));
          builder.setPiece(new Rook(7, Alliance.BLACK));
          builder.setPiece(new Pawn(8, Alliance.BLACK));
          builder.setPiece(new Pawn(9, Alliance.BLACK));
          builder.setPiece(new Pawn(10, Alliance.BLACK));
          builder.setPiece(new Pawn(11, Alliance.BLACK));
          builder.setPiece(new Pawn(12, Alliance.BLACK));
          builder.setPiece(new Pawn(13, Alliance.BLACK));
          builder.setPiece(new Pawn(14, Alliance.BLACK));
          builder.setPiece(new Pawn(15, Alliance.BLACK));
          // White's layout
          builder.setPiece(new Pawn(48, Alliance.WHITE));
          builder.setPiece(new Pawn(49, Alliance.WHITE));
          builder.setPiece(new Pawn(50, Alliance.WHITE));
          builder.setPiece(new Pawn(51, Alliance.WHITE));
          builder.setPiece(new Pawn(52, Alliance.WHITE));
          builder.setPiece(new Pawn(53, Alliance.WHITE));
          builder.setPiece(new Pawn(54, Alliance.WHITE));
          builder.setPiece(new Pawn(55, Alliance.WHITE));
          builder.setPiece(new Rook(56, Alliance.WHITE));
          builder.setPiece(new Knight(57, Alliance.WHITE));
          builder.setPiece(new Bishop(58, Alliance.WHITE));
          builder.setPiece(new Queen(59, Alliance.WHITE));
          builder.setPiece(new King(60, Alliance.WHITE));
          builder.setPiece(new Bishop(61, Alliance.WHITE));
          builder.setPiece(new Knight(62, Alliance.WHITE));
          builder.setPiece(new Rook(63, Alliance.WHITE));
          // White to move
          builder.setMoveMaker(Alliance.WHITE);

          return builder.build();
     }

     public Iterable<Move> getAllLegalMoves() {
          return Iterables.unmodifiableIterable(Iterables.concat(this.whitePlayer.getLegalMoves(), this.blackPlayer.getLegalMoves()));
     }

     public static class Builder {
          Map<Integer, Piece> boardConfig;
          Alliance nextMoveMaker;
          Pawn enPassantPawn;

          public Builder() {
               this.boardConfig = new HashMap<>();
          }

          public Builder setPiece(final Piece piece) {
               this.boardConfig.put(piece.getPiecePosition(), piece);
               return this;
          }

          public Builder setMoveMaker(final Alliance alliance) {
               this.nextMoveMaker = nextMoveMaker;
               return this;
          }

          public Board build() {
               return new Board(this);
          }

          public void setEnPassantPawn(Pawn enPassantPawn) {
               this.enPassantPawn = enPassantPawn;
          }
     }


}


/*              Black Side
|***************************************|
|----|----|----|----|----|----|----|----|
|  0 | -> |    |    |    |    |    |    |
|----|----|----|----|----|----|----|----|
|    |    |    |    |    |    |    |    |
|----|----|----|----|----|----|----|----|
|    |    |    |    |    |    |    |    |
|----|----|----|----|----|----|----|----|
|    |    |    |    |    |    |    |    |
|----|----|----|----|----|----|----|----|
|    |    |    |    |    |    |    |    |
|----|----|----|----|----|----|----|----|
|    |    |    |    |    |    |    |    |
|----|----|----|----|----|----|----|----|
|    |    |    |    |    |    |    |    |
|----|----|----|----|----|----|----|----|
|    |    |    |    |    |    | -> | 63 |
|----|----|----|----|----|----|----|----|
|***************************************|
                White Side             */