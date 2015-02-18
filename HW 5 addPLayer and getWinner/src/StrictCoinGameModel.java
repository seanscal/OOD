import java.util.ArrayList;

//Strict Game - coins may not jump other coins
public final class StrictCoinGameModel implements CoinGameModel {
  /* CLASS INVARIANT: board.length() > 0 */

  /**
   * Constructs a CoinGameModel in terms Strict Rules
   *
   * @param board the number of seconds (non-negative)
   * @throws IllegalArgumentException if {@code board.length()} <= 0
   * @throws IllegalArgumentException if{@code board} characters are not '-' or 'O'
   *
   * CLASS INVARIANTS: boardSize() > 0, boardArray contains only '-' or 'O' characters
   */

  protected int boardSize;
  protected boolean[] booleanArray;
  protected char[] boardArray;
  protected int turns;
  ArrayList <Integer> players = new ArrayList<Integer>();

  public StrictCoinGameModel(String board, int numPlayers) {
    if (board == null) {
      throw new NullPointerException("No board detected for play");
    }

    boardSize = board.length();
    booleanArray = new boolean[boardSize];
    boardArray = new char[boardSize];
    turns = 0;
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

    for (int i = 0; i <numPlayers; i++)
    {
      players.add(i);
    }
  }

  @Override
  public int boardSize() {
    return this.boardSize;
  }

  @Override
  public int coinCount() {
    int count = 0;
    for (int i = 0; i < boardSize(); i++) {
      if (booleanArray[i] == true) {
        count++;
      }
    }
    return count;
  }

  @Override
  public int getCoinPosition(int coinIndex) {
    int total = coinCount();
    int count = 0;

    for (int i = 0; i < boardSize(); i++) {
      if (booleanArray[i] == true) {
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
      throw new IllegalMoveException("Coin cannot move right");
    } else if (currentPos == newPosition) {
      throw new IllegalMoveException("Coin cannot move to same spot");
    } else if (newPosition < 0) {
      throw new IllegalMoveException("Coin cannot move off the board");
    } else {
      booleanArray[currentPos] = false;
      booleanArray[newPosition] = true;
      turns++;
    }
  }

  @Override
  public int getPlayers(){
    return players.size();
  }

  @Override
  public void addPlayer(int index){
    players.add(index,getPlayers());
  }

  @Override
  public int getTurn(){return turns;}

  @Override
  public int currentPlayerTurn(){
    return getTurn() % getPlayers();
  }

  @Override
  public int getWinner(){
    return (getTurn() - 1) % getPlayers();
  }

  @Override
  public String toString() {
    int count = 0;
    while (count < boardSize) {
      if (booleanArray[count] == true) {
        boardArray[count] = 'O';
      } else if (booleanArray[count] == false) {
        boardArray[count] = '-';
      }
      count++;
    }
    String board = new String(boardArray);
    return board;
  }
}

