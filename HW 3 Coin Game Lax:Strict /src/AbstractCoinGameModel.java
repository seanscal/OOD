/**
 * Provides functionality common to both representational subclasses,
 * including comparisons, hashing, addition, subtraction, and the ability
 * to construct new durations of the same class as a given instance.
 */
abstract class AbstractCoinGameModel implements CoinGameModel {
  protected char[] boardArray;
  protected int boardSize;

  /**
   * Constructs a new CoinGameModel having the same class as {@code this}.
   *
   * @param board The String containing the board for the game
   * @return the new board
   * @throws IllegalArgumentException {@code board} is empty
   * @throws java.lang.NullPointerException {@code board} = null
   * @throws IllegalArgumentException {@code board} has the characters other than "-" or "O"
   */
  protected abstract AbstractCoinGameModel playGame(String board);

  @Override
  public int boardSize() {
    return this.boardSize;
  }

  @Override
  public int coinCount() {
    int count = 0;
    for (int i =0; i < boardSize(); i++) {
      if (boardArray[i] == 'O') {
        count++;
      }
    }
    return count;
  }

  @Override
  public int getCoinPosition(int coinIndex) {
    int total = coinCount();
    int count = 0;

    for (int i =0; i < boardSize(); i++) {
      if (boardArray[i] == 'O') {
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
    for (int i =0; i < total; i++) {
      if (boardArray[i] != 'O' && total > 0) {
        return false;
      }
    }
    return true;
  }

  @Override
  public String toString() {
    String board = new String(boardArray);
    return board;
  }
}
