package calculator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PositiveNumberTest {

  @DisplayName("문자열이 숫자가 아니면 예외를 발생시킨다.")
  @CsvSource({"삼", "일", "이"})
  @ParameterizedTest
  void parseStringToNumberThrowString(String inputValue) {
    // when, then
    assertThatThrownBy(() -> new PositiveNumber(inputValue))
        .isInstanceOf(RuntimeException.class)
        .hasMessage(ErrorMessage.NOT_NUMBER);
  }

  @DisplayName("split된 문자열이 음수면 예외를 발생시킨다.")
  @CsvSource({"-1", "-10"})
  @ParameterizedTest
  void parseStringToNumberThrowNegativeNumber(String inputValue) {
    // when, then
    assertThatThrownBy(() -> new PositiveNumber(inputValue))
        .isInstanceOf(RuntimeException.class)
        .hasMessage(ErrorMessage.NEGATIVE_NUMBER);
  }
}
