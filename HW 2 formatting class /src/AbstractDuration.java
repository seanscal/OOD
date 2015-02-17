import java.util.HashMap;
import java.util.Map;
/**
 * Provides functionality common to both representational subclasses,
 * including comparisons, hashing, addition, subtraction, and the ability
 * to construct new durations of the same class as a given instance.
 */
abstract class AbstractDuration implements Duration {
  protected static final int SECS_IN_DAY = 24 * 60 * 60;

  /**
   * Constructs a new duration having the same class as {@code this}.
   *
   * @param seconds length of the new duration in seconds (non-negative)
   * @return the new duration
   * @throws IllegalArgumentException {@code seconds} is negative
   */
  protected abstract AbstractDuration fromSeconds(long seconds);

  /**
   * Constructs a new duration having the same class as {@code this}.
   *
   * @param days the days component of the new duration (non-negative)
   * @param hours the hours component of the new duration (non-negative)
   * @param minutes the minutes component of the new duration (non-negative)
   * @param seconds the seconds component of the new duration (non-negative)
   * @return the new duration
   * @throws IllegalArgumentException if any argument is negative
   */
  protected abstract AbstractDuration fromDHMS(long days, int hours,
                                               int minutes, int seconds);

  /**
   * Formats a duration as a string, based on the template. The template
   * is a string that may contain both uninterpreted characters and
   * <i>format specifiers</i>, which are special two-character codes
   * starting with a {@code %} character. This method returns a copy of
   * the template string in which the format specifiers are replaced by
   * representations of the indicated value for {@code this} duration,
   * and all other characters are copied into the result as-is.
   *
   * <p>In cases where multiple format specifiers overlap (<i>e.g.,</i>
   * {@code "%%t"}), the leftmost specifiers take precedence (so the
   * result would be {@code "%t"}).
   *
   * <table>
   *   <thead>
   *     <tr>
   *       <th>Format Specifier</th>
   *       <th>Meaning</th>
   *     </tr>
   *   </thead>
   *   <tr><td>{@code %t}</td><td>the whole duration in seconds</td></tr>
   *   <tr><td>{@code %d}</td><td>the days component of the decomposed
   *     duration</td></tr>
   *   <tr><td>{@code %h}</td><td>the hours component of the decomposed
   *     duration</td></tr>
   *   <tr><td>{@code %H}</td><td>the hours component of the decomposed
   *     duration, padded to 2 digits with leading zeros (<i>e.g.</i>,
   *     {@code 05} or {@code 11})</td></tr>
   *   <tr><td>{@code %m}</td><td>the minutes component of the decomposed
   *     duration</td></tr>
   *   <tr><td>{@code %M}</td><td>the minutes component of the decomposed
   *     duration, padded to 2 digits with leading zeros (<i>e.g.</i>,
   *     {@code 05} or {@code 56})</td></tr>
   *   <tr><td>{@code %s}</td><td>the seconds component of the decomposed
   *     duration</td></tr>
   *   <tr><td>{@code %S}</td><td>the seconds component of the decomposed
   *     duration, padded to 2 digits with leading zeros (<i>e.g.</i>,
   *     {@code 05} or {@code 56})</td></tr>
   *   <tr><td>{@code %%}</td><td>a literal percent sign ({@code %})</td></tr>
   *   <caption>Format specifiers</caption>
   * </table>
   *
   * @param template the template
   * @return the formatted string
   */
  @Override
  public String format(String template)
  {
    StringBuilder formatted = new StringBuilder(template);

    Map<Character, Object> options = new HashMap<Character, Object>();

    options.put('t', inSeconds());
    options.put('d', getDaysComponent());
    options.put('h', getHoursComponent());
    options.put('m', getMinutesComponent());
    options.put('s', getSecondsComponent());
    options.put('%', '%');

    if (getHoursComponent() < 10)
      options.put('H', "0" + getHoursComponent());
    else
      options.put('H', getHoursComponent());

    if (getMinutesComponent() < 10)
      options.put('M', "0" + getMinutesComponent());
    else
      options.put('M', getMinutesComponent());

    if (getSecondsComponent() < 10)
      options.put('S', "0" + getSecondsComponent());
    else
      options.put('S', getSecondsComponent());

    for (int x = 0; x < formatted.toString().length()-1; x++)
  {
    char detectPercent = formatted.toString().charAt(x);
    char detectFormat = formatted.toString().charAt(x + 1);
    if (detectPercent == '%')
    {
      for (Map.Entry<Character, Object> entry : options.entrySet())
      {
        if (entry.getKey() == detectFormat)
        {
          formatted.delete(x, x + 2);
          formatted.insert(x, entry.getValue());
        }
      }
    }
  }
    return formatted.toString();
  }

  /**
   * Returns the sum of two durations. The result will have the same dynamic
   * class as {@code this}.
   *
   * @param other the duration to add to {@code this}
   * @return the sum of the durations
   */
  @Override
  public Duration plus(Duration other) {
    long result = inSeconds() + other.inSeconds();

    if (result < 0) {
      throw new RuntimeException("Duration overflow");
    }

    return fromSeconds(result);
  }

  /**
   * Returns the difference of two durations. Returns the zero duration rather
   * than negative. The result will have the same dynamic class as
   * {@code this}.
   *
   * @param other the duration to subtract from {@code this}
   * @return the difference of the durations
   */
  @Override
  public Duration minus(Duration other) {
    long result = inSeconds() - other.inSeconds();
    return fromSeconds(result < 0 ? 0 : result);
  }

  @Override
  public int compareTo(Duration other) {
    return Long.compare(inSeconds(), other.inSeconds());
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Duration)) return false;
    return inSeconds() == ((Duration) other).inSeconds();
  }

  @Override
  public int hashCode() {
    return (int) (inSeconds() ^ (inSeconds() >>> 32));
  }
}
