package calculator;

public class StringCalculator {
  public int calculateSum(Input input) {
    if(input == null) {
      return 0;
    }
    return input.getPositiveNumbers().stream().mapToInt(PositiveNumber::getValue).sum();
  }
}
