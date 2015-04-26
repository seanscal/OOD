import java.util.ArrayList;

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

  public static final class Builder {

    public Model build() {
      return new Model(8);
    }

    public Model build(int size) {
      return new Model(size);
    }

  }

  static Model checkers() {
    return builder().build();
  }

  static Builder builder() {
    return new Builder();
  }

  int getWidth() {
    return size;
  }

  int getHeight() {
    return size;
  }

  Status getStatus() {
    return status;
  }

  boolean isGameOver() {
    return checkForWinner();
  }

  Player getNextPlayer() {
    if (isGameOver()) {
      throw new IllegalStateException("the game is over, there is non next player");
    }
    return turn;
  }

  public Player getWinner() {
    if (getStatus() != Status.Won) {
      throw new IllegalStateException("the game isn't over yet");
    }

    return turn;
  }

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
      return havetomove;
    }

    if (turn == Player.Second) {
      temp = Util.reverse(temp);
    }
    return temp;
  }

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

  private boolean checkForWinner() {
    ArrayList<Check> firstp = board.getPlayersPieces(Player.First);
    ArrayList<Check> secondp = board.getPlayersPieces(Player.Second);

    if(firstp.size() == 0) {
      status = Status.Won;
      turn = Player.Second;
      return true;
    } else if(secondp.size() == 0) {
      status = Status.Won;
      turn = Player.First;
      return true;
    } else {
      return false;
    }
  }

  public Check getCheckAt(int x, int y) {
    return board.getCheck(x, y);
  }

  public void changeSelected(Check c) {
    this.selected = c;
  }
}