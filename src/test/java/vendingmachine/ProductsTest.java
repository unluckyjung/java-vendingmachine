package vendingmachine;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class ProductsTest {

    @DisplayName("상품 추가")
    @Test
    void addTest() {

        // given
        Products products = new Products();
        Product 콜라 = new Product("콜라", 1000);

        // when
        products.add(콜라);

        // then
        assertThat(products.getProducts()).containsExactly(콜라);
    }

    @DisplayName("상품 삭제")
    @Test
    void removeTest() {

        // given
        Products products = new Products();
        Product 콜라 = new Product("콜라", 1000);
        products.add(콜라);

        // when
        products.remove("콜라");

        // then
        assertThat(products.getProducts()).isEmpty();
    }

    @DisplayName("이름으로 상품 조회")
    @Test
    void findByNameTest() {

        // given
        Products products = new Products();
        Product 콜라 = new Product("콜라", 1000);
        products.add(콜라);

        // when
        final Product insertedProduct = products.findByName("콜라");

        // then
        assertThat(insertedProduct).isEqualTo(콜라);
    }
}
