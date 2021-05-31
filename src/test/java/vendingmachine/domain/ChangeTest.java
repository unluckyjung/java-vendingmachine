package vendingmachine.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ChangeTest {

    private final static int DEFAULT_VALUE = 500;
    private Change change;

    @BeforeEach
    void setUp() {
        change = new Change(DEFAULT_VALUE);
    }

    @DisplayName("잘못된 값으로 잔돈을 만드려고 하면, 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {-1})
    void InvalidateAmountTest(final int amount) {
        assertThatThrownBy(() -> {
            new Change(amount);
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("잔돈은 음수가 될 수 없습니다.");
    }

    @DisplayName("잔돈의 금액을 확인하면, 올바른 금액이 나온다.")
    @ParameterizedTest
    @ValueSource(ints = {100, 200, 300, 400})
    void changeGetAmountTest(final int amount) {
        Change change = new Change(amount);
        assertThat(change.getAmount()).isEqualTo(amount);
    }

    @DisplayName("잔돈의 금액을 차감하면, 금액이 차감된다.")
    @ParameterizedTest
    @ValueSource(ints = {10, 49, 50, 111})
    void deductChange(final int deductAmount) {
        assertThat(change.reduce(deductAmount)).isEqualTo(new Change(DEFAULT_VALUE - deductAmount));
    }

    @DisplayName("잔돈의 금액을 증액하면, 금액이 증가한다.")
    @ParameterizedTest
    @ValueSource(ints = {10, 49, 50, 111})
    void increaseChange(final int increaseAmount) {
        assertThat(change.increase(increaseAmount)).isEqualTo(new Change(DEFAULT_VALUE + increaseAmount));
    }
}
