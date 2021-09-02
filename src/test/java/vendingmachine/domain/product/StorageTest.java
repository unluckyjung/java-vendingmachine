package vendingmachine.domain.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
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

    @ValueSource(strings = {"[콜라,20,1500];[콜라,10,1500]", "[콜라,20,1500];[콜라,10,1000]"})
    @ParameterizedTest
    void from_duplicated(final String text) {
        assertThatThrownBy(() -> Storage.from(text))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void from() {
        final Storage storage = Storage.from("[콜라,20,1500];[사이다,10,1000]");
        final Map<Product, Integer> products = storage.getProducts();
        assertThat(products).containsEntry(new Product("콜라", 1500), 20);
        assertThat(products).containsEntry(new Product("사이다", 1000), 10);
    }

    @Test
    void pop() {
        final Product expected = new Product("콜라", 1500);
        final Storage storage = new Storage(new HashMap<Product, Integer>() {{
            put(expected, 20);
        }});
        final Product actual = storage.pop("콜라");
        assertThat(actual).isSameAs(expected);
        assertThat(storage.getProducts()).containsEntry(expected, 19);
    }

    @Test
    void pop_empty() {
        final Product expected = new Product("콜라", 1500);
        final Storage storage = new Storage(new HashMap<Product, Integer>() {{
            put(expected, 0);
        }});
        assertThatThrownBy(() -> storage.pop("콜라"))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void minimumPrice() {
        final Storage storage = new Storage(new HashMap<Product, Integer>() {{
            put(new Product("콜라", 1500), 0);
            put(new Product("사이다", 1000), 10);
        }});
        final int price = storage.minimumPrice();
        assertThat(price).isEqualTo(1000);
    }

    @Test
    void empty() {
        final Storage storage = new Storage(new HashMap<Product, Integer>() {{
            put(new Product("콜라", 1500), 0);
            put(new Product("사이다", 1000), 0);
        }});
        final boolean empty = storage.empty();
        assertThat(empty).isTrue();
    }

    @Test
    void not_empty() {
        final Storage storage = new Storage(new HashMap<Product, Integer>() {{
            put(new Product("콜라", 1500), 1);
            put(new Product("사이다", 1000), 0);
        }});
        final boolean empty = storage.empty();
        assertThat(empty).isFalse();
    }
}
