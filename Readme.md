# Live Football Scoreboard

Library for displaying current World Cup matches. Allows you to track and update match scores, as well as get a summary of current games.

## Functionality description

The library supports the following operations:

1. **Start a new match**:
    - Accepts home and away teams.
    - The starting score of the match is 0 - 0.

2. **Update Score**:
    - Updates the score of the match.
    - A pair of values is expected: the score for the home and away teams.

3. **End Match**:
    - Removes the match from the scoreboard.

4. **Get a summary of the current matches**:
    - The summary is sorted by total points scored.
    - For matches with the same number of points, it is sorted by the time they were added to the scoreboard.

## How to start the project

1. Clone the repository:
   ```bash
   git clone https://github.com/AlexKozel/FootballLiveScoreBoard.git
    ```

2. Navigate to the project directory:
    ```bash
    cd FootballLiveScoreBoard
    ```

3. Build the project using Maven:
    ````bash
    mvn clean install
    ```

4. Run the demonstration:
    - Use the `Main` class to operate the scoreboard. Example usage:
      ```java
      public class Main {
      public static void main(String[] args) {
      Scoreboard scoreboard = new Scoreboard();
      scoreboard.startMatch("Mexico", "Canada");
      scoreboard.updateScore("Mexico", "Canada", 0, 5);
      scoreboard.startMatch("Spain", "Brazil");
      scoreboard.updateScore("Spain", "Brazil", 10, 2);
      // Next, the matches are added and updated in the same way
         }
      }

## Dependencies

- **JUnit 5** for testing
- **Maven** for building and managing dependencies

## Testing

Use the following command to run the tests:
```bash
mvn test