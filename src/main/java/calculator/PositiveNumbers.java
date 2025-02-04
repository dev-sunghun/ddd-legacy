package calculator;

import java.util.List;

public class PositiveNumbers {

	private final List<PositiveNumber> positiveNumbers;

	public PositiveNumbers(List<PositiveNumber> positiveNumbers) {
		this.positiveNumbers = positiveNumbers;
	}

	public int getSum() {
		return this.positiveNumbers.stream().mapToInt(PositiveNumber::getValue).sum();
	}
}
