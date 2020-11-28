package org.hyperskill.battleship;

import org.hyperskill.battleship.ships.*;

import java.util.Scanner;

/**
 * Class of the playing field.
 */
public class Battlefield {
    final int SIZE = 10;
    final int AIRCRAFT_SIZE = 5;
    final int BATTLESHIP_SIZE = 4;
    final int SUBMARINE_SIZE = 3;
    final int CRUISER_SIZE = 3;
    final int DESTROYER_SIZE = 2;
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

    /**
     * Fill the playing field with ships
     * Ask the user for coordinates and if they are suitable, pass them to the ship objects.
     */
    public void initField() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(this.toString());
        ships = new Ship[5];
        ships[0] = new AircraftCarrier(AIRCRAFT_SIZE, "Aircraft Carrier");
        ships[1] = new Battleship(BATTLESHIP_SIZE, "Battleship");
        ships[2] = new Submarine(SUBMARINE_SIZE, "Submarine");
        ships[3] = new Cruiser(CRUISER_SIZE, "Cruiser");
        ships[4] = new Destroyer(DESTROYER_SIZE, "Destroyer");
        for (Ship ship : ships) {
            System.out.printf("Enter the coordinates of the %s (%d cells):\n", ship.getName(), ship.getSize());
            while (true) {
                String[] coordinates = scanner.nextLine().split(" ");
                if (coordinates.length < 2) {
                    System.out.println("Error! Wrong ship location! Try again:");
                    break;
                }
                int rowBegin = coordinates[0].charAt(0) - 65;
                int columnBegin = Integer.parseInt(coordinates[0].substring(1));
                int rowEnd = coordinates[1].charAt(0) - 65;
                int columnEnd = Integer.parseInt(coordinates[1].substring(1));
                if (rowBegin > rowEnd) {
                    int tmp = rowEnd;
                    rowEnd = rowBegin;
                    rowBegin = tmp;
                }
                if (columnBegin > columnEnd) {
                    int tmp = columnEnd;
                    columnEnd = columnBegin;
                    columnBegin = tmp;
                }
                if (ship.setCoordinates(rowBegin, columnBegin, rowEnd, columnEnd)) {
                    if (putShipOnField(rowBegin, columnBegin, rowEnd, columnEnd, ship)) {
                        System.out.println(this.toString());
                        break;
                    }
                }
            }
        }
    }

    /**
     * Place the ship on the playing field, if it does not interfere with other ships.
     * @param _rowBegin - int, coordinate of the beginning of the ship.
     * @param _columnBegin - int, coordinate of the beginning of the ship.
     * @param _rowEnd - int, coordinate of the end of the ship.
     * @param _columnEnd - int, coordinate of the end of the ship.
     * @param _ship - Ship, object that is placed on the field.
     * @return - boolean, true if placing is success.
     */
    public boolean putShipOnField(int _rowBegin, int _columnBegin, int _rowEnd, int _columnEnd, Ship _ship) {
        for (Ship ship : ships) {
            //If the ship being compared is not an installable ship and the ship isn't on the field yet
            if (ship != _ship && ship.isPlaces()) {
                //Find out if there are any coordinates of other ships near the one being placed
                for (int i = _rowBegin - 1; i <= _rowEnd + 1; i++) {
                    for (int j = _columnBegin - 1; j <= _columnEnd + 1; j++) {
                        if ((i == ship.getRowBegin() && j == ship.getColumnBegin())
                                || (i == ship.getRowEnd() && j == ship.getColumnEnd())) {
                            System.out.println("Error! You placed it too close to another one. Try again:");
                            return false;
                        }
                    }
                }
                if (ship.getSize() == 5) {
                    int middleX = (ship.getRowBegin() + ship.getRowEnd()) / 2;
                    int middleY = (ship.getColumnBegin() + ship.getColumnEnd()) / 2;
                    for (int i = _rowBegin - 1; i <= _rowEnd + 1; i++) {
                        for (int j = _columnBegin - 1; j <= _columnEnd + 1; j++) {
                            if (i == middleX && j == middleY) {
                                System.out.println("Error! You placed it too close to another one. Try again:");
                                return false;
                            }
                        }
                    }
                }
            }
        }

        //Put the ship symbols in the game field according to its coordinates
        if (_rowBegin == _rowEnd) {
            for (int i = _columnBegin; i <= _columnEnd; i++) {
                field[_rowBegin][i - 1] = _ship.getCells()[i - _columnBegin];
            }
        } else {
            for (int i = _rowBegin; i <= _rowEnd; i++) {
                field[i][_columnBegin - 1] = _ship.getCells()[i - _rowBegin];
            }
        }
        return true;
    }

    @Override
    public String toString() {
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
