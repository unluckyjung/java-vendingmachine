package domain.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StorageTest {
    @ValueSource(strings = {"콜라,20,1500]", "[콜라,20,1500", "[]", "[콜라,20]"})
    @ParameterizedTest
    void from(final String text) {
        assertThatThrownBy(() -> Storage.from(text))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void from_duplicated() {
        assertThatThrownBy(() -> Storage.from("[콜라,20,1500];[콜라,20,1000]"))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void from() {
        final Storage storage = Storage.from("[콜라,20,1500];[사이다,10,1000]");
        final Map<Product, Integer> products = storage.getProducts();
        assertThat(products).containsEntry(new Product("콜라", 1500), 20);
        assertThat(products).containsEntry(new Product("사이다", 1000), 10);
    }
}
