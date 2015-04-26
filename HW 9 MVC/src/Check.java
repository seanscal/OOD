import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Check {
  int x;
  int y;
  Piece piece;
  boolean isEmpty;
  boolean isSelected;
  Board board;

  Check(Piece p, int xx, int yy, boolean selected) {
    piece = p;
    x = xx;
    y = yy;
    isEmpty = false;
    isSelected = selected;
  }

  Check(int xx, int yy) {
    piece = null;
    x = xx;
    y = yy;
    isEmpty = true;
    isSelected = false;
  }

  boolean isEmpty() {
    return isEmpty;
  }

  Piece getPiece() {
    return piece;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Check)) {
      return false;
    }

    Check check = (Check) o;

    if (isEmpty != check.isEmpty) {
      return false;
    }
    if (x != check.x) {
      return false;
    }
    if (y != check.y) {
      return false;
    }
    if (piece != check.piece) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return this.x * 31 + this.y * 13;
    /*
    int result = x;
    result = 31 * result + y;
    result = 31 * result + (piece != null ? piece.hashCode() : 0);
    result = 31 * result + (isEmpty ? 1 : 0);
    return result;
    */
  }

  public String getText() {
    if (isEmpty) {
      return "";
    } else {
      return piece.toString();
    }
  }

  ArrayList<Position> moves () {
    Position pos =  Position.fromRowColumn(x, y);
    Stream<Position> jumpPositions = pos.jumpAdjacentPositions();
    Stream<Position> adjacentPositions = pos.adjacentPositions();
    Set<Position> posSet = adjacentPositions.collect(Collectors.toSet());
    Set<Position> jumpPosSet = jumpPositions.collect(Collectors.toSet());
    ArrayList<Position> returnable= new ArrayList<>();


    if (this.isEmpty()) {
      throw new IllegalArgumentException("an empty spot has no moves");
    }

    if (board.mustMove(x, y))
    {
      // for postions in the set of jumpable positions
      for(Position jumpTo: jumpPosSet) {
        Check jumpPositionCheck = new Check(jumpTo.row(),jumpTo.column());

        //if the jump is empty, check the jumped piece and if it's the other player's, add that pos
        if(jumpPositionCheck.isEmpty()){
          Position jumpedPosition = pos.findJumpedPosition(jumpTo);
          Check jumpedPositionCheck = new Check(jumpedPosition.row(), jumpedPosition.column());
          if (!jumpedPositionCheck.isEmpty() &&
              jumpedPositionCheck.getPiece().player() != this.getPiece().player()&&
              (this.getPiece().isCrowned() ||
               (!this.getPiece().isCrowned() &&
                ((jumpTo.isAbove(jumpedPosition) && this.getPiece().player() == Player.First) ||
                 (jumpTo.isBelow(jumpedPosition) && this.getPiece().player() == Player.Second))))){
            returnable.add(jumpTo);
          }
        }
      }
    }
    else if (board.hasMove(x, y))
    {
      for(Position moveTo: posSet) {
        Check movePositionCheck = new Check(moveTo.row(),moveTo.column());
        if (movePositionCheck.isEmpty()){
          if (((moveTo.isAbove(pos) && this.getPiece().player() == Player.First) ||
               (moveTo.isBelow(pos) && this.getPiece().player() == Player.Second)) ||
              this.getPiece().isCrowned())
          {
            returnable.add(moveTo);
          }
        }
      }
    }
    return returnable;
  }

  Check changeSelected(Check c, boolean status) {
    //this.isSelected = status;
    return new Check(c.piece, c.x, c.y, status);
  }
}