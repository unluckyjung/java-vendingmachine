package vendingmachine.domain.wallet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    void deduct() {
        final Wallet wallet = new Wallet(1000);
        wallet.deduct(900);
        assertThat(wallet.getAmount()).isEqualTo(100);
    }

    @Test
    void deduct_excess_amount() {
        final Wallet wallet = new Wallet(1000);
        assertThatThrownBy(() -> wallet.deduct(1500))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
