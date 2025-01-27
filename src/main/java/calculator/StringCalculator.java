package calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {
  private static final String SEPERATOR = "[,:]";
  private static final Pattern CUSTOM_SEPERATOR_PATTERN = Pattern.compile("//(.)\n(.*)");

  public int calculateSum(String input) {
    if (input == null || input.isEmpty()) {
      throw new RuntimeException(ErrorMessage.NOT_NUMBER);
    }
    String[] inputs = split(input);
    return addAll(inputs);
  }

  private String[] split(String input) {
    Matcher matcher = CUSTOM_SEPERATOR_PATTERN.matcher(input);
    if (matcher.find()) {
      String customSeperator = Pattern.quote(matcher.group(1));
      String numberString = matcher.group(2);
      return numberString.split(customSeperator);
    }
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
