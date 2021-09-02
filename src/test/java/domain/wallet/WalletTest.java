package domain.wallet;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class WalletTest {
    @ValueSource(ints = {1000, 1, 0})
    @ParameterizedTest
    void constructor(final int amount) {
        assertDoesNotThrow(() -> new Wallet(amount));
    }

    @ValueSource(ints = {-1000, -1})
    @ParameterizedTest
    void constructor_wrong_amount(final int amount) {
        assertThatThrownBy(() -> new Wallet(amount))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
