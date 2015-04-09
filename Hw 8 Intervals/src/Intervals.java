import java.util.Comparator;

public class Intervals<T> extends Object{

  static <T extends Comparable<T>> Interval<T> closed(T lower, T upper){
    return new ClosedInterval<T>(lower,upper);
  }

  static <T> Interval<T> closed(T lower, T upper, Comparator<T> comparator){
    return new ClosedInterval<T>(lower,upper,comparator);
  }

  static <T extends Comparable<T>> Interval<T> closedOpen(T lower, T upper){
    Comparator<T> comp = Comparator.naturalOrder();
    if (comp.compare(lower,upper) == 0){
      return new EmptyInterval<>();
    }
    return new ClosedOpenInterval<T>(lower,upper);
  }

  static <T> Interval<T> closedOpen(T lower, T upper, Comparator<T> comparator){
    Comparator<T> comp = (Comparator<T>) Comparator.naturalOrder();
    if (comp.compare(lower,upper) == 0){
      return new EmptyInterval<>(comparator);
    }
    return new ClosedOpenInterval<T>(lower,upper, comparator);
  }

  static <T extends Comparable<T>> Interval<T> empty(){
    return new EmptyInterval<>();
  }

  static <T extends Comparable<T>> Interval<T> empty(Comparator<T> comparator){
    return new EmptyInterval<>(comparator);
  }

  static <T extends Comparable<T>> Interval<T> interval(T lower, BoundType lowerType, T upper,
                                                        BoundType upperType){
    Interval<T> x = new ClosedInterval<T>(lower,upper);
    Comparator<T> comp = Comparator.naturalOrder();

    if (lowerType==BoundType.Closed){
      if(upperType == BoundType.Closed){
        return x;
      }
      else if(upperType==BoundType.Open){
        if (comp.compare(upper,lower)==0){
          x = new EmptyInterval<>();
        }
        else{
        x = new ClosedOpenInterval<T>(lower,upper);
        }
      }
    }
    else if (lowerType==BoundType.Open){
      if(upperType == BoundType.Closed) {
        if (comp.compare(upper, lower) == 0) {
          x = new EmptyInterval<>();
        }
        else {
          x = new OpenClosedInterval<T>(lower, upper);
        }
      }
      else if(upperType==BoundType.Open){
        x = new OpenInterval<T>(lower,upper);
      }
    }
    return x;
  }

  static <T> Interval<T> interval(T lower, BoundType lowerType, T upper, BoundType upperType,
                                  Comparator<T> comparator){

    Interval<T> x = new ClosedInterval<T>(lower,upper, comparator);
    Comparator<T> comp = (Comparator<T>) Comparator.naturalOrder();

    if (lowerType==BoundType.Closed){
      if(upperType == BoundType.Closed){
        return x;
      }
      else if(upperType==BoundType.Open){
        if (comp.compare(upper, lower) == 0) {
          x = new EmptyInterval<>(comparator);
        }
        else {
          x = new ClosedOpenInterval<T>(lower, upper, comparator);
        }
      }
    }
    else if (lowerType==BoundType.Open){
      if(upperType == BoundType.Closed){
        if (comp.compare(upper, lower) == 0) {
          x = new EmptyInterval<>(comparator);
        }
        else {
          x = new OpenClosedInterval<T>(lower, upper,comparator);
        }
        x = new OpenClosedInterval<T>(lower,upper,comparator);
      }
      else if(upperType==BoundType.Open){
        x = new OpenInterval<T>(lower,upper,comparator);
      }
    }
    return x;
  }

  static <T extends Comparable<T>> Interval<T> open(T lower, T upper){
    return new OpenInterval<T>(lower,upper);
  }

  static <T> Interval<T> open(T lower, T upper, Comparator<T> comparator){
    return new OpenInterval<T>(lower,upper,comparator);
  }

  static <T extends Comparable<T>> Interval<T> openClosed(T lower, T upper){
    Comparator<T> comp = Comparator.naturalOrder();
    if (comp.compare(lower,upper) == 0){
      return new EmptyInterval<>();
    }
    return new OpenClosedInterval<T>(lower,upper);
  }

  static <T> Interval<T> openClosed(T lower, T upper, Comparator<T> comparator){
    Comparator<T> comp = (Comparator<T>) Comparator.naturalOrder();
    if (comp.compare(lower,upper) == 0){
      return new EmptyInterval<>(comparator);
    }
    return new OpenClosedInterval<T>(lower,upper,comparator);
  }

  static <T extends Comparable<T>> Interval<T> singleton(T value){
    return new ClosedInterval<T>(value);
  }

  static <T> Interval<T> singleton(T value, Comparator<T> comparator){
    return new ClosedInterval<T>(value,comparator);
  }
}
