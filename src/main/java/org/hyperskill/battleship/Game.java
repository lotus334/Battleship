package org.hyperskill.battleship;

import java.util.Scanner;

public class Game {
    public void start() {
        Scanner scanner = new Scanner(System.in);
        Logic battlefield = new Logic();
        Battlefield First = new Battlefield();
        Battlefield Second = new Battlefield();
        System.out.println("Player 1, place your ships on the game friendField\n");
        battlefield.initField(First);
        System.out.println("Press Enter and pass the move to another player\n");
        scanner.nextLine();
        System.out.println("Player 2, place your ships to the game friendField");
        battlefield.initField(Second);
        System.out.println("Press Enter and pass the move to another player\n");
        scanner.nextLine();
        boolean isFirst = true;
        int player = 0;
        Battlefield friendField;
        Battlefield enemyField;
        while (!battlefield.isFinished) {
            player = isFirst ? 1 : 2;
            friendField = isFirst ? First : Second;
            enemyField = isFirst ? Second : First;
            System.out.println(enemyField.toFogOfWar());
            System.out.println("---------------------\n");
            System.out.println(friendField.toOpenField());
            System.out.printf("Player %d, it's your turn:\n", player);
            if (!battlefield.takeAShot(enemyField)) {
                System.out.println("You missed!");
            }
            System.out.println("Press Enter and pass the move to another player");
            scanner.nextLine();
            isFirst = !isFirst;
        }
    }

}
