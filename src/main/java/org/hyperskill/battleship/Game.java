package org.hyperskill.battleship;

import java.util.Scanner;

public class Game {
    public void start() {
        Scanner scanner = new Scanner(System.in);
        Battlefield battlefield = new Battlefield();
        System.out.println("Player 1, place your ships on the game friendField\n");
        battlefield.initField(battlefield.fieldFirst);
        System.out.println("Press Enter and pass the move to another player\n");
        scanner.nextLine();
        System.out.println("Player 2, place your ships to the game friendField");
        battlefield.initField(battlefield.fieldSecond);
        System.out.println("Press Enter and pass the move to another player\n");
        scanner.nextLine();
        boolean isFirst = true;
        int player = 0;
        char[][] friendField;
        char[][] enemyField;
        while (!battlefield.isFinished) {
            player = isFirst ? 1 : 2;
            friendField = isFirst ? battlefield.fieldFirst : battlefield.fieldSecond;
            enemyField = isFirst ? battlefield.fieldSecond : battlefield.fieldFirst;
            System.out.println(battlefield.toFogOfWar(enemyField));
            System.out.println("---------------------\n");
            System.out.println(battlefield.toOpenField(friendField));
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
