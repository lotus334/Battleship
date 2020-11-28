package org.hyperskill.battleship;

public class Start {
    private static Logic logic = new Logic();

    public void startGame() {
        logic.createShips();
    }
}
