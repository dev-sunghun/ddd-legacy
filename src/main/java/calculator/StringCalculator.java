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
      sum += Integer.parseInt(input);
    }
    return sum;
  }
}
