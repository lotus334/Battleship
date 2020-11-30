package org.hyperskill.battleship;

import org.hyperskill.battleship.ships.Ship;

public class Field {
    final int SIZE = 10;
    final int AIRCRAFT_SIZE = 5;
    final int BATTLESHIP_SIZE = 4;
    final int SUBMARINE_SIZE = 3;
    final int CRUISER_SIZE = 3;
    final int DESTROYER_SIZE = 2;
    final char EMPTY = '~';
    char[][] fieldFirst = new char[SIZE][SIZE];
    char[][] fieldSecond = new char[SIZE][SIZE];
    Ship[] shipsFirst;
    Ship[] shipsSecond;
    boolean isFinished = false;

    /**
     * Constructor of the playing field.
     * Creating an array of fields with the specified size.
     */
    public Field() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
//                field[i][j] = EMPTY;
                fieldFirst[i][j] = EMPTY;
                fieldSecond[i][j] = EMPTY;
            }
        }
    }
}
