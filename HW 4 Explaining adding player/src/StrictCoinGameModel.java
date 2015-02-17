import java.util.List;

/** You don't need to implement this class or any concrete subclasses
 * for pset04.
 */
public final class StrictCoinGameModel implements CoinGameModel {
  // (Exercise 2) Declare the fields needed to support the methods in
  // the interface you’ve designed:
  private final int boardSize;
  private int turn;
  private int players;
  private List<Integer> playerList;
  private char[] boardArray;


  // (Exercise 3) Describe, as precisely as you can, your
  // representation’s class invariants:
  // CLASS INVARIANTS:
  // board != null && boardSize() > 0
  // coinCount() < boardSize()
  // coinCount() > 0
  // board only contains '-' or 'O'
  // players != null && players > 0


  // (Exercise 4) Describe your constructor API here by filling in
  // whatever arguments you need and writing good Javadoc. (You may
  // declare any combination of constructors and static factory
  // methods that you like, but you need not get fancy.)
  /**
   * Constructs [fill in comprehensive and clear Javadoc here]
   *
   * @param board - string containing the board to play on including coins
   * @param players - number of players on the board
   * @throws IllegalArgumentException {@code board} is empty string
   * @throws NullPointerException if {@code board} is null
   * @throws IllegalArgumentException if {@code boardSize()} <= {@code coinCount()}
   * @throws IllegalArgumentException if {@code coinCount} == 0
   * @throws IllegalArgumentException {@code board} has the characters other than "-" or "O"
   * @throws IllegalArgumentException if {@code players} < 1
   * @throws NullPointerException if {@code players} is null
   */
  protected StrictCoinGameModel(String board, int players) {
    // You don't need to implement this constructor.
    throw new UnsupportedOperationException("no need to implement this");
  }

    public int boardSize() {return 0;}

    public int coinCount(){return 0;}

    public int getCoinPosition(int coinIndex){return 0;}

    public boolean isGameOver(){return false;}

    public void move(int coinIndex, int newPosition){}

    public int getPlayers(){return 0;}

    public int addPlayer(){return 0;}

    public int getTurn(){return 0;}

    public int currentPlayerTurn(){return 0;}

    public int getWinner(){return 0;}

}
