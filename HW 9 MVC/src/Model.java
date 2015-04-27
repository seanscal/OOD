import java.util.ArrayList;

/**
 * represents the data for a checkers game
 */
public class Model {

  Board board;
  int size;
  Status status;
  Player turn;
  Check selected;

  //constructs a model that contains a board with the given size dimension
  Model(int s) {
    this.board = new Board(s);
    this.size = s;
    this.status = Status.Playing;
    this.turn = Player.First;
    selected = null;
  }

  /**
   * the possible states of the game
   */
  public enum Status {
    Playing, Won;
  }

  /**
   * a Builder to create models of default or specified size
   */
  public static final class Builder {

    /**
     * builds a model of default size 8
     * @return a checkers model
     */
    public Model build() {
      return new Model(8);
    }

    /**
     * builds a model of given size
     * @param size width/height length
     * @return model of that dimension
     */
    public Model build(int size) {
      return new Model(size);
    }
  }

  /**
   * builds model
   * @return default model
   */
  static Model checkers() {
    return builder().build();
  }

  /**
   * @return new builder to call methods from
   */
  static Builder builder() {
    return new Builder();
  }

  /**
   * @return the width of this model
   */
  int getWidth() {
    return size;
  }

  /**
   * @return the height of this model
   */
  int getHeight() {
    return size;
  }

  /**
   * @return the status of the game
   */
  Status getStatus() {
    return status;
  }

  /**
   * @return boolean if the game is over
   */
  boolean isGameOver() {
    if (board.getPlayersPieces(Player.First).size() != 0 &&
        board.getPlayersPieces(Player.Second).size()!= 0) {
      return false;
    }
    else {
      return true;
    }
  }

  /**
   * @return the next player to take a turn
   */
  Player getNextPlayer() {
    if (isGameOver()) {
      throw new IllegalStateException("the game is over, there is non next player");
    }
    return turn;
  }

  /**
   * @return the winner of the game
   * @throws java.lang.IllegalStateException if the game isn't over yet
   */
  public Player getWinner() {
    if (getStatus() != Status.Won) {
      throw new IllegalStateException("the game isn't over yet");
    }

    return turn;
  }

  /**
   * gets the pieces that can or must move on this turn
   * @return an arrayList of Checks of the pieces that can or must move
   */
  ArrayList<Check> movablePieces() {
    ArrayList<Check> worklist = board.getPlayersPieces(turn);
    ArrayList<Check> temp = new ArrayList<Check>();
    ArrayList<Check> havetomove = new ArrayList<Check>();

    for (Check c : worklist) {
      if (board.mustMove(c.x, c.y)) {
        havetomove.add(c);
      }
      if (board.hasMove(c.x, c.y)) {
        temp.add(c);
      }
    }
    if (havetomove.size() != 0){
      if(turn == Player.Second) {
        havetomove = Util.reverse(havetomove);
      }
      return havetomove;
    }

    if (turn == Player.Second) {
      temp = Util.reverse(temp);
    }
    return temp;
  }

  /**
   * moves the piece at the given check to the given location
   * @param fx the piece row
   * @param fy the piece column
   * @param sx the new location row
   * @param sy the new location column
   */
  public void move(int fx, int fy, int sx, int sy) {
    Check first = board.getCheck(fx, fy);
    if (turn != first.piece.player()) {
      throw new IllegalArgumentException("it's not your turn!");
    }
    if (!(movablePieces().contains(first))) {
      throw new IllegalArgumentException("this piece has no moves to make");
    }
    Check orig = board.getCheck(fx,fy);
    boolean crownedOrig = orig.getPiece().isCrowned();

    board.move(fx, fy, sx, sy);

    Check moved = board.getCheck(sx,sy);
    boolean crownedNew = moved.getPiece().isCrowned();
    Position jump = Position.fromRowColumn(sx,sy);
    Position start = Position.fromRowColumn(fx,fy);

    if (crownedNew == crownedOrig) {
      if (isGameOver()) {
        return;
      } else if (start.isJumpAdjacentTo(jump) && board.mustMove(sx, sy)) {
        return;
      }
    }
    turn = turn.other();
  }


  /**
   *gets the check at the given location on this models board
   * @param x the row
   * @param y the column
   * @return
   */
  public Check getCheckAt(int x, int y) {
    return board.getCheck(x, y);
  }

}