package gametheory.snowball.student2022;

import gametheory.snowball.Player;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class implements the player that make decision based on the opponent's strategy
 * and the current state of the game.
 * It waits till some moment for the opponent shoot.
 * Then, if opponent shoot to the hot field, it shoots to the hot field with maximized strategy.
 * And if opponent shoot to his field, it shoots to the opponent field with maximized strategy.
 */
public class OpponentAgreementPlayer implements Player {

    // some Class fields needed for determining the strategy
    int snowballNumber;
    int minutesPassedAfterYourShot;
    int roundNumber;
    int shootToOpponentField;
    int shootToHotField;
    Integer[] shotRounds;
    ArrayList<Integer> shotRoundsList;
    int opponentShotsSum;


    /**
     * Method for calculation most valuable strategy for the player.
     * This method uses DP algorithm for finding the most valuable strategy.
     *
     * @param currentRound current round of the game from which we start to calculate the strategy
     * @return the most valuable strategy for the player
     */
    public ArrayList<Integer> calculateStrategy(int currentRound){
        int numOfRounds = 60;


        // calculate the result of the given function for every number skipped of rounds(from 1 to 60)
        ArrayList<Integer> opponent_shots = new ArrayList<>();
        for (int i = 0; i < numOfRounds; i++) {
            int result = this.maxSnowballsPerMinute(i);
            opponent_shots.add(result);
        }


        // array for calculating the most valuable strategy (initially all values are 0)
        ArrayList<Integer> dp = new ArrayList<>();
        ArrayList<Integer> fromPath = new ArrayList<>();
        for (int i = 0; i <= numOfRounds; i++) {
            dp.add(0);
        }

        // DP algorithm for constructing strategy
        // with max number of snowballs shot
        for (int i = currentRound; i <= numOfRounds; i++) {
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
            fromPath.add(from);
        }

        // Constructing the strategy
        // In other words, list of rounds when we should shoot
        ArrayList<Integer> path = new ArrayList<>();
        int cur = numOfRounds;
        while (cur >= currentRound) {
            path.add(cur);
            cur = fromPath.get(cur-currentRound);
        }
        // reverse the path
        ArrayList<Integer> reversedPath = new ArrayList<>();
        for (int i = path.size() - 1; i >= 0; i--) {
            reversedPath.add(path.get(i));
        }
        return reversedPath;
    }

    /**
     * Reset the player's fields to the initial values.
     */
    @Override
    public void reset() {
        this.snowballNumber = 100;
        this.minutesPassedAfterYourShot = 0;
        this.roundNumber = 1;
        this.shootToOpponentField = -1;
        this.shootToHotField = -1;
        this.shotRoundsList = calculateStrategy(6);
        this.opponentShotsSum = 0;
    }

    /**
     * Constructor for the gametheory.snowball.student2022.OpponentAgreementPlayer class
     * It sets the initial values for the fields of the class
     * This constructor just call the method reset() for the class
     */
    public OpponentAgreementPlayer(){
        this.reset();
    }

    /**
     * This method returns the number of snowballs the player will shoot to opponent’s field.
     * This method will return 0 if the opponent will not shoot to his field.
     * And it will return the number of snowballs to shoot to the opponent's field
     * If the opponent will shoot to his field.
     *
     * @param opponentLastShotToYourField the last shot of the opponent to your
     *                                    field last round
     * @param snowballNumber              the number of snowballs on your field
     *                                    used to not overcome the limit of snowballs
     *
     * @param minutesPassedAfterYourShot  the number of minutes passed after
     *                                    your last shot to any field
     *
     * @return the number of snowballs the player will shoot to opponent’s field
     */
    @Override
    public int shootToOpponentField(int opponentLastShotToYourField, int snowballNumber, int minutesPassedAfterYourShot) {
        // if the opponent didn't shoot to his field don't shoot to the opponent's field
        if(opponentLastShotToYourField == 0 && this.opponentShotsSum == 0){
            return 0;
        }
        // else shot to opponents field according to the strategy
        int maxShot = Math.min(this.maxSnowballsPerMinute(minutesPassedAfterYourShot), snowballNumber);
        int currShot = 0;
        if (this.roundNumber == this.shotRoundsList.get(0)) {
            currShot = maxShot;
            this.shotRoundsList.remove(0);
        }
        // update local information about the game and return the result of shooting
        this.snowballNumber -= currShot;
        this.snowballNumber += opponentLastShotToYourField;
        this.shootToOpponentField = currShot;
        this.opponentShotsSum += opponentLastShotToYourField;
        return currShot;
    }

    /**
     * This method returns the number of snowballs the player will shoot
     * in the hot field. It's depends on the opponent's strategy.
     * If the opponent shoot to the hot field, the player will shoot to the hot field as well.
     * If the opponent shoot to his field, the player will shoot to the opponent field
     * and this method will return 0.
     *
     * @param opponentLastShotToYourField the last shot of the opponent to your
     *                                    field (0 – if this is the first shot)
     * @param snowballNumber              the number of snowballs on your field
     * @param minutesPassedAfterYourShot  the number of minutes passed after
     *                                    your last shot to any field
     *                                    (0 – if this is the first shot)
     *
     * @return the number of snowballs the player will shoot in the hot field
     */
    @Override
    public int shootToHotField(int opponentLastShotToYourField, int snowballNumber, int minutesPassedAfterYourShot) {
        // if the opponent shoot to his field don't shoot to the hot field
        if(opponentLastShotToYourField != 0 || this.opponentShotsSum != 0){
            this.roundNumber++;
            return 0;
        }
        // else shot to hot field according to the strategy
        int maxShot = Math.min(this.maxSnowballsPerMinute(minutesPassedAfterYourShot), snowballNumber);
        int currShot = 0;
        if (this.roundNumber == this.shotRoundsList.get(0)) {
            currShot = maxShot;
            this.shotRoundsList.remove(0);
        }
        // update local information about the game and return the result of shooting
        this.snowballNumber -= currShot;
        this.shootToOpponentField = currShot;
        this.roundNumber++;
        return currShot;
    }

    /**
     * @return my email
     */
    @Override
    public String getEmail() {
        return "n.sergeev@innopolis.university";
    }
}