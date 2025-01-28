package calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Input {
  private static final String SEPERATOR = "[,:]";
  private static final Pattern CUSTOM_SEPERATOR_PATTERN = Pattern.compile("//(.)\n(.*)");

  private final List<PositiveNumber> positiveNumbers = new ArrayList<>();

  public Input(String inputString) {
    String[] values = split(inputString);
    Arrays.stream(values).toList().forEach(value -> positiveNumbers.add(new PositiveNumber(value)));
  }

  public List<PositiveNumber> getPositiveNumbers() {
    return positiveNumbers;
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
}
