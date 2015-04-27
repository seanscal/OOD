import java.util.ArrayList;
import java.util.Comparator;

public class Util {

  Util() {
  }

  /**
  determines if the given check can have a piece on it, used to generate board

  @param y the column coordinate
  @param x the row coordinate
  @returns if it is a valid place
   */
  public static boolean placePiece(int y, int x) {
    if (((y % 2) == 0) && ((x % 2) == 1)) {
      return true;
    } else if (((y % 2) == 1) && ((x % 2) == 0)) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * reverses the given arrayList of Checks, used to properly show Player 2 pieces
   *
   * @param list the list to be reversed
   * @return the reversed list
   */
  public static ArrayList<Check> reverse(ArrayList<Check> list) {
    ArrayList<Check> returnable = new ArrayList<>();
    for(int i = list.size() - 1; i >= 0; i--) {
      returnable.add(list.get(i));
    }
    return returnable;
  }

  /**
   * sorts the given ArrayList of Checks using a comparator for Checks by position
   * @param list the list to be sorted
   * @return the sorted list
   */
  public static ArrayList<Check> sort(ArrayList<Check> list) {
    list.sort(new Comparator<Check>() {
      @Override
      public int compare(Check o1, Check o2) {
        int y1 = o1.x;
        int y2 = o2.x;
        int x1 = o1.y;
        int x2 = o2.y;
        if(y1 < y2) {
          return -1;
        } else if(y1 > y2) {
          return 1;
        } else if(y1 == y2 && x1 > x2) {
          return 1;
        } else if(y1 == y2 && x1 < x2) {
          return -1;
        } else {
          return 0;
        }
      }
    });
    return list;
  }
}