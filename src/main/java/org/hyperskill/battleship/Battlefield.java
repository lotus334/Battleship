package org.hyperskill.battleship;

import org.hyperskill.battleship.ships.Ship;

public class Battlefield {
    private int size = 10;
    private char empty = '~';
    private char[][] field = new char[size][size];
    private Ship[] ships;

    /**
     * Constructor of the playing field.
     * Creating an array of fields with the specified size.
     */
    public Battlefield() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                field[i][j] = empty;
            }
        }
    }

    /**
     * @return - String, the field with fog of war.
     */
    public String toFogOfWar() {
        StringBuilder result = new StringBuilder("  1 2 3 4 5 6 7 8 9 10\n");
        for (int i = 0; i < size; i++) {
            result.append(Character.toChars(65 + i));
            for (int j = 0; j < size; j++) {
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

    /**
     * @return - String, the field without fog of war.
     */
    public String toOpenField() {
        StringBuilder result = new StringBuilder("  1 2 3 4 5 6 7 8 9 10\n");
        for (int i = 0; i < size; i++) {
            result.append(Character.toChars(65 + i));
            for (int j = 0; j < size; j++) {
                result.append(" ").append(field[i][j]);
            }
            result.append("\n");
        }
        return result.toString();
    }

    public char[][] getField() {
        return field;
    }

    public void setField(char[][] field) {
        this.field = field;
    }

    public Ship[] getShips() {
        return ships;
    }

    public void setShips(Ship[] ships) {
        this.ships = ships;
    }

    public int getSize() {
        return size;
    }

    public char getEmpty() {
        return empty;
    }
}
