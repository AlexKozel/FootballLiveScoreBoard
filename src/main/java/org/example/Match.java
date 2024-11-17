package org.example;

import java.time.Instant;

/**
 * Match class with Match info
 */
public class Match {
    private final String homeTeam;
    private final String awayTeam;
    private int homeScore = 0;
    private int awayScore = 0;
    private final String startTime;

    /**
     * constructor with conversion of command names to lower case for consistency and beginning time
     *
     * @param homeTeam - home team name
     * @param awayTeam - away team name
     */
    public Match(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam.toLowerCase();
        this.awayTeam = awayTeam.toLowerCase();
        this.startTime = generateUniqueStartTime();
    }


    /**
     * Use the nano time and the current time in milliseconds to generate a unique label
     *
     * @return Start time with UUID as string
     */
    private String generateUniqueStartTime() {
        return Instant.now().toString() + "-" + System.nanoTime();
    }


    /**
     * Update Score
     *
     * @param homeScore - int score fore home team
     * @param awayScore - int score fore away team
     */
    public void updateScore(int homeScore, int awayScore) {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    /**
     * Return command names with their original case
     *
     * @return teams's score and name
     */
    @Override
    public String toString() {
        return capitalize(homeTeam) + " " + homeScore + " - " + awayScore + " " + capitalize(awayTeam);
    }

    /**
     * Bringing to the original capitalised form
     *
     * @param teamName - team name as String
     * @return team name with capital first letter
     */
    private String capitalize(String teamName) {
        String[] parts = teamName.split(" ");
        StringBuilder capitalized = new StringBuilder();
        for (String part : parts) {
            capitalized.append(part.substring(0, 1).toUpperCase()).append(part.substring(1)).append(" ");
        }
        return capitalized.toString().trim();
    }

    /**
     * get total score
     */
    public int getTotalScore() {
        return homeScore + awayScore;
    }

    /**
     * get match start time
     *
     * @return startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * get home team name
     *
     * @return name as string
     */
    public String getHomeTeam() {
        return homeTeam;
    }

    /**
     * get away team name
     *
     * @return name as string
     */
    public String getAwayTeam() {
        return awayTeam;
    }
}
