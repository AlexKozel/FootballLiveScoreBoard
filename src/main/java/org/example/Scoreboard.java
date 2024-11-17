package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * ScoreBoard class with list of {@link Match}
 */
public class Scoreboard {
    private final List<Match> matches = new ArrayList<>();

    /**
     * Method for starting a case-sensitive match
     *
     * @param homeTeam - home team name
     * @param awayTeam - away team name
     */
    public void startMatch(String homeTeam, String awayTeam) {
        validateTeamName(homeTeam);
        validateTeamName(awayTeam);

        String homeTeamLc = homeTeam.trim().toLowerCase();
        String awayTeamLc = awayTeam.trim().toLowerCase();

        if (matches.stream().anyMatch(m -> m.getHomeTeam().equals(homeTeamLc) && m.getAwayTeam().equals(awayTeamLc))) {
            throw new IllegalArgumentException("Match already exists!");
        }
        matches.add(new Match(homeTeamLc, awayTeamLc));
    }

    /**
     * Method for score update
     *
     * @param homeTeam  - home team name
     * @param awayTeam  - away team name
     * @param homeScore - home team score
     * @param awayScore - away team score
     */
    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Scores cannot be negative");
        }
        Match match = findMatch(homeTeam, awayTeam);
        if (match == null) throw new IllegalArgumentException("Match not found!");
        match.updateScore(homeScore, awayScore);
    }

    /**
     * Method to end the match
     *
     * @param homeTeam - home team name
     * @param awayTeam - away team name
     */
    public void finishMatch(String homeTeam, String awayTeam) {
        Match match = findMatch(homeTeam, awayTeam);
        if (match == null) throw new IllegalArgumentException("Match not found!");
        matches.remove(match);
    }

    /**
     * Method for obtaining a match summary
     *
     * @return list of {@link Match}
     */
    public List<Match> getSummary() {
        return matches.stream()
                .sorted(Comparator.comparingInt(Match::getTotalScore)
                        .thenComparing(Match::getStartTime).reversed())
                .toList();
    }

    /**
     * method for finding a match
     *
     * @param homeTeam - home team name
     * @param awayTeam - away team name
     * @return {@link Match}
     */
    private Match findMatch(String homeTeam, String awayTeam) {
        return matches.stream()
                .filter(m -> m.getHomeTeam().equals(homeTeam.toLowerCase()) && m.getAwayTeam().equals(awayTeam.toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Validate that the team name is not null, empty or blank
     *
     * @param teamName - team name to validate
     */
    private void validateTeamName(String teamName) {
        if (teamName == null || teamName.trim().isEmpty()) {
            throw new IllegalArgumentException("Team name cannot be null or empty");
        }
    }

}
