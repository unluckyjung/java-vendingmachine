package domain.product;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ProductTest {
    @ValueSource(ints = {101, 90, 99, 9, 0, -1})
    @ParameterizedTest
    void constructor_wrong_price(final int price) {
        assertThatThrownBy(() -> new Product("콜라", price))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @ValueSource(ints = {1000, 650, 500, 100})
    @ParameterizedTest
    void constructor(final int price) {
        assertDoesNotThrow(() -> new Product("콜라", price));
    }
}
