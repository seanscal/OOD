import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * represents one space on a checkers board
 */

public class Check {
  int x;
  int y;
  Piece piece;
  boolean isEmpty;

  //constructs an occupied check which cannot be empty
  Check(Piece p, int xx, int yy) {
    x = xx;
    y = yy;
    piece = p;
    isEmpty = false;
  }

  //constructs an empty check which cannot be occupied
  Check(int xx, int yy) {
    x = xx;
    y = yy;
    piece = null;
    isEmpty = true;
  }

  /**
   * Determines if this check is empty
   * @return boolean true if this check is empty
   */
  boolean isEmpty() {
    return isEmpty;
  }

  /**
   * @return the piece on this check
   */
  Piece getPiece() {
    return piece;
  }

  /**
   * overrides equality for checks
   * @param o the object this check is compared to
   * @return boolean true if they are equal
   */
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

  /**
   * overrides the hashCode for this check
   * @return the hashcode for this check
   */
  @Override
  public int hashCode() {
    return this.x * 31 + this.y * 13;
  }

  /**
   * gives the string representation of the piece or empty space of this check
   * @return string representation
   */
  public String getText() {
    if (isEmpty) {
      return "";
    } else {
      return piece.toString();
    }
  }
}