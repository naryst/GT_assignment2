package gametheory.snowball.student2022;

import java.util.ArrayList;
import java.util.Scanner;

import gametheory.snowball.Player;

class RandomPlayer implements Player {

    int snowballNumber;
    int minutesPassedAfterYourShot;
    int roundNumber;

    boolean shotToOpponentField;
    boolean shotToHotField;
    int showBallsToOpponentField;
    int showBallsToHotField;

    public RandomPlayer() {
        this.reset();
    }

    @Override
    public void reset() {
        this.snowballNumber = 100;
        this.minutesPassedAfterYourShot = 0;
        this.roundNumber = 1;
    }

    @Override
    public int shootToOpponentField(int opponentLastShotToYourField, int snowballNumber, int minutesPassedAfterYourShot) {
        int maxShot = this.maxSnowballsPerMinute(minutesPassedAfterYourShot);

        // shot is random num from 0 to min of maxShot and number of snowballs
        int shot = (int) (Math.random() * Math.min((maxShot + 1), (snowballNumber)));
        this.snowballNumber -= shot;
        this.showBallsToOpponentField = shot;
        return shot;
    }

    @Override
    public int shootToHotField(int opponentLastShotToYourField, int snowballNumber, int minutesPassedAfterYourShot) {

        int maxShot = this.maxSnowballsPerMinute(minutesPassedAfterYourShot);
        maxShot -= opponentLastShotToYourField;
        maxShot = Math.max(maxShot, 0);
        int shot = (int) (Math.random() * Math.min((maxShot + 1), (snowballNumber)));
        this.snowballNumber -= shot;
        this.showBallsToHotField = shot;
        return shot;
    }

    @Override
    public String getEmail() {
        return "n.sergeev@innopolis.university";
    }
}

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numOfRounds = 60;
        int numOfBalls = 100;

        RandomPlayer p = new RandomPlayer();

        ArrayList<Integer> opponent_shots = new ArrayList<>();
        for (int i = 1; i <= numOfRounds; i++) {
            int result = p.maxSnowballsPerMinute(i);
            opponent_shots.add(result);
        }

        System.out.println("Opponent shots: " + opponent_shots);

        ArrayList<Integer> dp = new ArrayList<>();
        for (int i = 0; i < numOfRounds + 1; i++) {
            dp.add(0);
        }

        for (int i = 0; i < numOfRounds + 1; i++) {
            int max = 0;
            for (int j = 0; j < i; j++) {
                int cur = dp.get(j) + opponent_shots.get(i - j - 1);
                if (cur > max) {
                    max = cur;
                }
            }
            dp.set(i, max);
        }

        System.out.println("DP max_value: " + dp.get(numOfRounds));

        int testBalls = numOfBalls;
        for (int i = 1; i <= numOfRounds; i++) {
            testBalls += 1;
            if (i % 4 == 0) {
                testBalls -= p.maxSnowballsPerMinute(4);
//                testBalls -= p.maxSnowballsPerMinute(4);
            }
            System.out.println("Round: " + i + " Balls: " + testBalls);
        }
    }
}













