package org.hyperskill.battleship;

import org.hyperskill.battleship.ships.*;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Class of the playing field.
 */
public class Logic {
    private int airCraftSize = 5;
    private int battleshipSize = 4;
    private int submarineSize = 3;
    private int cruiserSize = 3;
    private int destroyerSize = 2;
    private boolean isFinished = false;

    /**
     * Fill the playing battlefield with ships
     * Ask the user for coordinates and if they are suitable, pass them to the ship objects.
     */
    public void initField(Battlefield battlefield) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(battlefield.toOpenField());
        battlefield.setShips(new Ship[5]);
        battlefield.getShips()[0] = new AircraftCarrier(airCraftSize, "Aircraft Carrier");
        battlefield.getShips()[1] = new Battleship(battleshipSize, "Battleship");
        battlefield.getShips()[2] = new Submarine(submarineSize, "Submarine");
        battlefield.getShips()[3] = new Cruiser(cruiserSize, "Cruiser");
        battlefield.getShips()[4] = new Destroyer(destroyerSize, "Destroyer");
        for (Ship ship : battlefield.getShips()) {
            System.out.printf("Enter the coordinates of the %s (%d cells):\n",
                    ship.getName(),
                    ship.getSize());
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
                    if (putShipOnField(
                            rowBegin,
                            columnBegin,
                            rowEnd,
                            columnEnd,
                            ship,
                            battlefield)) {
                        System.out.println(battlefield.toOpenField());
                        break;
                    }
                }
            }
        }
    }

    /**
     * Place the ship on the playing battlefield, if it does not interfere with other ships.
     * @param rowBeginPar - int, coordinate of the beginning of the ship.
     * @param columnBeginPar - int, coordinate of the beginning of the ship.
     * @param rowEndPar - int, coordinate of the end of the ship.
     * @param columnEndPar - int, coordinate of the end of the ship.
     * @param shipPar - Ship, object that is placed on the battlefield.
     * @return - boolean, true if placing is success.
     */
    public boolean putShipOnField(int rowBeginPar,
                                  int columnBeginPar,
                                  int rowEndPar,
                                  int columnEndPar,
                                  Ship shipPar,
                                  Battlefield battlefield) {
        for (Ship ship : battlefield.getShips()) {
            if (ship != shipPar && ship.isPlaces()) {
                for (int i = rowBeginPar - 1; i <= rowEndPar + 1; i++) {
                    for (int j = columnBeginPar - 1; j <= columnEndPar + 1; j++) {
                        if ((i == ship.getRowBegin() && j == ship.getColumnBegin())
                                || (i == ship.getRowEnd() && j == ship.getColumnEnd())) {
                            System.out.println("Error! You placed it too close to another one."
                                    + " Try again:\n");
                            return false;
                        }
                    }
                }
                if (ship.getSize() == 5) {
                    int middleX = (ship.getRowBegin() + ship.getRowEnd()) / 2;
                    int middleY = (ship.getColumnBegin() + ship.getColumnEnd()) / 2;
                    for (int i = rowBeginPar - 1; i <= rowEndPar + 1; i++) {
                        for (int j = columnBeginPar - 1; j <= columnEndPar + 1; j++) {
                            if (i == middleX && j == middleY) {
                                System.out.println("Error! You placed it too close to another one."
                                        + " Try again:\n");
                                return false;
                            }
                        }
                    }
                }
            }
        }

        if (rowBeginPar == rowEndPar) {
            for (int i = columnBeginPar; i <= columnEndPar; i++) {
                battlefield.getField()[rowBeginPar][i - 1] = shipPar.getCells()[i - columnBeginPar];
            }
        } else {
            for (int i = rowBeginPar; i <= rowEndPar; i++) {
                battlefield.getField()[i][columnBeginPar - 1] = shipPar.getCells()[i - rowBeginPar];
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
                    && battlefield.getField()[row][column] != 'X'
                    && battlefield.getField()[row][column] != 'M') {
                break;
            }
            System.out.println("Error! Wrong ship location! Try again:\n");
            coordinate = scanner.nextLine();
            row = coordinate.charAt(0) - 65;
            column = Integer.parseInt(coordinate.substring(1)) - 1;

        }
        if (battlefield.getField()[row][column] == 'O') {
            battlefield.getField()[row][column] = 'T';
            for (Ship ship : battlefield.getShips()) {
                if (ship.getRowBegin() == ship.getRowEnd()) {
                    for (int i = ship.getColumnBegin(); i <= ship.getColumnEnd(); i++) {
                        if (battlefield.getField()[ship.getRowBegin()][i - 1] == 'T') {
                            ship.getCells()[i - ship.getColumnBegin()] = 'X';
                            battlefield.getField()[row][column] = 'X';
                            targetShip = ship;
                            break;
                        }
                    }
                } else {
                    for (int i = ship.getRowBegin(); i <= ship.getRowEnd(); i++) {
                        if (battlefield.getField()[i][ship.getColumnBegin() - 1] == 'T') {
                            ship.getCells()[i - ship.getRowBegin()] = 'X';
                            battlefield.getField()[row][column] = 'X';
                            targetShip = ship;
                            break;
                        }
                    }
                }
            }
            isTheShipSunk(targetShip, battlefield.getShips());
            result = true;
        } else if (battlefield.getField()[row][column] == '~') {
            battlefield.getField()[row][column] = 'M';
            result = false;
        }
        return result;
    }

    private boolean isTheShipSunk(Ship ship, Ship[] ships) {
        System.out.println(Arrays.toString(ship.getCells()));
        for (char ch : ship.getCells()) {
            if (ch == ship.getShipSymbol()) {
                System.out.println("You hit a ship!\n");
                return false;
            }
        }
        if (isAllShipsSunk(ships)) {
            System.out.println("You sank the last ship. You won. Congratulations!\n");
            setFinished(true);
        } else {
            System.out.println("You sank a ship!\n");
        }
        return true;
    }

    private boolean isAllShipsSunk(Ship[] ships) {
        for (Ship ship : ships) {
            for (char ch : ship.getCells()) {
                if (ch == ship.getShipSymbol()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}
