package org.hyperskill.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Stream;

public class Logic {
    private final Field field = new Field();

    void createShips() {
        var greeting = new String[] {
                "Enter the coordinates of the Aircraft Carrier (5 cells):\n",
                "Enter the coordinates of the Battleship (4 cells):\n",
                "Enter the coordinates of the Submarine (3 cells):\n",
                "Enter the coordinates of the Cruiser (3 cells):\n",
                "Enter the coordinates of the Destroyer (2 cells):\n"
        };
        var shipSizes = new int[] {5, 4, 3, 3, 2};
        field.printField();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            int counter = 0;
            while (counter < greeting.length){
                System.out.println(greeting[counter]);
                String[] coordinates = reader
                        .readLine()
                        .trim()
                        .split("\\s");
                if (coordinates.length < 2) {
                    System.out.println("\nError! Wrong ship location! Try again:\n");
                    continue;
                }
                if (putTheShip(coordinates, shipSizes[counter])) {
                    field.printField();
                    counter++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean putTheShip(String[] coordinates, int shipSize) {
        var first = coordinates[0];
        var second = coordinates[1];
        int x1 = first.charAt(0) < second.charAt(0) ? first.charAt(0) - 65 : second.charAt(0) - 65;
        int x2 = first.charAt(0) > second.charAt(0) ? first.charAt(0) - 65 : second.charAt(0) - 65;
        int y1 = Math.min(Integer.parseInt(first.replaceAll("[a-zA-Z]", "")) - 1,
                Integer.parseInt(second.replaceAll("[a-zA-Z]", "")) - 1);
        int y2 = Math.max(Integer.parseInt(first.replaceAll("[a-zA-Z]", "")) - 1,
                Integer.parseInt(second.replaceAll("[a-zA-Z]", "")) - 1);
        var result = checkInputCells(x1, y1, x2, y2, shipSize);
        if (result) {
            boolean vertically = x1 == x2;
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    field.closed.add(List.of(x1 + i, y1 + j));
                    field.closed.add(List.of(x2 + i, y2 + j));
                }
            }
            if (vertically) {
                for (int i = 0; i < shipSize; i++) {
                    field.setPoint(x1, y1 + i, 'O');
                    field.closed.add(List.of(x1, y1 + i));
                    field.closed.add(List.of(x1 + 1, y1 + i));
                    field.closed.add(List.of(x1 - 1, y1 + i));
                }
            } else {
                for (int i = 0; i < shipSize; i++) {
                    field.setPoint(x1 + i, y1, 'O');
                    field.closed.add(List.of(x1 + i, y1));
                    field.closed.add(List.of(x1 + i, y1 + 1));
                    field.closed.add(List.of(x1 + i, y1 - 1));
                }
            }
        }
        return result;
    }

    private boolean checkInputCells(int x1, int y1, int x2, int y2, int shipSize) {
        boolean result = true;
        int shipSizeByInput = x1 == x2 ? Math.abs(y1 - y2) + 1 : Math.abs(x1 - x2) + 1;
        if (Stream.of(x1, y1, x2, y2).anyMatch(integer -> integer < 0 || integer > 9)
                || (x1 != x2 && y1 != y2)) {
            System.out.println("\nError! Wrong ship location! Try again:");
            result = false;
        } else if (shipSize != shipSizeByInput) {
            System.out.println("\nError! Wrong length of the Submarine! Try again:");
            result = false;
        } else if (field.closed.contains(List.of(x1, y1))
                || field.closed.contains(List.of(x2, y2))) {
            System.out.println(List.of(x1, y1));
            System.out.println("\nError! You placed it too close to another one. Try again:");
            result = false;
        }
        return result;
    }
}
