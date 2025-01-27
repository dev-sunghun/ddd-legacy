package calculator;

public class StringCalculator {
  private static final String SEPERATOR = "[,:]";

  public int calculateSum(String input) {
    if (input == null || input.isEmpty()) {
      return 0;
    }
    String[] inputs = split(input);
    return addAll(inputs);
  }

  private String[] split(String input) {
    return input.split(SEPERATOR);
  }

  private int addAll(String[] inputs) {
    int sum = 0;
    for (String input : inputs) {
      sum += parseStringToNumber(input);
    }
    return sum;
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
