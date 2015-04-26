import java.util.ArrayList;

public class Util {

  Util() {
  }

  public static boolean placePiece(int y, int x) {
    if (((y % 2) == 0) && ((x % 2) == 1)) {
      return true;
    } else if (((y % 2) == 1) && ((x % 2) == 0)) {
      return true;
    } else {
      return false;
    }
  }

  public static ArrayList<Check> reverse(ArrayList<Check> list) {
    ArrayList<Check> returnable = new ArrayList<>();
    for(int i = list.size() - 1; i >= 0; i--) {
      returnable.add(list.get(i));
    }
    return returnable;
  }
  /*
  public static boolean inCheckList(ArrayList<Check> list, Check c) {
    boolean answer = false;
    for(int i = 0; i < list.size(); i++) {
      if(list.get(i).equals(c)) {
        answer = true;
      }
    }
    return answer;
  }
  */

}