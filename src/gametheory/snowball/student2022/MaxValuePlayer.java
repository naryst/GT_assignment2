package gametheory.snowball.student2022;

import gametheory.snowball.Player;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Class for the Player.
 * It implements the Player interface.
 * This player shoots the maximum number of snowballs
 * according to the strategy, calculated in the main function.
 * This player shoot snowballs only to the opponent's field.
 * Because it's the most valuable strategy
 */
class MaxValuePlayer implements Player {

    int snowballNumber;
    int minutesPassedAfterYourShot;
    int roundNumber;
    int shootToOpponentField;
    int shootToHotField;
    Integer[] shotRounds = {4, 8, 12, 16, 20, 24, 28, 32, 36, 40, 44, 48, 52, 56, 60};
    ArrayList<Integer> shotRoundsList = new ArrayList<Integer>(Arrays.asList(shotRounds));

    /**
     * Constructor for the gametheory.snowball.student2022.MaxValuePlayer class
     * It sets the initial values for the fields of the clas
     * This constructor just call the method reset() for the class
     */
    public MaxValuePlayer() {
        this.reset();
    }

    /**
     * This method is called to reset the agent before the match
     * with each player containing several rounds
     */
    @Override
    public void reset() {
        this.snowballNumber = 100;
        this.minutesPassedAfterYourShot = 0;
        this.roundNumber = 1;
        this.shootToOpponentField = -1;
        this.shootToHotField = -1;
        this.shotRounds = new Integer[]{4, 8, 12, 16, 20, 24, 28, 32, 36, 40, 44, 48, 52, 56, 60};
//        this.shotRounds = new Integer[]{5, 9, 13, 17, 21, 25, 29, 33, 37, 41, 45, 49, 53, 57, 60};
        this.shotRoundsList = new ArrayList<Integer>(Arrays.asList(shotRounds));

    }

    /**
     * This method returns the number of snowballs the player will shoot
     * to opponent’s field.
     * According to the strategy, calculated in the main function the player will shoot to the
     * opponent's field the maximum number of snowballs on rounds from shotRounds field.
     * If the Player have in his field less snowballs than he can shoot, he will shoot all of them.
     *
     * @param opponentLastShotToYourField this parameter useless in this method, because according to the strategy
     *                                    the player will shoot to the opponent's field the maximum number of snowballs
     *                                    every 4 rounds. No matter what the opponent shot to the player's field.
     * @param snowballNumber              this parameter shows the number of snowballs on the player's field.
     *                                    It's used to check if the player can shoot the maximum number of snowballs,
     *                                    or he can shoot all of remaining.
     * @param minutesPassedAfterYourShot  the number of minutes passed after
     *                                    your last shot to any field
     *                                    (0 – if this is the first shot)
     * @return the number of snowballs the player will shoot to opponent's field
     */
    @Override
    public int shootToOpponentField(int opponentLastShotToYourField, int snowballNumber, int minutesPassedAfterYourShot) {
        int shot;
        if (this.roundNumber == shotRoundsList.get(0)) {
            shot = Math.min(this.maxSnowballsPerMinute(minutesPassedAfterYourShot), snowballNumber);
            this.shotRoundsList.remove(0);
        } else {
            shot = 0;
        }

        this.snowballNumber -= shot;
        this.shootToOpponentField = shot;
        this.roundNumber++;
        return shot;
    }

    /**
     * This method returns the number of snowballs the player will shoot
     * to hot field. According to the strategy, calculated in the main function the player will
     * not shoot to the hot field. So this method will simply always return 0.
     *
     * @param opponentLastShotToYourField the last shot of the opponent to your
     *                                    field (0 – if this is the first shot)
     * @param snowballNumber              the number of snowballs on your field
     * @param minutesPassedAfterYourShot  the number of minutes passed after
     *                                    your last shot to any field
     *                                    (0 – if this is the first shot)
     * @return 0
     */
    @Override
    public int shootToHotField(int opponentLastShotToYourField, int snowballNumber, int minutesPassedAfterYourShot) {
        return 0;
    }

    @Override
    public String getEmail() {
        return "n.sergeev@innopolis.university";
    }
}

/**
 * Class for calculating the best strategy for the player
 * in the main function we perform next steps:
 * 1. Create an array of all possible values of the maxSnowballsPerMinute function
 * 2. With the dynamic programming algorithm, we calculate the maximum number of snowballs that can be shot
 * during the game (60 rounds)
 * 3. Simulate the game without opponent and see how many snowballs player will have in each round
 * (without opponent, shooting in his field)
 */
class Strategy {
    public static void main(String[] args) {
        int numOfRounds = 60;
        int numOfBalls = 100;

        MaxValuePlayer p = new MaxValuePlayer();

        // calculate the result of the given function for every number skipped of rounds(from 1 to 60)
        ArrayList<Integer> opponent_shots = new ArrayList<>();
        for (int i = 0; i < numOfRounds; i++) {
            int result = p.maxSnowballsPerMinute(i);
            opponent_shots.add(result);
        }

        System.out.println("Opponent shots: " + opponent_shots);

        ArrayList<Integer> dp = new ArrayList<>();
        ArrayList<Integer> fromPath = new ArrayList<>();
        for (int i = 0; i <= numOfRounds; i++) {
            dp.add(0);
        }

        // DP algorithm for constructing strategy
        // with max number of snowballs shot
        for (int i = 6; i <= numOfRounds; i++) {
            int max = 0;
            int from = -1;
            for (int j = 1; j <= i; j++) {
                int cur = dp.get(j) + opponent_shots.get(i - j);
                if (cur > max) {
                    max = cur;
                    from = j;
                }
            }
            dp.set(i, max);
            System.out.println("Round " + i + ": " + max + " from " + from);
            fromPath.add(from);
        }

        // Constructing the strategy
        // In other words, list of rounds when we should shoot
        ArrayList<Integer> path = new ArrayList<>();
        int cur = numOfRounds;
        while (cur >= 6) {
            path.add(cur);
            cur = fromPath.get(cur-6);
        }
        // reverse the path
        ArrayList<Integer> reversedPath = new ArrayList<>();
        for (int i = path.size() - 1; i >= 0; i--) {
            reversedPath.add(path.get(i));
        }
        reversedPath.remove(0);
        System.out.println("Path: " + reversedPath);


//        System.out.println("DP max_value: " + dp.get(numOfRounds));
//        System.out.println("DP: " + dp);


        // from the dp algorithm we get the most valuable strategy
        // With it, we can simulate the game without opponent
        // and see how many snowballs player will have in each round
        int testBalls = numOfBalls;
        int roundsMissed = 0;
        for (int i = 1; i <= numOfRounds; i++) {
            if (i == reversedPath.get(0)) {
                reversedPath.remove(0);
                testBalls -= p.maxSnowballsPerMinute(roundsMissed);
//                System.out.println(roundsMissed);
                roundsMissed = 1;
            } else {
                roundsMissed++;
            }
            if(i != 1) testBalls++;
            System.out.println("Round: " + i + " Balls: " + testBalls);
        }
    }
}

