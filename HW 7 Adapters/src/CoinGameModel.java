import java.util.ArrayList;

/**
 * An interface for playing a coin game. The rules of a particular coin game will be implemented by
 * classes that implement this interface.
 */
public interface CoinGameModel {


  /**
   * returns the players currently playing
   * needed specifically for the adapter
   *
   * @return Arraylist of player IDs
   */
  ArrayList<Integer> getPlayersArray();

  /**
   * Gets the size of the board (the number of squares)
   *
   * @return the board size
   */
  int boardSize();

  /**
   * Gets the number of coins.
   *
   * @return the number of coins
   */
  int coinCount();

  /**
   * Gets the (zero-based) position of coin number {@code coinIndex}.
   *
   * @param coinIndex which coin to look up
   * @return the coin's position
   */
  int getCoinPosition(int coinIndex);

  /**
   * Returns whether the current game is over. The game is over if there are no valid moves.
   *
   * @return whether the game is over
   */
  boolean isGameOver();

  /**
   * Moves coin {@code coinIndex} to position {@code newPosition}, if the requested move is legal.
   * Throws {@code IllegalMoveException} if the requested move is illegal, which can happen in
   * several ways:
   *
   * <ul> <li>There is no coin with the requested index.</li> <li>The new position is occupied by
   * another coin.</li> <li>There is some other reason the move is illegal, as specified by the
   * concrete game.</li> </ul>
   *
   * @param coinIndex   which coin to move (numbered from the left)
   * @param newPosition where to move it to
   * @throws CoinGameModel.IllegalMoveException the move is illegal
   */
  void move(int coinIndex, int newPosition);

  /**
   * Gets the current number of players playing the game.
   *
   * @return current number of players
   */
  int getPlayers();

  /**
   * Uses getPlayers in order to find current number of players, then adds one and returns the new
   * number of players.
   *
   * @throws IllegalArgumentException if the game is over
   */
  void addPlayer(int index);

  /**
   * Gets which player's turn it currently is
   *
   * @return player number whose turn it is
   * @throws IllegalArgumentException if the game is over
   */
  int currentPlayerTurn();

  /**
   * Gets which player won the game (returns -1 if game in progress)
   *
   * @return player number who won the game or -1 if no winner
   */
  int getWinner();

  /**
   * The exception thrown by {@code move} when the requested move is illegal.
   *
   * <p>(Implementation Note: Implementing this interface doesn't require "implementing" the {@code
   * IllegalMoveException} class. Nesting a class within an interface is a way to strongly associate
   * that class with the interface, which makes sense here because the exception is intended to be
   * used specifically by implementations and clients of this interface.)
   */
  static class IllegalMoveException extends IllegalArgumentException {

    /**
     * Constructs a illegal move exception with no description.
     */
    public IllegalMoveException() {
      super();
    }

    /**
     * Constructs a illegal move exception with the given description.
     *
     * @param msg the description
     */
    public IllegalMoveException(String msg) {
      super(msg);
    }
  }
}
