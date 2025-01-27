package calculator;

public class StringCalculator {
  private static final String SEPERATOR = "[,:]";

  public String[] split(String input) {
    return input.split(SEPERATOR);
  }
}
