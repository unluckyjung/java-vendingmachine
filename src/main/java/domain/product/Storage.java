package domain.product;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Storage {
    private static final Pattern PRODUCT_WITH_QUANTITY_PATTERN = Pattern.compile("^\\[([\\wㄱ-ㅎㅏ-ㅣ가-힣]+),(\\d+),(\\d+)\\]$");

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

    public int minimumPrice() {
        // 상품 최소 금액의 기준
        return products.keySet().stream()
            .map(Product::getPrice)
            .min(Integer::compareTo)
            .orElse(0);
    }

    public boolean empty() {
        return products.values().stream()
            .allMatch(it -> it == 0);
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
        final Matcher matcher = PRODUCT_WITH_QUANTITY_PATTERN.matcher(token);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(String.format("형식이 잘못되었습니다. text:%s", token));
        }
        return new String[]{matcher.group(1), matcher.group(2), matcher.group(3)};
    }
}
