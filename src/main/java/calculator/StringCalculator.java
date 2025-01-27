package calculator;

import java.util.Arrays;

public class StringCalculator {
  public int calculateSum(Input input) {
    return Arrays.stream(
            Arrays.stream(input.getValues()).mapToInt(this::parseStringToNumber).toArray())
        .sum();
  }

  private int parseStringToNumber(String input) {
    try {
      int number = Integer.parseInt(input);
      throwExceptionIfNegativeNumber(number);
      return number;
    } catch (NumberFormatException numberFormatException) {
      throw new RuntimeException(ErrorMessage.NOT_NUMBER);
    }
  }

  private void throwExceptionIfNegativeNumber(int number) {
    if (number < 0) {
      throw new RuntimeException(ErrorMessage.NEGATIVE_NUMBER);
    }
  }
}
