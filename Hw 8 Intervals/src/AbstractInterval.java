import java.lang.invoke.WrongMethodTypeException;
import java.util.Comparator;

public abstract class AbstractInterval<T> implements Interval {

  protected T lowerBound;
  protected T upperBound;
  protected Comparator<T> comparator;

  public AbstractInterval() {
    lowerBound = null;
    upperBound = null;
    comparator = (Comparator<T>) Comparator.naturalOrder();
  }

  public AbstractInterval(Comparator<T> comp) {
    lowerBound = null;
    upperBound = null;
    comparator = comp;
  }

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
    if (comparator.compare(lowerBound,upperBound) > 0){
      throw new IllegalArgumentException("Lower Bound greater than upper");
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
    if (comparator.compare(lowerBound,upperBound) > 0){
      throw new IllegalArgumentException("Lower Bound greater than upper");
    }
    if(lower.getClass() != upper.getClass()){
      throw new WrongMethodTypeException("not the same class");
    }
  }

  @Override
  public T lowerBound() {
    if (this.isEmpty()){
      throw new IllegalStateException("Set is empty");
    }
    return lowerBound;
  }

  @Override
  public T upperBound() {
    if (this.isEmpty()){
      throw new IllegalStateException("Set is empty");
    }
    return upperBound;
  }

  @Override
  public boolean contains(Object value) {
    T val = (T) value;
    if (this.lowerBoundType() == BoundType.Closed){
      if (this.upperBoundType() == BoundType.Closed){
        double dif = (comparator.compare((T) this.lowerBound(), val));
        if (comparator.compare((T) this.lowerBound(), val) >= 1) {
          return false;
        }
         else{
            if (comparator.compare((T) this.upperBound(), val)<= -1) {
              return false;
            }
            else{
                return true;
            }
        }
      }
      if (this.upperBoundType() == BoundType.Open){
        if (comparator.compare((T) this.lowerBound(), val)>=1) {
          return false;
        }
        else{
            if (comparator.compare((T) this.upperBound(), val)<=0) {
              return false;
            }
            else{
                return true;
            }
        }
      }
    }
    if (this.lowerBoundType() == BoundType.Open){
      if (this.upperBoundType() == BoundType.Closed){
        if (comparator.compare((T) this.lowerBound(), val)>=0) {
          return false;
        }
        else{
            if (comparator.compare((T) this.upperBound(), val)<=-1) {
              return false;
            }
            else{
                return true;
            }
        }
      }
      if (this.upperBoundType() == BoundType.Open){
        if (comparator.compare((T) this.lowerBound(), val)>=0) {
          return false;
        }
        else{
            if (comparator.compare((T) this.upperBound(), val)<=0) {
              return false;
            }
            else{
                return true;
            }
        }
      }
    }
    return false;
  }

  public boolean includes(Interval other){
    if (this.lowerBoundType() == BoundType.Closed) {
      if (this.upperBoundType() == BoundType.Closed) {
        return this.contains(other.lowerBound()) && this.contains(other.upperBound());
      }
      if (this.upperBoundType() == BoundType.Open){
        if (other.upperBoundType() == BoundType.Closed){
          return this.contains(other.lowerBound()) &&
                 comparator.compare(this.upperBound(),(T)other.upperBound()) >= 1;
        }
        if (other.upperBoundType() == BoundType.Open){
          return this.contains(other.lowerBound()) &&
                 comparator.compare(this.upperBound(),(T)other.upperBound()) >= 0;
        }
      }
    }

    if (this.lowerBoundType() == BoundType.Open) {
      if (this.upperBoundType() == BoundType.Closed) {
        if (other.lowerBoundType() == BoundType.Closed) {
          return comparator.compare(this.lowerBound(), (T) other.lowerBound()) <= -1
                 && this.contains(other.upperBound());
        }
        if (other.lowerBoundType() == BoundType.Open) {
          return comparator.compare(this.lowerBound(), (T) other.lowerBound()) < 1
                 && this.contains(other.upperBound());
        }
      }
      if (this.upperBoundType() == BoundType.Open){
        if (other.lowerBoundType() == BoundType.Closed) {
          if (other.upperBoundType() == BoundType.Closed) {
            return comparator.compare(this.lowerBound(), (T) other.lowerBound()) <= -1 &&
                   comparator.compare(this.upperBound(), (T) other.upperBound()) >= 1;
          }
          if (other.upperBoundType() == BoundType.Open) {
            return comparator.compare(this.lowerBound(), (T) other.lowerBound()) <= -1 &&
                   comparator.compare(this.upperBound(), (T) other.upperBound()) > -1;
          }
        }
        if (other.lowerBoundType() == BoundType.Open) {
          if (other.upperBoundType() == BoundType.Closed) {
            return comparator.compare(this.lowerBound(), (T) other.lowerBound()) < 1 &&
                   comparator.compare(this.upperBound(), (T) other.upperBound()) >= 1;
          }
          if (other.upperBoundType() == BoundType.Open) {
            return comparator.compare(this.lowerBound(), (T) other.lowerBound()) < 1 &&
                   comparator.compare(this.upperBound(), (T) other.upperBound()) > -1;
          }
        }
      }
    }
    return false;
  }

