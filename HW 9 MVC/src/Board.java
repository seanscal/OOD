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
    for (int y = 0; y < dimension; y++) {
      ArrayList<Check> row = new ArrayList<Check>();
      for (int x = 0; x < dimension; x++) {
        if ((y < (dimension - placeoffset)) && Util.placePiece(y, x)) {
          row.add(new Check(Piece.NormalSecond, x, y));
        } else if ((y >= placeoffset) && Util.placePiece(y, x)) {
          row.add(new Check(Piece.NormalFirst, x, y));
        } else {
          row.add(new Check(x, y));
        }
      }
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
      board.get(y).set(x, new Check(p, x, y));
      return new Check(p, x, y);
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
          Piece pi = getCheck(x, y).getPiece();
          if (pi == Piece.CrownedFirst || pi == Piece.NormalFirst) {
            temp.add(getCheck(x, y));
          }
        }
      }
    } else if (p == Player.Second) {
      for (int y = 0; y < dimension; y++) {
        for (int x = 0; x < dimension; x++) {
          Piece pi = getCheck(x, y).getPiece();
          if (pi == Piece.CrownedSecond || pi == Piece.NormalSecond) {
            temp.add(getCheck(x, y));
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
      Check movePositionCheck = new Check(moveTo.row(),moveTo.column());
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
      Check checkJump = new Check(p.row(), p.column());
      if (checkJump.isEmpty()) {
        Position jumpedPosition = pos.findJumpedPosition(p);
        Check jumpedPositionCheck = new Check(jumpedPosition.row(), jumpedPosition.column());

        if (!jumpedPositionCheck.isEmpty() &&
            jumpedPositionCheck.getPiece().player() != c.getPiece().player() &&
            (c.getPiece().isCrowned() ||
             ((!c.getPiece().isCrowned() &&
               ((p.isAbove(jumpedPosition) && c.getPiece().player() == Player.First) ||
                (p.isBelow(jumpedPosition) && c.getPiece().player() == Player.Second)))))) {
          return true;
        }
      }
    }
    return false;
  }

  ArrayList<Position> moves (Check xy) {
     int x = xy.x;
     int y = xy.y;
     Position pos =  Position.fromRowColumn(x, y);
     Stream<Position> jumpPositions = pos.jumpAdjacentPositions();
     Stream<Position> adjacentPositions = pos.adjacentPositions();
     Set<Position> posSet = adjacentPositions.collect(Collectors.toSet());
     Set<Position> jumpPosSet = jumpPositions.collect(Collectors.toSet());
     ArrayList<Position> returnable= new ArrayList<>();


     if (xy.isEmpty()) {
       throw new IllegalArgumentException("an empty spot has no moves");
     }

     if (this.mustMove(x, y))
     {
       // for postions in the set of jumpable positions
       for(Position jumpTo: jumpPosSet) {
         Check jumpPositionCheck = new Check(jumpTo.row(),jumpTo.column());

         //if the jump is empty, check the jumped piece and if it's the other player's, add that pos
         if(jumpPositionCheck.isEmpty()){
           Position jumpedPosition = pos.findJumpedPosition(jumpTo);
           Check jumpedPositionCheck = new Check(jumpedPosition.row(), jumpedPosition.column());
           if (!jumpedPositionCheck.isEmpty() &&
               jumpedPositionCheck.getPiece().player() != xy.getPiece().player()&&
               (xy.getPiece().isCrowned() ||
                (!xy.getPiece().isCrowned() &&
                 ((jumpTo.isAbove(jumpedPosition) && xy.getPiece().player() == Player.First) ||
                 (jumpTo.isBelow(jumpedPosition) && xy.getPiece().player() == Player.Second))))){
             returnable.add(jumpTo);
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
             returnable.add(moveTo);
           }
         }
       }
     }
     return returnable;
   }
}