package gametheory.snowball.student2022;

import gametheory.snowball.Player;

import java.util.ArrayList;


/**
 * Class for simulating the game between two players.
 */
public class PlayerTestSystem {
    ArrayList<Integer> firstPlayerOpponentShots;
    ArrayList<Integer> secondPlayerOpponentShots;
    ArrayList<Integer> firstPlayerLavaShots;
    ArrayList<Integer> secondPlayerLavaShots;
    ArrayList<Integer> firstPlayerBallsRemaining;
    ArrayList<Integer> secondPlayerBallsRemaining;

    public PlayerTestSystem() {
        this.firstPlayerOpponentShots = new ArrayList<>();
        this.secondPlayerOpponentShots = new ArrayList<>();
        this.firstPlayerLavaShots = new ArrayList<>();
        this.secondPlayerLavaShots = new ArrayList<>();
        this.firstPlayerBallsRemaining = new ArrayList<>();
        this.secondPlayerBallsRemaining = new ArrayList<>();
    }

    /**
     * Function for game simulation.
     * @param first - first player with his strategy
     * @param second - second player with his strategy
     * @param rounds - number of rounds in the game
     * @return - returns the string which tells the result
     *           of the simulated game (first win, second win or draw)
     */
    public String GameSimulation(Player first, Player second, int rounds) {
        int firstPlayerLastShotOpponent = 0;
        int secondPlayerLastShotOpponent = 0;
        int firstLavaShot = 0;
        int secondLavaShot = 0;

        int firstPlayerBallsRemaining = 100;
        int secondPlayerBallsRemaining = 100;

        int firstPlayerMinutes = 0;
        int secondPlayerMinutes = 0;



        for (int round = 1; round <= rounds; round++) {
            if(round != 1) {
                firstPlayerBallsRemaining++;
                secondPlayerBallsRemaining++;
            }

            int firstPlayerCurrShotOpponent = first.shootToOpponentField(
                    secondPlayerLastShotOpponent,
                    firstPlayerBallsRemaining,
                    firstPlayerMinutes);

            firstLavaShot = first.shootToHotField(
                    secondPlayerLastShotOpponent,
                    firstPlayerBallsRemaining,
                    firstPlayerMinutes);

            int secondPlayerCurrShotOpponent = second.shootToOpponentField(
                    firstPlayerLastShotOpponent,
                    secondPlayerBallsRemaining,
                    secondPlayerMinutes);

            secondLavaShot = second.shootToHotField(
                    firstPlayerLastShotOpponent,
                    secondPlayerBallsRemaining,
                    secondPlayerMinutes);

            firstPlayerBallsRemaining += secondPlayerCurrShotOpponent;
            secondPlayerBallsRemaining += firstPlayerCurrShotOpponent;

            firstPlayerBallsRemaining -= firstLavaShot;
            secondPlayerBallsRemaining -= secondLavaShot;
            firstPlayerBallsRemaining -= firstPlayerCurrShotOpponent;
            secondPlayerBallsRemaining -= secondPlayerCurrShotOpponent;

            this.firstPlayerOpponentShots.add(firstPlayerCurrShotOpponent);
            this.secondPlayerOpponentShots.add(secondPlayerCurrShotOpponent);
            this.firstPlayerLavaShots.add(firstLavaShot);
            this.secondPlayerLavaShots.add(secondLavaShot);

            // check if there is an unexpected behaviour of the player
            if (firstPlayerBallsRemaining < 0 || secondPlayerBallsRemaining < 0) {
                System.out.println("----------------------------------------");
                System.out.println("First player snowballs: " + firstPlayerBallsRemaining);
                System.out.println("Second player snowballs: " + secondPlayerBallsRemaining);
                return "Error: negative snowball number";
            }

            System.out.println("Round " + round + " first player shot " + firstPlayerCurrShotOpponent +
                    " | second player shot " + secondPlayerCurrShotOpponent);

            System.out.println("First player lava shot " + firstLavaShot + "| second player lava shot " + secondLavaShot);

            System.out.println("First player minutes passed " + firstPlayerMinutes +
                    " | second player minutes passed " + secondPlayerMinutes);

            System.out.println("First player snowballs " + firstPlayerBallsRemaining
                    + " | second player snowballs " + secondPlayerBallsRemaining);
            System.out.println("----------------");

            firstPlayerLastShotOpponent = firstPlayerCurrShotOpponent;
            secondPlayerLastShotOpponent = secondPlayerCurrShotOpponent;

            if (firstPlayerCurrShotOpponent == 0 && firstLavaShot == 0) {
                firstPlayerMinutes++;
            } else {
                firstPlayerMinutes = 1;
            }

            if (secondPlayerCurrShotOpponent == 0 && secondLavaShot == 0) {
                secondPlayerMinutes++;
            } else {
                secondPlayerMinutes = 1;
            }

        }
        this.firstPlayerBallsRemaining.add(firstPlayerBallsRemaining);
        this.secondPlayerBallsRemaining.add(secondPlayerBallsRemaining);

        if (firstPlayerBallsRemaining < secondPlayerBallsRemaining) {
            return "first player won";
        } else if (firstPlayerBallsRemaining > secondPlayerBallsRemaining) {
            return "second player won";
        } else {
            return "Draw";
        }

    }


    public static void main(String[] args) {
        OpponentAgreementPlayer firstPlayer = new OpponentAgreementPlayer();
        NikitaSergeev secondPlayer = new NikitaSergeev();
        PlayerTestSystem testSystem = new PlayerTestSystem();
        System.out.println(testSystem.GameSimulation(firstPlayer, secondPlayer, 60));
    }
}
