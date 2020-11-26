package org.hyperskill.battleship;

import java.util.Arrays;
import java.util.Iterator;

public class Logic {
    private Field field;

    public Logic(Field field) {
        this.field = field;
    }

    public void createShips() {
        var greeting = new String[] {
                "Enter the coordinates of the Aircraft Carrier (5 cells):",
                "Enter the coordinates of the Battleship (4 cells):",
                "Enter the coordinates of the Submarine (3 cells):",
                "Enter the coordinates of the Cruiser (3 cells):",
                "Enter the coordinates of the Destroyer (2 cells):"
        };
        var iter = Arrays.stream(greeting).iterator();
        while (iter.hasNext()) {
        }
    }
}
