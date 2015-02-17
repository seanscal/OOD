//Strict Game - coins may not jump other coins
public final class StrictCoinGameModel extends AbstractCoinGameModel
{
  /* CLASS INVARIANT: board.length() > 0 */

  /**
   * Constructs a CoinGameModel in terms Strict Rules
   *
   * @param board the number of seconds (non-negative)
   * @throws IllegalArgumentException if {@code board.length()} <= 0
   * @throws java.lang.IllegalArgumentException if{@code board} characters are not '-' or 'O'
   *
   * CLASS INVARIANTS: boardSize() > 0, boardArray contains only '-' or 'O' characters
   */
  public StrictCoinGameModel(String board) {
    if (board == null) {
      throw new NullPointerException("No board detected for play");
    }

    boardSize = board.length();
    boardArray = board.toCharArray();
    int count = 0;

    if (boardSize <= 0) {
      throw new IllegalArgumentException("Board must contain at least 1 space");
    }

    while (count < boardSize) {
      if (board.charAt(count) == 'O') {
        boardArray[count] = 'O';
      }
      else if (board.charAt(count) == '-') {
        boardArray[count] = '-';
      }
      else {
        throw new IllegalArgumentException("Illegal character on board.");
      }
      count++;
    }
  }

  @Override
  protected AbstractCoinGameModel playGame(String board) {
    return new StrictCoinGameModel(board);
  }

  @Override
  public void move(int coinIndex, int newPosition) {
    int currentPos = getCoinPosition(coinIndex);
    int indexes;

    for (int i = 0; i < coinIndex; i++) {
      indexes = getCoinPosition(i);

      if (indexes == newPosition) {
        throw new IllegalMoveException("Coin cannot move onto another coin.");
      }
      else if(indexes > newPosition) {
        throw new IllegalMoveException("Coin cannot move over another coin using strict rules.");
      }
      else if(newPosition < 0) {
        throw new IllegalMoveException("Coin cannot move off the board.");
      }
    }

    if (currentPos < newPosition) {
      throw new IllegalMoveException("Coin cannot move right");
    }
    else if (currentPos == newPosition) {
      throw new IllegalMoveException("Coin cannot move to same spot");
    }
    else if (newPosition < 0) {
      throw new IllegalMoveException("Coin cannot move off the board");
    }
    else {
      boardArray[currentPos] = '-';
      boardArray[newPosition] = 'O';
    }
  }
}

