package calculator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StringCalculatorTest {

  @DisplayName("문자열을 ',', ':' 구분자로 split할 수 있다.")
  @Test
  void split() {
    // given
    String input = "1,1:2";
    StringCalculator stringCalculator = new StringCalculator();

    // when
    String[] result = stringCalculator.split(input);

    // then
    assertThat(result.length).isEqualTo(3);
    assertThat(result[0]).isEqualTo("1");
    assertThat(result[1]).isEqualTo("1");
    assertThat(result[2]).isEqualTo("2");
  }
}
