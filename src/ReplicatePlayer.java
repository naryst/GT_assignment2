import gametheory.snowball.Player;

public class ReplicatePlayer implements Player {

    int snowballNumber;
    int minutesPassedAfterYourShot;
    int roundNumber;
    int shootToOpponentField;
    int shootToHotField;
    @Override
    public void reset() {
        this.snowballNumber = 100;
        this.minutesPassedAfterYourShot = 0;
        this.roundNumber = 1;
        this.shootToOpponentField = 0;
        this.shootToHotField = 0;
    }

    public ReplicatePlayer(){
        this.reset();
    }

    @Override
    public int shootToOpponentField(int opponentLastShotToYourField, int snowballNumber, int minutesPassedAfterYourShot) {
        int maxShot =  Math.min(this.maxSnowballsPerMinute(minutesPassedAfterYourShot), snowballNumber);
        int shot = Math.min(maxShot, opponentLastShotToYourField);
        this.snowballNumber -= shot;
        this.shootToOpponentField = shot;
        return shot;
    }

    @Override
    public int shootToHotField(int opponentLastShotToYourField, int snowballNumber, int minutesPassedAfterYourShot) {
        int maxShot =  Math.min(this.maxSnowballsPerMinute(minutesPassedAfterYourShot), this.snowballNumber);
        maxShot -= opponentLastShotToYourField;
        maxShot = Math.max(maxShot, 0);
        int shot = Math.min(maxShot, opponentLastShotToYourField);
        this.snowballNumber -= shot;
        this.shootToHotField = shot;
        return shot;
    }

    @Override
    public String getEmail() {
        return null;
    }
}
