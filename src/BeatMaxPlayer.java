import gametheory.snowball.Player;

public class BeatMaxPlayer  implements Player {
    int snowballNumber;
    int minutesPassedAfterYourShot;
    int roundNumber;

    public BeatMaxPlayer() {
        this.reset();
    }

    @Override
    public void reset() {
        this.snowballNumber = 100;
        this.minutesPassedAfterYourShot = 1;
        this.roundNumber = 1;
    }

    @Override
    public int shootToOpponentField(int opponentLastShotToYourField, int snowballNumber, int minutesPassedAfterYourShot) {
        int shot;
        if (minutesPassedAfterYourShot == 4) {
            shot = Math.min(this.maxSnowballsPerMinute(minutesPassedAfterYourShot), this.snowballNumber);
        } else {
            shot = 0;
        }

        this.snowballNumber -= shot;
        return shot;
    }

    @Override
    public int shootToHotField(int opponentLastShotToYourField, int snowballNumber, int minutesPassedAfterYourShot) {
        int shot;
        if (minutesPassedAfterYourShot == 4) {
            shot = Math.min(this.maxSnowballsPerMinute(minutesPassedAfterYourShot), this.snowballNumber);
        } else {
            shot = 0;
        }
        this.snowballNumber -= shot;
        return shot;
    }

    @Override
    public String getEmail() {
        return "n.sergeev@innopolis.university";
    }
}
