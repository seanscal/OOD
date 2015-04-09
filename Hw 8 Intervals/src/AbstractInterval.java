import java.lang.invoke.WrongMethodTypeException;
import java.util.Comparator;

public abstract class AbstractInterval<T> implements Interval {

  protected T lowerBound;
  protected T upperBound;
  protected Comparator<T> comparator;

  public AbstractInterval(T value) {
    lowerBound = value;
    upperBound = value;
    comparator = (Comparator<T>) Comparator.naturalOrder();
    if (value == null) {
      throw new NullPointerException("No values can be null");
    }
  }

  public AbstractInterval(T value, Comparator<T> comp) {
    lowerBound = value;
    upperBound = value;
    comparator = comp;
    if (value == null) {
      throw new NullPointerException("No values can be null");
    }
  }

  public AbstractInterval(T lower,T upper) {
    lowerBound = lower;
    upperBound = upper;
    comparator = (Comparator<T>) Comparator.naturalOrder();
    if (upper == null || lower == null) {
      throw new NullPointerException("No values can be null");
    }
    if (comparator.compare(lowerBound,upperBound) == 1){
      throw new IndexOutOfBoundsException("Lower Bound greater than upper");
    }
    if(lower.getClass() != upper.getClass()){
      throw new WrongMethodTypeException("not the same class");
    }
  }

  public AbstractInterval(T lower,T upper, Comparator<T> comp) {
    lowerBound = lower;
    upperBound = upper;
    comparator = comp;
    if (upper == null || lower == null || comp == null) {
      throw new NullPointerException("No values can be null");
    }
    if (comparator.compare(lowerBound,upperBound) == 1){
      throw new IndexOutOfBoundsException("Lower Bound greater than upper");
    }
    if(lower.getClass() != upper.getClass()){
      throw new WrongMethodTypeException("not the same class");
    }
  }

  @Override
  public T lowerBound() {
    return (T)lowerBound;
  }

  @Override
  public T upperBound() {
    return (T)upperBound;
  }

  @Override
  public boolean contains(Object value) {
    T val = (T) value;
    if (this.lowerBoundType() == BoundType.Closed){
      if (this.upperBoundType() == BoundType.Closed){
        switch (comparator.compare((T) this.lowerBound(), val)){
          case 1:
            return false;
          case 0:case -1:
            switch (comparator.compare((T) this.upperBound(), val)){
              case -1:
                return false;
              case 1:case 0:
                return true;
            }
        }
      }
      if (this.upperBoundType() == BoundType.Open){
        switch (comparator.compare((T) this.lowerBound(), val)){
          case 1:
            return false;
          case 0:case -1:
            switch (comparator.compare((T) this.upperBound(), val)){
              case -1:case 0:
                return false;
              case 1:
                return true;
            }
        }
      }
    }
    if (this.lowerBoundType() == BoundType.Open){
      if (this.upperBoundType() == BoundType.Closed){
        switch (comparator.compare((T) this.lowerBound(), val)){
          case 1:case 0:
            return false;
          case -1:
            switch (comparator.compare((T) this.upperBound(), val)){
              case -1:
                return false;
              case 1:case 0:
                return true;
            }
        }
      }
      if (this.upperBoundType() == BoundType.Open){
        switch (comparator.compare((T) this.lowerBound(), val)){
          case 1:case 0:
            return false;
          case -1:
            switch (comparator.compare((T) this.upperBound(), val)){
              case -1:case 0:
                return false;
              case 1:
                return true;
            }
        }
      }
    }
    return false;
  }


  public boolean includes(Interval other){
    return this.contains(other.lowerBound()) && this.contains(other.upperBound());
  }

