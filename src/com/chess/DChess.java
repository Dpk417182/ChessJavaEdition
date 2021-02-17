package com.chess;

import com.chess.board.Board;

public class DChess {

    public static void main(String[] args) {

        Board board = Board.createStandardBoard();

        System.out.println(board);
    }
}
