import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board {

  ArrayList<ArrayList<Check>> board;
  int dimension;

  // constructor that makes a board based on the dimensions
  Board(int dimension) {
    ArrayList<ArrayList<Check>> temp = new ArrayList<ArrayList<Check>>();
    int emptyrows = (dimension / 4);
    int placeoffset = ((dimension - emptyrows) / 2) + emptyrows;
    for (int x = 0; x < dimension; x++) {
      ArrayList<Check> row = new ArrayList<Check>();
      for (int y = 0; y < dimension; y++) {
        if ((y < (dimension - placeoffset)) && Util.placePiece(y,x)) {
          row.add(new Check(Piece.NormalSecond, y, x, false));
        } else if (y >= placeoffset && Util.placePiece(y,x)) {
          row.add(new Check(Piece.NormalFirst, y, x, false));
        } else {
          row.add(new Check(y, x));
        }
      }
      temp.add(row);
    }
    this.board = temp;

    this.dimension = dimension;
  }

  Check getCheck(int x, int y) {
    if (x < 0 || x > dimension || y < 0 || y > dimension) {
      throw new IndexOutOfBoundsException("that is not a space on the board");
    }

    return board.get(y).get(x);
  }

  Check changeCheck(int x, int y, Piece p) {
    Check work = getCheck(x, y);
    if (p != work.piece) {
      board.get(y).set(x, new Check(p, x, y, false));
      return new Check(p, x, y, false);
    } else {
      board.get(y).set(x, new Check(x, y));
      return new Check(x, y);
    }
  }

  void move(int fx, int fy, int sx, int sy) {
    Check first = getCheck(fx, fy);
    Check second = getCheck(sx, sy);
    Piece piece = first.getPiece();

    if (first.isEmpty()) {
      throw new IllegalArgumentException("there is no coin to move");
    } else if (!(second.isEmpty())) {
      throw new IllegalArgumentException("cannot move to an occupied space");
    }

    if((first.x - second.x) == 2 && (first.y - second.y) == 2) {
      Check del = getCheck(first.x - 1, first.y - 1);
      changeCheck(del.x, del.y, del.piece);
    } else if((first.x - second.x) == -2 && (first.y - second.y) == 2) {
      Check del2 = getCheck(first.x + 1, first.y - 1);
      changeCheck(del2.x, del2.y, del2.piece);
    } else if((first.x - second.x) == -2 && (first.y - second.y) == -2) {
      Check del3 = getCheck(first.x + 1, first.y + 1);
      changeCheck(del3.x, del3.y, del3.piece);
    } else if((first.x - second.x) == 2 && (first.y - second.y) == -2) {
      Check del4 = getCheck(first.x - 1, first.y + 1);
      changeCheck(del4.x, del4.y, del4.piece);
    }
    if (second.x == 7 && first.piece == Piece.NormalSecond) {
      changeCheck(fx, fy, piece);
      changeCheck(sx, sy, Piece.CrownedSecond);
    } else if (second.x == 0 && first.piece == Piece.NormalFirst) {
      changeCheck(fx, fy, piece);
      changeCheck(sx, sy, Piece.CrownedFirst);
    } else {
      changeCheck(fx, fy, piece);
      changeCheck(sx, sy, piece);
    }
  }

  ArrayList<Check> getPlayersPieces(Player p) {
    ArrayList<Check> temp = new ArrayList<>();
    if (p == Player.First) {
      for (int y = 0; y < dimension; y++) {
        for (int x = 0; x < dimension; x++) {
          Piece pi = getCheck(y, x).getPiece();
          if (pi == Piece.CrownedFirst || pi == Piece.NormalFirst) {
            temp.add(getCheck(y, x));
          }
        }
      }
    } else if (p == Player.Second) {
      for (int y = 0; y < dimension; y++) {
        for (int x = 0; x < dimension; x++) {
          Piece pi = getCheck(y, x).getPiece();
          if (pi == Piece.CrownedSecond || pi == Piece.NormalSecond) {
            temp.add(getCheck(y, x));
          }
        }
      }
    }
    return temp;
  }

  boolean hasMove(int x, int y) {
    Position pos = Position.fromRowColumn(x, y);
    Stream<Position> adjacentPositions = pos.adjacentPositions();
    Set<Position> posSet = adjacentPositions.collect(Collectors.toSet());
    Check c = getCheck(x, y);

    if (c.isEmpty()) {
      throw new IllegalArgumentException("an empty spot has no moves");
    }
    for(Position moveTo: posSet) {
      Check movePositionCheck = board.get(moveTo.column()).get(moveTo.row());
      if (movePositionCheck.isEmpty()){
        if (((moveTo.isAbove(pos) && c.getPiece().player() == Player.First) ||
             (moveTo.isBelow(pos) && c.getPiece().player() == Player.Second)) ||
            c.getPiece().isCrowned())
        {
          return true;
        }
      }
    }
    return false;
  }


  boolean mustMove(int x, int y) {
    Check c = getCheck(x, y);
    if (c.isEmpty()) {
      throw new IllegalArgumentException("an empty spot has no moves");
    }

    Position pos = Position.fromRowColumn(x, y);
    Stream<Position> jumpPositions = pos.jumpAdjacentPositions();
    Set<Position> jumpPosSet = jumpPositions.collect(Collectors.toSet());

    for (Position p : jumpPosSet) {
      int row = p.row();
      int col = p.column();
      int xer = c.x;
      int yer = c.y;
      Check checkJump = board.get(p.column()).get(p.row());
      if (checkJump.isEmpty()) {
        Position jumpedPosition = pos.findJumpedPosition(p);
        Check jumpedPositionCheck = board.get(jumpedPosition.column()).get(jumpedPosition.row());
        boolean empt = jumpedPositionCheck.isEmpty();
        if (!jumpedPositionCheck.isEmpty()) {
          Player q = jumpedPositionCheck.getPiece().player();
        }
        Player z = c.getPiece().player();
        boolean r = p.isAbove(jumpedPosition);

        if (!jumpedPositionCheck.isEmpty()) {
          if (jumpedPositionCheck.getPiece().player() != c.getPiece().player()) {
            if (c.getPiece().isCrowned()) {
              return true;
            } else if (!c.getPiece().isCrowned()) {
              if (p.isAbove(jumpedPosition) && c.getPiece().player() == Player.First) {
                return true;
              } else if (p.isBelow(jumpedPosition) && c.getPiece().player() == Player.Second) {
                return true;
              }
            }
          }
        }
      }
    }
    return false;
  }

  ArrayList<Check> moves (Check xy) {
    int x = xy.x;
    int y = xy.y;
    Position pos =  Position.fromRowColumn(x, y);
    Stream<Position> jumpPositions = pos.jumpAdjacentPositions();
    Stream<Position> adjacentPositions = pos.adjacentPositions();
    Set<Position> posSet = adjacentPositions.collect(Collectors.toSet());
    Set<Position> jumpPosSet = jumpPositions.collect(Collectors.toSet());
    ArrayList<Check> returnable= new ArrayList<>();


    if (xy.isEmpty()) {
      throw new IllegalArgumentException("an empty spot has no moves");
    }

    if (this.mustMove(x, y))
    {
      // for postions in the set of jumpable positions
      for(Position jumpTo: jumpPosSet) {
        Check jumpPositionCheck = this.getCheck(jumpTo.row(), jumpTo.column());

        //if the jump is empty, check the jumped piece and if it's the other player's, add that pos
        if(jumpPositionCheck.isEmpty()){
          Position jumpedPosition = pos.findJumpedPosition(jumpTo);
          Check jumpedPositionCheck = this.getCheck(jumpedPosition.row(), jumpedPosition.column());
          if (!jumpedPositionCheck.isEmpty()) {
            if (jumpedPositionCheck.getPiece().player() != xy.getPiece().player()) {
              if (xy.getPiece().isCrowned()) {
                returnable.add(jumpPositionCheck);
              } else if (!xy.getPiece().isCrowned()) {
                if (jumpTo.isAbove(jumpedPosition) && xy.getPiece().player() == Player.First) {
                  returnable.add(jumpPositionCheck);
                } else if (jumpTo.isBelow(jumpedPosition) && xy.getPiece().player() == Player.Second) {
                  returnable.add(jumpPositionCheck);
                }
              }
            }
          }
        }
      }
    }
    else if (this.hasMove(x, y))
    {
      for(Position moveTo: posSet) {
        Check movePositionCheck = new Check(moveTo.row(),moveTo.column());
        if (movePositionCheck.isEmpty()){
          if (((moveTo.isAbove(pos) && xy.getPiece().player() == Player.First) ||
               (moveTo.isBelow(pos) && xy.getPiece().player() == Player.Second)) ||
              xy.getPiece().isCrowned())
          {
            returnable.add(movePositionCheck);
          }
        }
      }
    }
    return returnable;
  }
}