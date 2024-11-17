package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit test for LiveScoreboard
 */
public class LiveScoreboardTest {

    private Scoreboard scoreboard;

    @BeforeEach
    public void setUo() {
        scoreboard = new Scoreboard();
    }

    @Test
    public void testStartMatchSuccessfully() {
        scoreboard.startMatch("Mexico", "Canada");

        List<Match> matches = scoreboard.getSummary();
        assertEquals(1, matches.size(), "There should be one match in the scoreboard");
        assertTrue(matches.stream().anyMatch(match -> match.getHomeTeam().equals("mexico") &&
                match.getAwayTeam().equals("canada")), "Match between Mexico and Canada should exist");
        assertEquals(0, matches.get(0).getTotalScore(), "total score - 0");
    }

    @Test
    public void testStartMatchDuplicate() {
        scoreboard.startMatch("Mexico", "Canada");

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            scoreboard.startMatch("mexico", "CANADA");
        });
        assertEquals("Match already exists!", thrown.getMessage(), "Duplicate match should throw exception");
    }

    @Test
    public void testStartAndUpdateMatch() {
        scoreboard.startMatch("Mexico", "Canada");
        scoreboard.updateScore("Mexico", "Canada", 1, 2);

        List<Match> matches = scoreboard.getSummary();

        assertEquals(1, matches.size(), "There should be one match in the scoreboard");
        assertEquals("Mexico 1 - 2 Canada", matches.get(0).toString());
        assertEquals(3, matches.get(0).getTotalScore());
    }

    @Test
    public void testFinishMatch() {
        scoreboard.startMatch("Mexico", "Canada");
        List<Match> summaryBefore = scoreboard.getSummary();
        assertEquals(1, summaryBefore.size());

        scoreboard.finishMatch("Mexico", "Canada");

        List<Match> summaryAfter = scoreboard.getSummary();
        assertEquals(0, summaryAfter.size());
    }

    @Test
    public void testStartMatchWithNullHomeTeam() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            scoreboard.startMatch(null, "Canada");
        });
        assertEquals("Team name cannot be null or empty", thrown.getMessage(), "Team name cannot be null");
    }

    @Test
    public void testStartMatchWithNullAwayTeam() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            scoreboard.startMatch("Mexico", null);
        });
        assertEquals("Team name cannot be null or empty", thrown.getMessage(), "Team name cannot be null");
    }

    @Test
    public void testStartMatchWithEmptyHomeTeam() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            scoreboard.startMatch("", "Canada");
        });
        assertEquals("Team name cannot be null or empty", thrown.getMessage(), "Team name cannot be empty");
    }

    @Test
    public void testStartMatchWithEmptyAwayTeam() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            scoreboard.startMatch("Mexico", "");
        });
        assertEquals("Team name cannot be null or empty", thrown.getMessage(), "Team name cannot be empty");
    }

    @Test
    public void testStartMatchWithWhitespaceHomeTeam() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            scoreboard.startMatch("   ", "Canada");
        });
        assertEquals("Team name cannot be null or empty", thrown.getMessage(), "Team name cannot be just spaces");
    }

    @Test
    public void testStartMatchWithWhitespaceAwayTeam() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            scoreboard.startMatch("Mexico", "   ");
        });
        assertEquals("Team name cannot be null or empty", thrown.getMessage(), "Team name cannot be just spaces");
    }

    @Test
    public void testUpdateScoreWithNegativeHomeScore() {
        scoreboard.startMatch("Mexico", "Canada");

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            scoreboard.updateScore("Mexico", "Canada", -1, 2);
        });
        assertEquals("Scores cannot be negative", thrown.getMessage(), "Negative home score should throw exception");
    }

    @Test
    public void testUpdateScoreWithNegativeAwayScore() {
        scoreboard.startMatch("Mexico", "Canada");

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            scoreboard.updateScore("Mexico", "Canada", 2, -2);
        });
        assertEquals("Scores cannot be negative", thrown.getMessage(), "Negative away score should throw exception");
    }

    @Test
    public void testUpdateScoreWithNegativeScores() {
        scoreboard.startMatch("Mexico", "Canada");

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            scoreboard.updateScore("Mexico", "Canada", -1, -1);
        });
        assertEquals("Scores cannot be negative", thrown.getMessage(), "Negative scores should throw exception");
    }

    @Test
    public void testSummarySorting() {
        scoreboard.startMatch("Mexico", "Canada");
        scoreboard.updateScore("Mexico", "Canada", 0, 5);

        scoreboard.startMatch("Spain", "Brazil");
        scoreboard.updateScore("Spain", "Brazil", 10, 2);

        scoreboard.startMatch("Germany", "France");
        scoreboard.updateScore("Germany", "France", 2, 2);

        scoreboard.startMatch("Uruguay", "Italy");
        scoreboard.updateScore("Uruguay", "Italy", 6, 6);

        scoreboard.startMatch("Argentina", "Australia");
        scoreboard.updateScore("Argentina", "Australia", 3, 1);

        List<Match> summary = scoreboard.getSummary();

        assertEquals("Uruguay 6 - 6 Italy", summary.get(0).toString());
        assertEquals("Spain 10 - 2 Brazil", summary.get(1).toString());
        assertEquals("Mexico 0 - 5 Canada", summary.get(2).toString());
        assertEquals("Argentina 3 - 1 Australia", summary.get(3).toString());
        assertEquals("Germany 2 - 2 France", summary.get(4).toString());
    }
}
