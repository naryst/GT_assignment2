//package gametheory.snowball.students2022;

import gametheory.snowball.Player;

/**
 * Smart plus player strategy
 */
class SmartPlusPlayer implements Player {

    private boolean attackOpponent;
    private int rounds = 0;
    private int snowballs = 0;

    @Override
    public void reset() {
        rounds = 0;
        snowballs = 0;
        attackOpponent = false;
    }

    @Override
    public int shootToOpponentField(int opponentLastShotToYourField, int snowballNumber, int minutesPassedAfterYourShot) {
        rounds += 1;
        snowballs = snowballNumber;

        if (opponentLastShotToYourField != 0){
            attackOpponent = true;
        }

        if (attackOpponent){
            if (rounds == 60 || rounds == 7 || (minutesPassedAfterYourShot % 4 == 0 && rounds > 7 && rounds != 59)) {
                return getNumToShot(maxSnowballsPerMinute(minutesPassedAfterYourShot));
            }
        }

        return 0;
    }

    @Override
    public int shootToHotField(int opponentLastShotToYourField, int snowballNumber, int minutesPassedAfterYourShot) {
        if (!attackOpponent){
            if (rounds == 7 || (minutesPassedAfterYourShot % 4 == 0 && rounds > 7 && rounds != 59)) {
                return getNumToShot(maxSnowballsPerMinute(minutesPassedAfterYourShot));
            }

            if (rounds == 59){
                attackOpponent = true;
            }
        }
        return 0;
    }

    @Override
    public String getEmail() {
        return "SmartPlusPlayer";
    }

    /**
     * Check correctness of the number of snowball to shot
     * @param toShot number of snowball to shot
     * @return correct number of snowball to shot
     */
    private int getNumToShot(int toShot){
        if (snowballs - toShot < 0){
            return snowballs;
        }else{
            return toShot;
        }
    }
}
