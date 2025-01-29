package calculator;

import java.util.ArrayList;
import java.util.List;

public class PositiveNumbers {
  private final List<PositiveNumber> positiveNumbers = new ArrayList<>();

  public void add(PositiveNumber positiveNumber) {
    positiveNumbers.add(positiveNumber);
  }

  public List<PositiveNumber> get() {
    return positiveNumbers;
  }
}