  @Override
  public Interval intersection(Interval other) {

    if (other.isEmpty()){
      return Intervals.empty();
    }

    BoundType lowerType = this.lowerBoundType();
    BoundType upperType = this.upperBoundType();
    T upper = (T) this.upperBound();
    T lower = (T) this.lowerBound();

    if ((comparator.compare(upper, (T) other.lowerBound()) < 1)){
      if (upperType == BoundType.Closed && other.upperBoundType() == BoundType.Closed){
        return Intervals.singleton(upper,this.comparator);
      }
      else{
        return Intervals.empty();
      }
    }

    if (lowerType == BoundType.Closed) {
      if (upperType == BoundType.Closed) {
        if (comparator.compare(lower, (T) other.lowerBound())>=1) {
          if (comparator.compare(upper, (T) other.upperBound()) >= 0) {
            return Intervals.interval(lower, lowerType, (T) other.upperBound()
                , other.upperBoundType(), this.comparator);
          } else {
            return Intervals.interval(lower, lowerType, upper, upperType, this.comparator);
          }
        }
        else{
          if (comparator.compare(upper, (T) other.upperBound())>=0) {
            return Intervals.interval((T) other.lowerBound(), other.lowerBoundType(),
                                      (T) other.upperBound(), other.upperBoundType(),
                                      this.comparator);
          }
          else{
                return Intervals.interval((T) other.lowerBound(), other.lowerBoundType(),
                                          upper, upperType, this.comparator);
            }
        }
      }
      if (upperType == BoundType.Open) {
        if (comparator.compare(lower, (T) other.lowerBound())>=1) {
          if (comparator.compare(upper, (T) other.upperBound()) >= 1) {
            return Intervals.interval(lower, lowerType, (T) other.upperBound()
                , other.upperBoundType(), this.comparator);
          } else {
            return Intervals.interval(lower, lowerType, upper, upperType, this.comparator);
          }
        }
        else{
          if (comparator.compare(upper, (T) other.upperBound())>=1) {
            return Intervals.interval((T) other.lowerBound(), other.lowerBoundType(),
                                      (T) other.upperBound(), other.upperBoundType(),
                                      this.comparator);
          }
          else{
            return Intervals.interval((T) other.lowerBound(), other.lowerBoundType(),
                                      upper, upperType, this.comparator);
          }
        }
      }
    }
    if (lowerType == BoundType.Open) {
      if (upperType == BoundType.Closed) {
        if (comparator.compare(lower, (T) other.lowerBound())>=0) {
          if (comparator.compare(upper, (T) other.upperBound()) >= 0) {
            return Intervals.interval(lower, lowerType, (T) other.upperBound()
                , other.upperBoundType(), this.comparator);
          } else {
            return Intervals.interval(lower, lowerType, upper, upperType, this.comparator);
          }
        }
        else {
          if (comparator.compare(upper, (T) other.upperBound()) >= 0) {
            return Intervals.interval((T) other.lowerBound(), other.lowerBoundType(),
                                      (T) other.upperBound(), other.upperBoundType(),
                                      this.comparator);
          } else {
            return Intervals.interval((T) other.lowerBound(), other.lowerBoundType(),
                                      upper, upperType, this.comparator);
          }
        }
      }
      if (upperType == BoundType.Open) {
        if (comparator.compare(lower, (T) other.lowerBound())>=0) {
          if (comparator.compare(upper, (T) other.upperBound()) >= 1) {
            return Intervals.interval(lower, lowerType, (T) other.upperBound()
                , other.upperBoundType(), this.comparator);
          } else {
            return Intervals.interval(lower, lowerType, upper, upperType, this.comparator);
          }
        }
        else{
          if (comparator.compare(upper, (T) other.upperBound())>=1) {
            return Intervals.interval((T) other.lowerBound(), other.lowerBoundType(),
                                      (T) other.upperBound(), other.upperBoundType(),
                                      this.comparator);
          }
          else{
            return Intervals.interval((T) other.lowerBound(), other.lowerBoundType(),
                                      upper, upperType, this.comparator);
          }
        }
      }
    }
    return Intervals.empty();
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
        if (comparator.compare(lower, (T) other.lowerBound())<=0) {
          if (comparator.compare(upper, (T) other.upperBound())<=-1) {
            return Intervals.interval(lower, lowerType, (T) other.upperBound()
                , other.upperBoundType(), this.comparator);
          }
          else{
              return Intervals.interval(lower, lowerType, upper, upperType, this.comparator);
          }
        }
        else{
          if (comparator.compare(upper, (T) other.upperBound())<=-1) {
            return Intervals.interval((T) other.lowerBound(), other.lowerBoundType(),
                                      (T) other.upperBound(), other.upperBoundType(),
                                      this.comparator);
          }
          else{
            return Intervals.interval((T) other.lowerBound(), other.lowerBoundType(),
                                      upper, upperType, this.comparator);
          }
        }
      }
      if (upperType == BoundType.Open) {
        if (comparator.compare(lower, (T) other.lowerBound())<=0) {
          if (comparator.compare(upper, (T) other.upperBound())<=0) {
            return Intervals.interval(lower, lowerType, (T) other.upperBound()
                , other.upperBoundType(), this.comparator);
          }
          else{
              return Intervals.interval(lower, lowerType, upper, upperType, this.comparator);
          }
        }
        else{
          if (comparator.compare(upper, (T) other.upperBound())<=0) {
            return Intervals.interval((T) other.lowerBound(), other.lowerBoundType(),
                                      (T) other.upperBound(), other.upperBoundType(),
                                      this.comparator);
          }
          else{
            return Intervals.interval((T) other.lowerBound(), other.lowerBoundType(),
                                      upper, upperType, this.comparator);
          }
        }
      }
    }
    if (lowerType == BoundType.Open) {
      if (upperType == BoundType.Closed) {
        if (comparator.compare(lower, (T) other.lowerBound())<=-1) {
          if (comparator.compare(upper, (T) other.upperBound())<=-1) {
            return Intervals.interval(lower, lowerType, (T) other.upperBound()
                , other.upperBoundType(), this.comparator);
          }
          else{
              return Intervals.interval(lower, lowerType, upper, upperType, this.comparator);
          }
        }
        else{
          if (comparator.compare(upper, (T) other.upperBound())<=-1) {
            return Intervals.interval((T) other.lowerBound(), other.lowerBoundType(),
                                      (T) other.upperBound(), other.upperBoundType(),
                                      this.comparator);
          }
          else{
            return Intervals.interval((T) other.lowerBound(), other.lowerBoundType(),
                                      upper, upperType, this.comparator);
          }
        }
      }
      if (upperType == BoundType.Open) {
        if (comparator.compare(lower, (T) other.lowerBound())<= -1) {
          if (comparator.compare(upper, (T) other.upperBound()) <= 0) {
            return Intervals.interval(lower, lowerType, (T) other.upperBound()
                , other.upperBoundType(), this.comparator);
          } else {
            return Intervals.interval(lower, lowerType, upper, upperType, this.comparator);
          }
        }
        else{
          if (comparator.compare(upper, (T) other.upperBound())<=0) {
            return Intervals.interval((T) other.lowerBound(), other.lowerBoundType(),
                                      (T) other.upperBound(), other.upperBoundType(),
                                      this.comparator);
          }
          else{
              return Intervals.interval((T) other.lowerBound(), other.lowerBoundType(),
                                        upper, upperType, this.comparator);
          }
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
