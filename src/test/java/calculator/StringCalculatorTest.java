package calculator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class StringCalculatorTest {

  private final StringCalculator STRING_CALCULATOR = new StringCalculator();

  @DisplayName("구분자가 포함된 문자열로 총 합을 구할 수 있다.")
  @Test
  void calculateSum() {
    // given
    String input = "1,1:2";
    // when
    int sum = STRING_CALCULATOR.calculateSum(input);

    // then
    assertThat(sum).isEqualTo(4);
  }

  @DisplayName("문자열을 ',', ':' 구분자로 split할 수 있다.")
  @Test
  void split() {
    // given
    String input = "1,1:2";

    // when
    String[] result = ReflectionTestUtils.invokeMethod(STRING_CALCULATOR, "split", input);

    // then
    assertThat(result.length).isEqualTo(3);
    assertThat(result[0]).isEqualTo("1");
    assertThat(result[1]).isEqualTo("1");
    assertThat(result[2]).isEqualTo("2");
  }

  @DisplayName("split된 문자열을 숫자로 변환, 합을 계산할 수 있다.")
  @Test
  void addAll() {
    // given
    String[] inputs = {"1", "1", "2"};

    // when
    int sum = ReflectionTestUtils.invokeMethod(STRING_CALCULATOR, "addAll", (Object) inputs);

    // then
    assertThat(sum).isEqualTo(4);
  }
}
