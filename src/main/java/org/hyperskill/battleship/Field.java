package org.hyperskill.battleship;

public class Field {
    private static char[][] field = new char[10][10];

    public Field() {
        for (char[] row : field) {
            for (int j = 0; j < row.length; j++) {
                row[j] = '~';
            }
        }
    }

    public void printField() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        char rowLetter = 'A';
        for (char[] row : field) {
            System.out.print(rowLetter++ + " ");
            for (char el : row) {
                System.out.print(el + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Field field = new Field();
        field.printField();
    }
}
