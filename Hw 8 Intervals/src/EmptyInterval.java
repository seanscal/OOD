import java.util.Comparator;

public class EmptyInterval<T> extends AbstractInterval{

  public EmptyInterval(){
    super();
  }
  public EmptyInterval(Comparator<T> comp){
    super(comp);
  }

  @Override
  public boolean isEmpty() {
    return true;
  }

  @Override
  public BoundType lowerBoundType() {
    return BoundType.Closed;
  }

  @Override
  public BoundType upperBoundType() {
    return BoundType.Open;
  }


}
