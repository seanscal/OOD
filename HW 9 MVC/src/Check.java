public class Check {
  int x;
  int y;
  Piece piece;
  boolean isEmpty;

  Check(Piece p, int xx, int yy) {
    piece = p;
    x = xx;
    y = yy;
    isEmpty = false;
  }

  Check(int xx, int yy) {
    piece = null;
    x = xx;
    y = yy;
    isEmpty = true;
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
    int result = x;
    result = 31 * result + y;
    result = 31 * result + (piece != null ? piece.hashCode() : 0);
    result = 31 * result + (isEmpty ? 1 : 0);
    return result;
  }

  public String getText() {
    if (isEmpty) {
      return "";
    } else {
      return piece.toString();
    }
  }
}