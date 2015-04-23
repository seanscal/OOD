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

  public static boolean inCheckList(ArrayList<Check> list, Check c) {
    boolean answer = false;
    for(int i = 0; i < list.size(); i++) {
      if(list.get(0).equals(c)) {
        answer = true;
      }
    }
    return answer;
  }

}