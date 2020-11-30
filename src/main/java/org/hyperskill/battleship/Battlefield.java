package org.hyperskill.battleship;

import org.hyperskill.battleship.ships.Ship;

public class Battlefield {
    final int SIZE = 10;
    final char EMPTY = '~';
    char[][] field = new char[SIZE][SIZE];
    Ship[] ships;

    /**
     * Constructor of the playing field.
     * Creating an array of fields with the specified size.
     */
    public Battlefield() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                field[i][j] = EMPTY;
            }
        }
    }

    public String toFogOfWar() {
        StringBuilder result = new StringBuilder("  1 2 3 4 5 6 7 8 9 10\n");
        for (int i = 0; i < SIZE; i++) {
            result.append(Character.toChars(65 + i));
            for (int j = 0; j < SIZE; j++) {
                if (field[i][j] == 'O') {
                    result.append(" ").append('~');
                } else {
                    result.append(" ").append(field[i][j]);
                }
            }
            result.append("\n");
        }
        return result.toString();
    }

    public String toOpenField() {
        StringBuilder result = new StringBuilder("  1 2 3 4 5 6 7 8 9 10\n");
        for (int i = 0; i < SIZE; i++) {
            result.append(Character.toChars(65 + i));
            for (int j = 0; j < SIZE; j++) {
                result.append(" ").append(field[i][j]);
            }
            result.append("\n");
        }
        return result.toString();
    }
}
