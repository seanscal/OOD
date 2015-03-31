import java.util.ArrayList;

//Strict Game - coins may not jump other coins
public final class StrictCoinGameModel implements CoinGameModel {
  /* CLASS INVARIANTS: board.length() > 0,
   *                   players.size() > 0 */

  private int boardSize;
  private boolean[] booleanArray;
  private char[] boardArray;
  private int turn;
  private ArrayList<Integer> players = new ArrayList<Integer>();

  /**
   * Constructs a CoinGameModel in terms Strict Rules
   *
   * @param board      String representation of the game board
   * @param numPlayers the number of players (must be > 0)
   * @throws java.lang.NullPointerException     if {@code board} is null
   * @throws IllegalArgumentException           if {@code board.length()} <= 0
   * @throws java.lang.IllegalArgumentException if{@code board} characters are not '-' or 'O'
   * @throws java.lang.IllegalArgumentException if {@code numPlayers} <= 0
   */
  public StrictCoinGameModel(String board, int numPlayers) {
    if (board == null) {
      throw new NullPointerException("No board detected for play");
    }

    boardSize = board.length();
    booleanArray = new boolean[boardSize];
    boardArray = new char[boardSize];
    int count = 0;

    if (boardSize <= 0) {
      throw new IllegalArgumentException("Board must contain at least 1 space");
    }

    while (count < boardSize) {
      if (board.charAt(count) == 'O') {
        booleanArray[count] = true;
      } else if (board.charAt(count) == '-') {
        booleanArray[count] = false;
      } else {
        throw new IllegalArgumentException("Illegal character on board.");
      }
      count++;
    }

    if (numPlayers < 1) {
      throw new IllegalArgumentException("Must be at least 1 player");
    }

    for (int i = 0; i < numPlayers; i++) {
      players.add(i);
    }

    turn = 0;
  }

  @Override
  public ArrayList<Integer> getPlayersArray()
  {
    return players;
  }

  @Override
  public int boardSize() {
    return this.boardSize;
  }

  @Override
  public int coinCount() {
    int count = 0;
    for (int i = 0; i < boardSize(); i++) {
      if (booleanArray[i]) {
        count++;
      }
    }
    return count;
  }

  @Override
  public int getCoinPosition(int coinIndex) {
    int count = 0;

    for (int i = 0; i < boardSize(); i++) {
      if (booleanArray[i]) {
        if (count == coinIndex) {
          return i;
        }
        count++;
      }
    }
    throw new IllegalArgumentException("Coin Index does not exist.");
  }

  @Override
  public boolean isGameOver() {
    int total = coinCount();
    for (int i = 0; i < total; i++) {
      if (!booleanArray[i] && total > 0) {
        return false;
      }
    }
    return true;
  }


  @Override
  public void move(int coinIndex, int newPosition) {
    int currentPos = getCoinPosition(coinIndex);
    int indexes;

    for (int i = 0; i < coinIndex; i++) {
      indexes = getCoinPosition(i);

      if (indexes == newPosition) {
        throw new IllegalMoveException("Coin cannot move onto another coin.");
      } else if (indexes > newPosition) {
        throw new IllegalMoveException("Coin cannot move over another coin using strict rules.");
      } else if (newPosition < 0) {
        throw new IllegalMoveException("Coin cannot move off the board.");
      }
    }

    if (currentPos < newPosition) {
      throw new IllegalMoveException("Coin cannot move right.");
    } else if (newPosition < 0) {
      throw new IllegalMoveException("Coin cannot move off the board.");
    } else {
      booleanArray[currentPos] = false;
      booleanArray[newPosition] = true;
      turn++;
      if (turn == players.size()) {
        turn = 0;
      }
    }
  }

  @Override
  public int getPlayers() {
    return players.size();
  }

  @Override
  public void addPlayer(int index) {
    if (isGameOver()){
      throw new IllegalArgumentException("Cannot add players to a finished game");
    }
    if (index <= currentPlayerTurn()) {
      turn++;
    }
    players.add(index, getPlayers());
  }

  @Override
  public int currentPlayerTurn() {
    return players.get(turn);
  }

  @Override
  public int getWinner() {
    if (!isGameOver()) {
      return -1;
    }
    if (turn == 0) {
      return (players.get(players.size() - 1));
    }
    return players.get(turn - 1);
  }

  @Override
  public String toString() {
    int count = 0;
    while (count < boardSize) {
      if (booleanArray[count]) {
        boardArray[count] = 'O';
      } else if (!booleanArray[count]) {
        boardArray[count] = '-';
      }
      count++;
    }
    return new String(boardArray);
  }
}

