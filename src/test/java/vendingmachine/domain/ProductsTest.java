package vendingmachine.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductsTest {

    private final Products products = new Products();

    @BeforeEach
    void setUp() {
        products.add(new Product("콜라", 1000));
    }

    @DisplayName("상품을 추가하면, 잘 들어간다.")
    @ParameterizedTest
    @CsvSource({"멍토,1500"})
    void addTest(final String name, final int price) {
        products.add(new Product(name, price));
    }

    @DisplayName("같은 이름의 상품을 추가하면, 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({"콜라,9999"})
    void invalidAddTest(final String name, final int price) {
        assertThatThrownBy(() -> {
            products.add(new Product(name, price));
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("이미 존재하는 상품명 입니다.");
    }

    @DisplayName("존재하는 상품을 이름을 통해 삭제하면, 잘 삭제된다.")
    @ParameterizedTest
    @ValueSource(strings = {"콜라"})
    void deleteTest(final String name) {
        products.delete(name);
    }

    @DisplayName("존재하지 않는 상품 이름을 통해 삭제하면, 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"멍토"})
    void invalidDeleteTest(final String name) {
        assertThatThrownBy(() -> {
            products.delete(name);
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("존재하지 않는 상품명 입니다.");
    }
}
