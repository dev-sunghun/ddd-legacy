package calculator;

public class StringCalculator {
  public int calculateSum(Input input) {
    return input.getPositiveNumbers().stream().mapToInt(PositiveNumber::getValue).sum();
  }
}