  @Override
  public Interval intersection(Interval other) {

    if (other.isEmpty() == true){
      //return new OpenClosedInterval<>();
    }

    BoundType lowerType = this.lowerBoundType();
    BoundType upperType = this.upperBoundType();
    T upper = (T) this.upperBound();
    T lower = (T) this.lowerBound();

    if (lowerType == BoundType.Closed) {
      if (upperType == BoundType.Closed) {
        switch (comparator.compare(lower, (T) other.lowerBound())) {
          case 1:
            switch (comparator.compare(upper, (T) other.upperBound())) {
              case 1:
              case 0:
                return Intervals.interval(lower, lowerType, (T) other.upperBound()
                    , other.upperBoundType(), this.comparator);
              case -1:
                return Intervals.interval(lower, lowerType, upper, upperType, this.comparator);
            }
            break;
          case -1:
          case 0:
            switch (comparator.compare(upper, (T) other.upperBound())) {
              case 1:
              case 0:
                return Intervals.interval((T) other.lowerBound(), other.lowerBoundType(),
                                          (T) other.upperBound(), other.upperBoundType(),
                                          this.comparator);
              case -1:
                return Intervals.interval((T) other.lowerBound(), other.lowerBoundType(),
                                          upper, upperType, this.comparator);
            }
            break;
        }
      }
      if (upperType == BoundType.Open) {
        switch (comparator.compare(lower, (T) other.lowerBound())) {
          case 1:
            switch (comparator.compare(upper, (T) other.upperBound())) {
              case 1:
                return Intervals.interval(lower, lowerType, (T) other.upperBound()
                    , other.upperBoundType(), this.comparator);
              case -1:
              case 0:
                return Intervals.interval(lower, lowerType, upper, upperType, this.comparator);
            }
            break;
          case -1:
          case 0:
            switch (comparator.compare(upper, (T) other.upperBound())) {
              case 1:

                return Intervals.interval((T) other.lowerBound(), other.lowerBoundType(),
                                          (T) other.upperBound(), other.upperBoundType(),
                                          this.comparator);
              case 0:
              case -1:
                return Intervals.interval((T) other.lowerBound(), other.lowerBoundType(),
                                          upper, upperType, this.comparator);
            }
            break;
        }
      }
    }
    if (lowerType == BoundType.Open) {
      if (upperType == BoundType.Closed) {
        switch (comparator.compare(lower, (T) other.lowerBound())) {
          case 1:
          case 0:
            switch (comparator.compare(upper, (T) other.upperBound())) {
              case 1:
              case 0:
                return Intervals.interval(lower, lowerType, (T) other.upperBound()
                    , other.upperBoundType(), this.comparator);
              case -1:
                return Intervals.interval(lower, lowerType, upper, upperType, this.comparator);
            }
            break;
          case -1:
            switch (comparator.compare(upper, (T) other.upperBound())) {
              case 1:
              case 0:
                return Intervals.interval((T) other.lowerBound(), other.lowerBoundType(),
                                          (T) other.upperBound(), other.upperBoundType(),
                                          this.comparator);
              case -1:
                return Intervals.interval((T) other.lowerBound(), other.lowerBoundType(),
                                          upper, upperType, this.comparator);
            }
            break;
        }
      }
      if (upperType == BoundType.Open) {
        switch (comparator.compare(lower, (T) other.lowerBound())) {
          case 1:
          case 0:
            switch (comparator.compare(upper, (T) other.upperBound())) {
              case 1:
                return Intervals.interval(lower, lowerType, (T) other.upperBound()
                    , other.upperBoundType(), this.comparator);
              case -1:
              case 0:
                return Intervals.interval(lower, lowerType, upper, upperType, this.comparator);
            }
            break;
          case -1:
            switch (comparator.compare(upper, (T) other.upperBound())) {
              case 1:

                return Intervals.interval((T) other.lowerBound(), other.lowerBoundType(),
                                          (T) other.upperBound(), other.upperBoundType(),
                                          this.comparator);
              case 0:
              case -1:
                return Intervals.interval((T) other.lowerBound(), other.lowerBoundType(),
                                          upper, upperType, this.comparator);
            }
            break;
        }
      }
    }
    return null;
  }

