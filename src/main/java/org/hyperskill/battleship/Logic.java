package org.hyperskill.battleship;

import org.hyperskill.battleship.ships.*;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Class of the playing field.
 */
public class Logic {
    final int AIRCRAFT_SIZE = 5;
    final int BATTLESHIP_SIZE = 4;
    final int SUBMARINE_SIZE = 3;
    final int CRUISER_SIZE = 3;
    final int DESTROYER_SIZE = 2;
    boolean isFinished = false;

    /**
     * Fill the playing battlefield with ships
     * Ask the user for coordinates and if they are suitable, pass them to the ship objects.
     */
    public void initField(Battlefield battlefield) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(battlefield.toOpenField());
        battlefield.ships = new Ship[5];
        battlefield.ships[0] = new AircraftCarrier(AIRCRAFT_SIZE, "Aircraft Carrier");
        battlefield.ships[1] = new Battleship(BATTLESHIP_SIZE, "Battleship");
        battlefield.ships[2] = new Submarine(SUBMARINE_SIZE, "Submarine");
        battlefield.ships[3] = new Cruiser(CRUISER_SIZE, "Cruiser");
        battlefield.ships[4] = new Destroyer(DESTROYER_SIZE, "Destroyer");
        for (Ship ship : battlefield.ships) {
            System.out.printf("Enter the coordinates of the %s (%d cells):\n", ship.getName(), ship.getSize());
            while (true) {
                String[] coordinates = scanner.nextLine().split(" ");
                if (coordinates.length < 2) {
                    System.out.println("Error! Wrong ship location! Try again:\n");
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
                if (rowEnd > 10 || columnEnd > 10
                        || rowBegin < 0 || columnBegin < 0) {
                    System.out.println("Error! Wrong ship location! Try again:\n");
                    continue;
                }
                if (ship.setCoordinates(rowBegin, columnBegin, rowEnd, columnEnd)) {
                    if (putShipOnField(rowBegin, columnBegin, rowEnd, columnEnd, ship, battlefield)) {
                        System.out.println(battlefield.toOpenField());
                        break;
                    }
                }
            }
        }
    }

    /**
     * Place the ship on the playing battlefield, if it does not interfere with other ships.
     * @param _rowBegin - int, coordinate of the beginning of the ship.
     * @param _columnBegin - int, coordinate of the beginning of the ship.
     * @param _rowEnd - int, coordinate of the end of the ship.
     * @param _columnEnd - int, coordinate of the end of the ship.
     * @param _ship - Ship, object that is placed on the battlefield.
     * @return - boolean, true if placing is success.
     */
    public boolean putShipOnField(int _rowBegin, int _columnBegin, int _rowEnd, int _columnEnd, Ship _ship, Battlefield battlefield) {
        for (Ship ship : battlefield.ships) {
            if (ship != _ship && ship.isPlaces()) {
                for (int i = _rowBegin - 1; i <= _rowEnd + 1; i++) {
                    for (int j = _columnBegin - 1; j <= _columnEnd + 1; j++) {
                        if ((i == ship.getRowBegin() && j == ship.getColumnBegin())
                                || (i == ship.getRowEnd() && j == ship.getColumnEnd())) {
                            System.out.println("Error! You placed it too close to another one. Try again:\n");
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
                                System.out.println("Error! You placed it too close to another one. Try again:\n");
                                return false;
                            }
                        }
                    }
                }
            }
        }

        if (_rowBegin == _rowEnd) {
            for (int i = _columnBegin; i <= _columnEnd; i++) {
                battlefield.field[_rowBegin][i - 1] = _ship.getCells()[i - _columnBegin];
            }
        } else {
            for (int i = _rowBegin; i <= _rowEnd; i++) {
                battlefield.field[i][_columnBegin - 1] = _ship.getCells()[i - _rowBegin];
            }
        }
        return true;
    }

    /**
     *
     */
    public boolean takeAShot(Battlefield battlefield) {
        boolean result = false;
        Scanner scanner = new Scanner(System.in);
        String coordinate = scanner.nextLine();
        int row = coordinate.charAt(0) - 65;
        int column = Integer.parseInt(coordinate.substring(1)) - 1;
        Ship targetShip = null;
        while (true) {
            if (row <= 9 && column <= 9
                    && row >= 0 && column >= 0
                    && battlefield.field[row][column] != 'X'
                    && battlefield.field[row][column] != 'M') {
                break;
            }
            System.out.println("Error! Wrong ship location! Try again:\n");
            coordinate = scanner.nextLine();
            row = coordinate.charAt(0) - 65;
            column = Integer.parseInt(coordinate.substring(1)) - 1;

        }
        if (battlefield.field[row][column] == 'O') {
            battlefield.field[row][column] = 'T';
            for (Ship ship : battlefield.ships) {
                if (ship.getRowBegin() == ship.getRowEnd()) {
                    for (int i = ship.getColumnBegin(); i <= ship.getColumnEnd(); i++) {
                        if (battlefield.field[ship.getRowBegin()][i - 1] == 'T' ) {
                            ship.getCells()[i - ship.getColumnBegin()] = 'X';
                            battlefield.field[row][column] = 'X';
                            targetShip = ship;
                            break;
                        }
                    }
                } else {
                    for (int i = ship.getRowBegin(); i <= ship.getRowEnd(); i++) {
                        if (battlefield.field[i][ship.getColumnBegin() - 1] == 'T') {
                            ship.getCells()[i - ship.getRowBegin()] = 'X';
                            battlefield.field[row][column] = 'X';
                            targetShip = ship;
                            break;
                        }
                    }
                }
            }
            isTheShipSunk(targetShip, battlefield.ships);
            result = true;
        } else if (battlefield.field[row][column] == '~') {
            battlefield.field[row][column] = 'M';
            result = false;
        }
        return result;
    }

    private boolean isTheShipSunk(Ship ship, Ship[] ships) {
        System.out.println(Arrays.toString(ship.getCells()));
        for (char ch : ship.getCells()) {
            if (ch == ship.getSHIP_SYMBOL()) {
                System.out.println("You hit a ship!\n");
                return false;
            }
        }
        if (isAllShipsSunk(ships)) {
            System.out.println("You sank the last ship. You won. Congratulations!\n");
            isFinished = true;
        } else {
            System.out.println("You sank a ship!\n");
        }
        return true;
    }

    private boolean isAllShipsSunk(Ship[] ships) {
        for (Ship ship : ships) {
            for (char ch : ship.getCells()) {
                if (ch == ship.getSHIP_SYMBOL()) {
                    return false;
                }
            }
        }
        return true;
    }
}
