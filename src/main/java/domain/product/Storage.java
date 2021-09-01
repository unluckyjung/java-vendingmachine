package domain.product;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class Storage {
    private final Map<Product, Integer> products;

    public Storage(final Map<Product, Integer> products) {
        if (duplicated(products)) {
            throw new IllegalArgumentException(String.format("중복 상품을 입력할 수 없습니다."));
        }
        this.products = new HashMap<>(products);
    }

    private boolean duplicated(final Map<Product, Integer> products) {
        final long unique = products.keySet().stream()
            .map(it -> it.getName())
            .distinct()
            .count();
        return products.size() != unique;
    }

    public Product pop(final String name) {
        final Product product = getProduct(name);
        products.computeIfPresent(product, decreaseQuantity(name));
        return product;
    }

    private Product getProduct(final String name) {
        return products.keySet().stream()
            .filter(it -> it.getName().equals(name))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException(String.format("존재하지 않는 상품입니다. name:%s", name)));
    }

    private BiFunction<Product, Integer, Integer> decreaseQuantity(final String name) {
        return (key, value) -> {
            if (value == 0) {
                throw new IllegalArgumentException(String.format("남은 수량이 없습니다. name:%s", name));
            }
            return value - 1;
        };
    }

    public Map<Product, Integer> getProducts() {
        return new HashMap<>(products);
    }

    public static Storage from(final String text) {
        final String[] tokens = text.split(";");
        final Map<Product, Integer> products = new HashMap<>();
        for (final String token : tokens) {
            final String[] properties = parse(token);
            final String name = properties[0];
            final int quantity = Integer.parseInt(properties[1]);
            final int price = Integer.parseInt(properties[2]);
            final boolean duplicated = products.putIfAbsent(new Product(name, price), quantity) != null;
            if (duplicated) {
                throw new IllegalArgumentException(String.format("중복 상품을 입력할 수 없습니다. text:%s", text));
            }
        }
        return new Storage(products);
    }

    private static String[] parse(final String token) {
        if (!token.startsWith("[") || !token.endsWith("]")) {
            throw new IllegalArgumentException(String.format("형식이 잘못되었습니다. text:%s", token));
        }
        final String productText = token.substring(1, token.length() - 1);
        final String[] properties = productText.split(",");
        if (properties.length != 3) {
            throw new IllegalArgumentException(String.format("형식이 잘못되었습니다. text:%s", token));
        }
        try {
            Integer.parseInt(properties[1]);
            Integer.parseInt(properties[2]);
        } catch (final NumberFormatException e) {
            throw new IllegalArgumentException(String.format("형식이 잘못되었습니다. text:%s", token));
        }
        return properties;
    }
}