  @Override
  public Interval span(Interval other) {

    BoundType lowerType = this.lowerBoundType();
    BoundType upperType = this.upperBoundType();
    T upper = (T) this.upperBound();
    T lower = (T) this.lowerBound();

    if (other.isEmpty() == true){
      return Intervals.interval(lower, lowerType, upper, upperType, this.comparator);
    }

    if (lowerType == BoundType.Closed) {
      if (upperType == BoundType.Closed) {
        switch (comparator.compare(lower, (T) other.lowerBound())) {
          case -1:
          case 0:
            switch (comparator.compare(upper, (T) other.upperBound())) {
              case -1:
                return Intervals.interval(lower, lowerType, (T) other.upperBound()
                    , other.upperBoundType(), this.comparator);
              case 1:
              case 0:
                return Intervals.interval(lower, lowerType, upper, upperType, this.comparator);
            }
            break;
          case 1:
            switch (comparator.compare(upper, (T) other.upperBound())) {
              case -1:
                return Intervals.interval((T) other.lowerBound(), other.lowerBoundType(),
                                          (T) other.upperBound(), other.upperBoundType(),
                                          this.comparator);
              case 1:
              case 0:
                return Intervals.interval((T) other.lowerBound(), other.lowerBoundType(),
                                          upper, upperType, this.comparator);
            }
            break;
        }
      }
      if (upperType == BoundType.Open) {
        switch (comparator.compare(lower, (T) other.lowerBound())) {
          case -1:
          case 0:
            switch (comparator.compare(upper, (T) other.upperBound())) {
              case -1:
              case 0:
                return Intervals.interval(lower, lowerType, (T) other.upperBound()
                    , other.upperBoundType(), this.comparator);
              case 1:
                return Intervals.interval(lower, lowerType, upper, upperType, this.comparator);
            }
            break;
          case 1:
            switch (comparator.compare(upper, (T) other.upperBound())) {
              case -1:
              case 0:
                return Intervals.interval((T) other.lowerBound(), other.lowerBoundType(),
                                          (T) other.upperBound(), other.upperBoundType(),
                                          this.comparator);
              case 1:
                return Intervals.interval((T) other.lowerBound(), other.lowerBoundType(),
                                          upper, upperType, this.comparator);
            }
            break;
        }
      }
    }
    if (lowerType == BoundType.Open) {
      if (upperType == BoundType.Closed) {
        switch (comparator.compare(lower, (T) other.lowerBound())) {
          case -1:
            switch (comparator.compare(upper, (T) other.upperBound())) {
              case -1:
                return Intervals.interval(lower, lowerType, (T) other.upperBound()
                    , other.upperBoundType(), this.comparator);
              case 1:
              case 0:
                return Intervals.interval(lower, lowerType, upper, upperType, this.comparator);
            }
            break;
          case 1:
          case 0:
            switch (comparator.compare(upper, (T) other.upperBound())) {
              case -1:
                return Intervals.interval((T) other.lowerBound(), other.lowerBoundType(),
                                          (T) other.upperBound(), other.upperBoundType(),
                                          this.comparator);
              case 1:
              case 0:
                return Intervals.interval((T) other.lowerBound(), other.lowerBoundType(),
                                          upper, upperType, this.comparator);
            }
            break;
        }
      }
      if (upperType == BoundType.Open) {
        switch (comparator.compare(lower, (T) other.lowerBound())) {
          case -1:
            switch (comparator.compare(upper, (T) other.upperBound())) {
              case -1:
              case 0:
                return Intervals.interval(lower, lowerType, (T) other.upperBound()
                    , other.upperBoundType(), this.comparator);
              case 1:
                return Intervals.interval(lower, lowerType, upper, upperType, this.comparator);
            }
            break;
          case 1:
          case 0:
            switch (comparator.compare(upper, (T) other.upperBound())) {
              case -1:
              case 0:
                return Intervals.interval((T) other.lowerBound(), other.lowerBoundType(),
                                          (T) other.upperBound(), other.upperBoundType(),
                                          this.comparator);
              case 1:
                return Intervals.interval((T) other.lowerBound(), other.lowerBoundType(),
                                          upper, upperType, this.comparator);
            }
            break;
        }
      }
    }
    return null;
  }

  @Override
  public Comparator<T> getComparator() {
    return comparator;
  }

  @Override
  public int hashCode() {
    int result = lowerBound != null ? lowerBound.hashCode() : 0;
    result = 31 * result + (upperBound != null ? upperBound.hashCode() : 0);
    result = 31 * result + (comparator != null ? comparator.hashCode() : 0);
    return result;
  }

  @Override
  public boolean equals(Object obj){
    Interval<T> equal = (Interval<T>) obj;

    if(this.getClass() != obj.getClass()){
      return false;
    }

    return this.lowerBound() == equal.lowerBound() &&
           this.lowerBoundType() == equal.lowerBoundType() &&
           this.upperBound() == equal.upperBound() &&
           this.upperBoundType() == equal.upperBoundType();
  }

  @Override
  public String toString() {
    String lowSym = "[";
    String upSym = "]";

    if (this.lowerBoundType() == BoundType.Open){
      lowSym = "(";
    }
    if (this.upperBoundType() == BoundType.Open){
      upSym = ")";
    }
    return "Interval is " + lowSym + this.lowerBound() + "," +this.upperBound()+upSym;
  }
}
