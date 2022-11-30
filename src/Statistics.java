import gametheory.snowball.Player;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Statistics {
    public static void main(String[] args) {
        OpponentAgreementPlayer p1 = new OpponentAgreementPlayer();
        OpponentAgreementPlayer p2 = new OpponentAgreementPlayer();

        PlayerTestSystem testSystem = new PlayerTestSystem();
        int numOfRounds = 60;
        int numOfBalls = 100;
        int numOfTests = 1;
        int p1Wins = 0;
        int p2Wins = 0;
        int draws = 0;
        for (int i = 0; i < numOfTests; i++) {
            p1.reset();
            p2.reset();
            String result = testSystem.GameSimulation(p1, p2, numOfRounds);
            if (result.equals("first player won")) {
                p1Wins++;
            } else if (result.equals("second player won")) {
                p2Wins++;
            } else {
                draws++;
            }
        }
        Path file = Paths.get("statistics.txt");
        String content = p1Wins + " " + p2Wins + " " + draws + "\n";
        double meanFirstPlayerOppoentShots = testSystem.firstPlayerOpponentShots.stream().mapToDouble(a -> a).average().orElse(0.0);
        double meanSecondPlayerOppoentShots = testSystem.secondPlayerOpponentShots.stream().mapToDouble(a -> a).average().orElse(0.0);
        double meanFirstPlayerShots = testSystem.firstPlayerLavaShots.stream().mapToDouble(a -> a).average().orElse(0.0);
        double meanSecondPlayerShots = testSystem.secondPlayerLavaShots.stream().mapToDouble(a -> a).average().orElse(0.0);
        double meanFirstPlayerBallsRemaining = testSystem.firstPlayerBallsRemaining.stream().mapToDouble(a -> a).average().orElse(0.0);
        double meanSecondPlayerBallsRemaining = testSystem.secondPlayerBallsRemaining.stream().mapToDouble(a -> a).average().orElse(0.0);
        try {
            Files.write(file, content.getBytes());
            Files.write(file, (meanFirstPlayerOppoentShots + "\n").getBytes(), StandardOpenOption.APPEND);
            Files.write(file, (meanSecondPlayerOppoentShots + "\n").getBytes(), StandardOpenOption.APPEND);
            Files.write(file, (meanFirstPlayerShots + "\n").getBytes(), StandardOpenOption.APPEND);
            Files.write(file, (meanSecondPlayerShots + "\n").getBytes(), StandardOpenOption.APPEND);
            Files.write(file, (meanFirstPlayerBallsRemaining + "\n").getBytes(), StandardOpenOption.APPEND);
            Files.write(file, (meanSecondPlayerBallsRemaining + "\n").getBytes(), StandardOpenOption.APPEND);

        } catch (Exception e) {
            System.out.println("Error");
        }
    }
}
