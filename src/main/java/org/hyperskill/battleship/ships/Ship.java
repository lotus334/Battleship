package org.hyperskill.battleship.ships;

import java.util.Arrays;

/**
 * Abstract ship class. Use it to create as many ships as you want. With individual parameters.
 */
public abstract class Ship {
    private char shipSymbol = 'O';
    private int size;
    private String name;
    private char[] cells;
    private boolean isPlaces = false;
    private int rowBegin;
    private int rowEnd;
    private int columnBegin;
    private int columnEnd;

    /**
     * Designer. Creating a ship with the specified size and name.
     * @param size - int, size of ship on game field.
     * @param name - String, name of ship.
     */
    public Ship(int size, String name) {
        this.size = size;
        this.name = name;
        cells = new char[size];
        Arrays.fill(cells, shipSymbol);
    }

    /**
     * Check the given coordinates of the ship's location on the map,
     * If the coordinates do not contradict, we save them in the fields of the class instance.
     * @param rowBeginPar - int, coordinates
     * @param columnBeginPar - int, coordinates
     * @param rowEndPar - int, coordinates
     * @param columnEndPar - int, coordinates
     * @return boolean, true if the coordinates meet condition.
     */
    public boolean setCoordinates(int rowBeginPar,
                                  int columnBeginPar,
                                  int rowEndPar,
                                  int columnEndPar) {
        if (rowBeginPar == rowEndPar || columnBeginPar == columnEndPar) {
            if (rowEndPar - rowBeginPar != size - 1 && columnEndPar - columnBeginPar != size - 1) {
                System.out.printf("Error! Wrong length of the %s! Try again:\n", name);
                return false;
            }
        } else {
            System.out.println("Error! Wrong ship location! Try again:");
            return false;
        }
        rowBegin = rowBeginPar;
        columnBegin = columnBeginPar;
        rowEnd = rowEndPar;
        columnEnd = columnEndPar;
        isPlaces = true;

        return true;
    }

    public char getShipSymbol() {
        return shipSymbol;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public char[] getCells() {
        return cells;
    }

    public boolean isPlaces() {
        return isPlaces;
    }

    public int getRowBegin() {
        return rowBegin;
    }

    public int getRowEnd() {
        return rowEnd;
    }

    public int getColumnBegin() {
        return columnBegin;
    }

    public int getColumnEnd() {
        return columnEnd;
    }
}
