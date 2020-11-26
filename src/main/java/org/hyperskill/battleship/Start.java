package org.hyperskill.battleship;

public class Start {
    private static Field field;
    private static Logic logic;

    public Start() {
        field = new Field();
        logic = new Logic(field);
    }

    public void startGame() {
        logic.createShips();
    }
}
