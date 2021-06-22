# Rock Paper Scissors Game
## Lottoland Back-End Challenge

This is a web project (only the back-end part, Java 8) for playing random rounds of Rock-Paper-Scissors game.

Rules of the game is below in case you’re not familiar:
https://en.wikipedia.org/wiki/Rock_paper_scissors

### Rules
There are two kinds of players:
  * The first one selection is always rock.
  * Second's choice is created randomly.

### What's Inside?
  * Spring Boot,
  * RESTful services,
  * JUnit, 
  * Mockito (to mock dependencies)
  * MockMvc (to test controllers)
  
### REST Services
* GET /game/round/play
    * Service which creates a round for the current game (second user selection is produced randomly).
* GET /game/restart
    * Service which starts a new game and saves the last game.
* GET /games/numberOfRounds
    * Service for providing the total number of rounds (including the restarted games)
* GET /games/numberOfGivenResult
    * Service for providing the total number of requested result (first user or second user wins or draw, including the restarted games)


### Design Decisions
  Because the task does not include a database, all data is kept in-memory, but persistence mechanism designed with Strategy pattern (
  `GameStorage`, composed with Factory Pattern: `GameStorageFactory`) so that one can use a database or a cache instead of a Map.
  
  It’s considered that there may not be too many rounds in a game. Starting from this idea, 
  a game is kept in memory until it’s restarted or “numberOfRounds” or “numberOfGivenResult” services are called, after that it will be persisted on which strategy is selected.  

  According to the round result, the number of result field (`numberOfRoundsThatFirstUserWins`, `numberOfRoundsThatSecondUserWins` and `numberOfRoundsDrawing` in `Game` class) is increased by one
  for each round creation in order to get total number of rounds in all games or total number of given results (e.g first user wins or draw) with high performance.

  To get round results quickly, there’s an ImmutableMap in `GameService` class which key is a list of two GameElements (The binary combination of `ROCK`, `PAPER` and `SCISSORS`) 
  and value is RoundResult (`DRAW`, `FIRST_PLAYER_WINS`, `SECOND_PLAYER_WINS`). Also, in case constant selection of one player changes (for this task is `ROCK` always), 
  there is no need to reconsider the game results by the help of this map and `PREDEFINED_FIRST_USER_SELECTION` constant.
  
  Random selection of the other player is handled by `getRandomRockPaperScissors()` method of `GameElement` enum. 

  `RoundResultConverter` utility class is used to convert json string (e.g. `FIRST_PLAYER_WINS`) to `RoundResult` enum
  

  
