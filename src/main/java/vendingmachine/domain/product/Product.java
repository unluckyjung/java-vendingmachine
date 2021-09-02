package vendingmachine.domain.product;

import java.util.Objects;

public class Product {
    private static final int MINIMUM_PRICE = 100;
    private static final int DIVIDED_PRICE = 10;

    private final String name;
    private final int price;

    public Product(final String name, final int price) {
        validate(price);
        this.name = name;
        this.price = price;
    }

    private void validate(final int price) {
        if (price < MINIMUM_PRICE || price % DIVIDED_PRICE != 0) {
            throw new IllegalArgumentException(String.format("상품의 최소 금액은 100원이며, 10원으로 나누어 떨어져야 합니다. price:%d", price));
        }
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Product product = (Product) o;
        return price == product.price && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }

    @Override
    public String toString() {
        return "Product{" +
            "name='" + name + '\'' +
            ", price=" + price +
            '}';
    }
}
