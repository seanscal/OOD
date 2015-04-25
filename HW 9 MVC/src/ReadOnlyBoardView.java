import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public final class ReadOnlyBoardView implements ReadOnlyBoardViewModel {
  Model model;
  ReadOnlyBoardView(Model m) {
    model = m;
  }

  @Override
  public String get(int row, int column, int width) {
    Check c = model.getCheckAt(row, column);

    for (Check check : model.movablePieces()){
      if (check.isSelected){
        int g = 0;
        Position p = Position.fromRowColumn(row, column);
        int count = 0;
        for(Position move : c.moves()){
          if (p == move){
            count++;
            return "[" + count + "]";
          }
        }
      }
    }

    if (c.isSelected) {
      return "<" + c.getText() + ">";
    }
    else if (model.movablePieces().contains(c)) {
      int moveNum = 1 + model.movablePieces().indexOf(c);
      if (c.getPiece().isCrowned()) {
        return "[[" + Integer.toString(moveNum) + "]]";
      } else {
        return "[" + Integer.toString(moveNum) + "]";
      }
    } else {
      return model.getCheckAt(row, column).getText();
    }
  }

  @Override
  public boolean isValidPosition(int row, int column) {
    return row >= 0 && row < model.size &&
           column >= 0 && column < model.size;
  }

  @Override
  public Iterator<Integer> rows() {
    Board b= new Board(8);
    Set<Integer> set = new TreeSet<Integer>();


    for (int x = 0; x < b.dimension; x++){
      set.add(x);
    }
    return set.iterator();
  }

  @Override
  public Iterator<Integer> columns() {
    Board b= new Board(8);
    Set<Integer> set = new TreeSet<Integer>();

    for (int x = 0; x < b.dimension; x++){
      set.add(x);
    }
    Iterator<Integer> f = set.iterator();
    return set.iterator();
  }
}