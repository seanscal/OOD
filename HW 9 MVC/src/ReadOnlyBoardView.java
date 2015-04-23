import java.util.Iterator;

public final class ReadOnlyBoardView implements ReadOnlyBoardViewModel {
  Model model;
  ReadOnlyBoardView(Model m) {
    model = m;
  }

  @Override
  public String get(int row, int column, int width) {
    return model.getCheckAt(row, column).getText();
  }

  @Override
  public boolean isValidPosition(int row, int column) {
    return row >= 0 && row < model.size &&
           column >= 0 && column < model.size;
  }

  @Override
  public Iterator<Integer> rows() {
    return null;
  }

  @Override
  public Iterator<Integer> columns() {
    return null;
  }
}