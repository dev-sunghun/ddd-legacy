package calculator;

public class StringCalculator {
  private static final String SEPERATOR = "[,:]";

  public String[] split(String input) {
    return input.split(SEPERATOR);
  }

  public int add(String[] inputs) {
    int sum = 0;
    for (String input : inputs) {
      sum += Integer.parseInt(input);
    }
    return sum;
  }
}
