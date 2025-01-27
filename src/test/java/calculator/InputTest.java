package calculator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class InputTest {

  @DisplayName("문자열을 ',', ':' 구분자로 split할 수 있다.")
  @Test
  void split() {
    // given
    String inputValue = "1,1:2";
    final Input input = new Input(inputValue);

    // when
    String[] inputValues = input.getValues();

    // then
    assertThat(inputValues.length).isEqualTo(3);
    assertThat(inputValues[0]).isEqualTo("1");
    assertThat(inputValues[1]).isEqualTo("1");
    assertThat(inputValues[2]).isEqualTo("2");
  }

  @DisplayName("문자열을 커스텀 구분자로 split할 수 있다.")
  @CsvSource({"'//;\n1;2;3'", "'//!\n1!2!3'", "'//@\n1@2@3'"})
  @ParameterizedTest
  void splitCustom(String inputValue) {
    // given
    final Input input = new Input(inputValue);

    // when
    String[] inputValues = input.getValues();

    // then
    assertThat(inputValues.length).isEqualTo(3);
    assertThat(inputValues[0]).isEqualTo("1");
    assertThat(inputValues[1]).isEqualTo("2");
    assertThat(inputValues[2]).isEqualTo("3");
  }
}
