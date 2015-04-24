import java.util.ArrayList;

public class Model {

  Board board;
  int size;
  Status status;
  Player turn;

  //constructs a model that contains a board with the given size dimension
  Model(int s) {
    this.board = new Board(s);
    this.size = s;
    this.status = Status.Playing;
    this.turn = Player.First;
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
    if (status == Status.Playing) {
      return false;
    } else {
      return true;
    }
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

  /*
    public int move(Player who, int where) {
      if (status != Status.Playing) {
        throw new IllegalStateException("game over");
      }

      if (who != turn) {
        throw new IllegalStateException("out of turn");

  */
  ArrayList<Check> movablePieces() {
    ArrayList<Check> worklist = board.getPlayersPieces(turn);
    ArrayList<Check> temp = new ArrayList<Check>();
    ArrayList<Check> jumpable = new ArrayList<Check>();

    for (Check c : worklist) {
      if (board.mustMove(c.x, c.y)){
        jumpable.add(c);
      }

      if (board.hasMove(c.x, c.y)) {
        temp.add(c);
      }
    }

    if (!jumpable.isEmpty()){
      return jumpable;
    }
    return temp;
  }

  public void move(int fx, int fy, int sx, int sy) {
    Check first = board.getCheck(fx, fy);
    if (turn != first.piece.player()) {
      throw new IllegalArgumentException("it's not your turn!");
    }
    if (!(Util.inCheckList(movablePieces(), first))) {
      throw new IllegalArgumentException("this piece has no moves to make");
    }
    board.move(fx, fy, sx, sy);
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

}