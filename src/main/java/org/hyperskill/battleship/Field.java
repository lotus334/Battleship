package org.hyperskill.battleship;

import java.util.*;

public class Field {
    private static char[][] field = new char[10][10];
    Set<List<Integer>> closed = new HashSet<>();

    Field() {
        for (char[] row : field) {
            for (int j = 0; j < row.length; j++) {
                row[j] = '~';
            }
        }
    }

    void printField() {
        System.out.println("\n  1 2 3 4 5 6 7 8 9 10");
        char rowLetter = 'A';
        for (char[] row : field) {
            System.out.print(rowLetter++ + " ");
            for (char el : row) {
                System.out.print(el + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    void setPoint(int x, int y, char ch) {
        field[x][y] = ch;
    }
}
