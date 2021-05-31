package vendingmachine.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductTest {

    @DisplayName("상품의 금액을 음수로 만들면, 예외가 발생한다.")
    @Test
    void productWrongPrice() {
        assertThatThrownBy(() -> {
            new Product("콜라", -1);
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("상품의 금액은 음수가 될 수 없습니다.");
    }
}
