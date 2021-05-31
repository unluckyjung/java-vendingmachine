package vendingmachine.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ChangeTest {

    @DisplayName("잘못된 값으로 잔돈을 만드려고 하면, 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {-1})
    void InvalidateAmountTest(final int amount) {
        assertThatThrownBy(() -> {
            new Change(amount);
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("잔돈은 음수가 될 수 없습니다.");
    }
}
