package domain.product;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    private final Map<Product, Integer> products;

    private Storage(final Map<Product, Integer> products) {
        this.products = new HashMap<>(products);
    }

    public Map<Product, Integer> getProducts() {
        return new HashMap<>(products);
    }

    public static Storage from(final String text) {
        final String[] tokens = text.split(";");
        final Map<Product, Integer> products = new HashMap<>();
        for (final String token : tokens) {
            if (!token.startsWith("[") || !token.endsWith("]")) {
                throw new IllegalArgumentException(String.format("형식이 잘못되었습니다. text:%s", text));
            }
            final String productText = token.substring(1, token.length() - 1);
            final String[] properties = productText.split(",");
            if (properties.length != 3) {
                throw new IllegalArgumentException(String.format("형식이 잘못되었습니다. text:%s", text));
            }
            final String name = properties[0];
            int quantity;
            int price;
            try {
                quantity = Integer.parseInt(properties[1]);
                price = Integer.parseInt(properties[1]);
            } catch (final NumberFormatException e) {
                throw new IllegalArgumentException(String.format("형식이 잘못되었습니다. text:%s", text));
            }
            final boolean duplicated = products.putIfAbsent(new Product(name, price), quantity) != null;
            if (duplicated) {
                throw new IllegalArgumentException(String.format("중복 상품을 입력할 수 없습니다. text:%s", text));
            }
        }
        return new Storage(products);
    }
}
