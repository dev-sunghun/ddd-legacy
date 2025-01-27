package calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Input {
  private static final String SEPERATOR = "[,:]";
  private static final Pattern CUSTOM_SEPERATOR_PATTERN = Pattern.compile("//(.)\n(.*)");

  private final String[] values;

  public Input(String inputString) {
    this.values = split(inputString);
  }

  public String[] getValues() {
    return values;
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
