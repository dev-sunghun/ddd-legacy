package calculator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.test.util.ReflectionTestUtils;

class StringCalculatorTest {

  private final StringCalculator STRING_CALCULATOR = new StringCalculator();

  @DisplayName("구분자가 포함된 문자열로 총 합을 구할 수 있다.")
  @CsvSource({"'1,1:2', 4", "'10,1,2', 13", "'1:1:2', 4", "100, 100"})
  @ParameterizedTest
  void calculateSum(String input, int expected) {
    // when
    int sum = STRING_CALCULATOR.calculateSum(input);

    // then
    assertThat(sum).isEqualTo(expected);
  }

  @DisplayName("숫자가 아니거나, 음수인 문자열을 입력하면 예외를 발생시킨다.")
  @CsvSource({"삼", "-1", "abc", "!2"})
  @ParameterizedTest
  void calculateSumException(String input) {
    // when, then
    assertThatThrownBy(() -> STRING_CALCULATOR.calculateSum(input))
        .isInstanceOf(RuntimeException.class)
        .satisfies(
            e ->
                assertThat(e.getMessage())
                    .matches(
                        msg ->
                            msg.equals(ErrorMessage.NOT_NUMBER)
                                || msg.equals(ErrorMessage.NEGATIVE_NUMBER)));
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

  @DisplayName("split된 문자열이 숫자가 아니면 예외를 발생시킨다.")
  @Test
  void addAllThrowString() {
    // given
    String[] inputs = {"삼", "일", "이"};

    // when, then
    assertThatThrownBy(
            () -> ReflectionTestUtils.invokeMethod(STRING_CALCULATOR, "addAll", (Object) inputs))
        .isInstanceOf(RuntimeException.class)
        .hasMessage(ErrorMessage.NOT_NUMBER);
  }

  @DisplayName("split된 문자열이 음수면 예외를 발생시킨다.")
  @Test
  void addAllThrowNegativeNumber() {
    // given
    String[] inputs = {"-1", "1", "2"};

    // when, then
    assertThatThrownBy(
            () -> ReflectionTestUtils.invokeMethod(STRING_CALCULATOR, "addAll", (Object) inputs))
        .isInstanceOf(RuntimeException.class)
        .hasMessage(ErrorMessage.NEGATIVE_NUMBER);
  }
}
