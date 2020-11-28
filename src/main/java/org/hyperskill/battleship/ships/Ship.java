package org.hyperskill.battleship.ships;

import java.util.Arrays;

/**
 * Abstract ship class. Use it to create as many ships as you want. With individual parameters.
 */
public abstract class Ship {
    final char SHIP_SYMBOL = 'O';
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
     * @param _size - int, size of ship on game field.
     * @param _name - String, name of ship.
     */
    public Ship(int _size, String _name) {
        size = _size;
        name = _name;
        cells = new char[_size];
        Arrays.fill(cells, SHIP_SYMBOL);
    }

    /**
     * Check the given coordinates of the ship's location on the map,
     * If the coordinates do not contradict, we save them in the fields of the class instance.
     * @param _rowBegin - int, coordinates
     * @param _columnBegin - int, coordinates
     * @param _rowEnd - int, coordinates
     * @param _columnEnd - int, coordinates
     * @return boolean, true if the coordinates meet condition.
     */
    public boolean setCoordinates(int _rowBegin, int _columnBegin, int _rowEnd, int _columnEnd) {
        if (_rowBegin == _rowEnd || _columnBegin == _columnEnd) {
            if (_rowEnd - _rowBegin != size - 1 && _columnEnd - _columnBegin != size - 1) {
                System.out.printf("Error! Wrong length of the %s! Try again:\n", name);
                return false;
            }
        } else {
            System.out.println("Error! Wrong ship location! Try again:");
            return false;
        }
        rowBegin = _rowBegin;
        columnBegin = _columnBegin;
        rowEnd = _rowEnd;
        columnEnd = _columnEnd;
        isPlaces = true;

        return true;
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
