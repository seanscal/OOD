import java.util.ArrayList;

public class Board {

  ArrayList<ArrayList<Check>> board;
  int dimension;
  ArrayList<Piece> mustMove;

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
    Check c = getCheck(x, y);
    if (c.isEmpty()) {
      throw new IllegalArgumentException("an empty spot has no moves");
    }

    if (c.getPiece() == Piece.NormalFirst) {
      if (c.x > 0 && c.x < dimension && c.y > 0) {

        return (getCheck(x - 1, y - 1).isEmpty()) ||
               (getCheck(x + 1, y - 1).isEmpty());
      } else if (c.x == 0 && c.y > 0) {
        return (getCheck(x + 1, y - 1).isEmpty());
      } else if (c.x == dimension && c.y > 0) {
        return (getCheck(x - 1, y - 1).isEmpty());
      } else {
        return false;
      }
    } else if (c.getPiece() == Piece.CrownedFirst || c.getPiece() == Piece.CrownedSecond) {
      if (c.x > 0 && c.x < dimension && c.y > 0 && c.y < dimension) {
        return (getCheck(x - 1, y - 1).isEmpty()) ||
               (getCheck(x + 1, y - 1).isEmpty()) ||
               (getCheck(x - 1, y + 1).isEmpty()) ||
               (getCheck(x + 1, y + 1).isEmpty());
      } else if (c.x == 0 && c.y > 0 && c.y < dimension) {
        return (getCheck(x + 1, y - 1).isEmpty()) ||
               (getCheck(x + 1, y + 1).isEmpty());
      } else if (c.x == 0 && c.y == 0) {
        return (getCheck(x + 1, y + 1).isEmpty());
      } else if (c.x == dimension && c.y > 0 && c.y < dimension) {
        return (getCheck(x - 1, y - 1).isEmpty()) ||
               (getCheck(x - 1, y + 1).isEmpty());
      } else if (c.x == dimension && c.y == 0) {
        return (getCheck(x - 1, y + 1).isEmpty());
      } else if (c.x == dimension && c.y == dimension) {
        return (getCheck(x - 1, y - 1).isEmpty());
      } else {
        return false;
      }
    } else if (c.getPiece() == Piece.NormalSecond) {
      if (c.x > 0 && c.x < dimension && c.y < dimension) {
        return (getCheck(x - 1, y + 1).isEmpty()) ||
               (getCheck(x + 1, y + 1).isEmpty());
      } else if (c.x == 0 && c.y < dimension) {
        return (getCheck(x + 1, y + 1).isEmpty());
      } else if (c.x == dimension && c.y < dimension) {
        return (getCheck(x - 1, y + 1).isEmpty());
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  boolean mustMove(int x, int y) {
    Check c = getCheck(x, y);
    if (c.isEmpty()) {
      throw new IllegalArgumentException("an empty spot has no moves");
    }
   // Check upRight = getCheck(x - 1, y - 1);
   // Check upLeft = getCheck(x + 1, y - 1);
   // Check downRight = getCheck(x - 1, y + 1);
   // Check downLeft = getCheck(x + 1, y + 1);

   // Check jumpUpLeft = getCheck(x + 2, y + 2);
   // Check jumpUpRight = getCheck(x + 2, y - 2);
   // Check jumpDownRight = getCheck(x - 2, y + 2);
   // Check jumpDownLeft = getCheck(x + 2, y + 2);

    if (c.getPiece() == Piece.NormalFirst) {
      if ((c.y - 1) > 0) {
        if ((c.x - 1) > 0){
          Check upRight = getCheck(x - 1, y - 1);
          Check jumpUpRight = getCheck(x + 2, y - 2);

          if ((upRight.getPiece() == Piece.CrownedSecond ||
              upRight.getPiece() == Piece.NormalSecond) && jumpUpRight.isEmpty()){
            return true;
          }
        }
        if ((c.x + 1) < dimension) {
          Check upLeft = getCheck(x + 1, y - 1);
          Check jumpUpLeft = getCheck(x + 2, y + 2);

          if ((upLeft.getPiece() == Piece.CrownedSecond ||
               upLeft.getPiece() == Piece.NormalSecond) && jumpUpLeft.isEmpty()) {
            return true;
          }
        }
      }
    }

    if (c.getPiece() == Piece.CrownedFirst) {
      if ((c.y - 1) > 0) {
        if ((c.x - 1) > 0){
          Check upRight = getCheck(x - 1, y - 1);
          Check jumpUpRight = getCheck(x + 2, y - 2);

          if ((upRight.getPiece() == Piece.CrownedSecond ||
               upRight.getPiece() == Piece.NormalSecond) && jumpUpRight.isEmpty()){
            return true;
          }
        }
        if ((c.x + 1) < dimension) {
          Check upLeft = getCheck(x + 1, y - 1);
          Check jumpUpLeft = getCheck(x + 2, y + 2);

          if ((upLeft.getPiece() == Piece.CrownedSecond ||
               upLeft.getPiece() == Piece.NormalSecond) && jumpUpLeft.isEmpty()) {
            return true;
          }
        }
      }
      if ((c.y + 1) < dimension) {
        if ((c.x - 1) > 0){
          Check downRight = getCheck(x - 1, y + 1);
          Check jumpDownRight = getCheck(x + 2, y + 2);

          if ((downRight.getPiece() == Piece.CrownedSecond ||
               downRight.getPiece() == Piece.NormalSecond) && jumpDownRight.isEmpty()){
            return true;
          }
        }
        if ((c.x + 1) < dimension) {
          Check downLeft = getCheck(x + 1, y + 1);
          Check jumpDownLeft = getCheck(x + 2, y + 2);

          if ((downLeft.getPiece() == Piece.CrownedSecond ||
               downLeft.getPiece() == Piece.NormalSecond) && jumpDownLeft.isEmpty()) {
            return true;
          }
        }
      }
    }

    if (c.getPiece() == Piece.CrownedSecond) {
      if ((c.y - 1) > 0) {
        if ((c.x - 1) > 0){
          Check upRight = getCheck(x - 1, y - 1);
          Check jumpUpRight = getCheck(x + 2, y - 2);

          if ((upRight.getPiece() == Piece.CrownedFirst ||
               upRight.getPiece() == Piece.NormalFirst) && jumpUpRight.isEmpty()){
            return true;
          }
        }
        if ((c.x + 1) < dimension) {
          Check upLeft = getCheck(x + 1, y - 1);
          Check jumpUpLeft = getCheck(x + 2, y + 2);

          if ((upLeft.getPiece() == Piece.CrownedFirst ||
               upLeft.getPiece() == Piece.NormalFirst) && jumpUpLeft.isEmpty()) {
            return true;
          }
        }
      }
      if ((c.y + 1) < dimension) {
        if ((c.x - 1) > 0){
          Check downRight = getCheck(x - 1, y + 1);
          Check jumpDownRight = getCheck(x + 2, y + 2);

          if ((downRight.getPiece() == Piece.CrownedFirst ||
               downRight.getPiece() == Piece.NormalFirst) && jumpDownRight.isEmpty()){
            return true;
          }
        }
        if ((c.x + 1) < dimension) {
          Check downLeft = getCheck(x + 1, y + 1);
          Check jumpDownLeft = getCheck(x + 2, y + 2);

          if ((downLeft.getPiece() == Piece.CrownedFirst ||
               downLeft.getPiece() == Piece.NormalFirst) && jumpDownLeft.isEmpty()) {
            return true;
          }
        }
      }
    }

    if (c.getPiece() == Piece.NormalSecond) {
      if ((c.y - 1) > 0) {
        if ((c.x - 1) > 0){
          Check downRight = getCheck(x - 1, y + 1);
          Check jumpDownRight = getCheck(x + 2, y + 2);

          if ((downRight.getPiece() == Piece.CrownedFirst ||
               jumpDownRight.getPiece() == Piece.NormalFirst) && jumpDownRight.isEmpty()){
            return true;
          }
        }
        if ((c.x + 1) < dimension) {
          Check downLeft = getCheck(x + 1, y + 1);
          Check jumpDownLeft = getCheck(x + 2, y + 2);

          if ((downLeft.getPiece() == Piece.CrownedFirst ||
               downLeft.getPiece() == Piece.NormalFirst) && jumpDownLeft.isEmpty()) {
            return true;
          }
        }
      }
    }
    return false;
  }
}