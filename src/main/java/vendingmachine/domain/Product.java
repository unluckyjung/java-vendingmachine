package vendingmachine.domain;

public class Product {
    private final String name;
    private final int price;

    public Product(final String name, final int price) {
        validatePrice(price);
        this.name = name;
        this.price = price;
    }

    private void validatePrice(final int price) {
        if (price < 0) {
            throw new IllegalArgumentException("상품의 금액은 음수가 될 수 없습니다.");
        }
    }

    public boolean hasSameName(final Product product) {
        return name.equals(product.getName());
    }

    public boolean hasSameName(final String name) {
        return this.name.equals(name);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
