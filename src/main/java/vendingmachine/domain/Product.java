package vendingmachine.domain;

public class Product {

    private final String name;
    private int quantity;
    private final int price;

    public Product(final String name, final int quantity, int price) {
        validateProduct();
        this.quantity = quantity;
        this.name = name;
        this.price = price;
    }

    private void validateProduct() {
        validateName(name);
        validatePrice(price);
        validateQuantity(quantity);
    }

    private void validateName(final String name) {
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("상품 이름은 공백일 수 없습니다.");
        }
    }

    private void validatePrice(final int price) {
        if (price <= 0) {
            throw new IllegalArgumentException("상품의 금액은 0이나, 음수가 될 수 없습니다.");
        }
    }

    private void validateQuantity(final int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("물품의 개수는 음수일 수 없습니다.");
        }
    }

    public boolean hasSameName(final String name) {
        return this.name.equals(name);
    }

    public void decrease() {
        validateQuantity(quantity - 1);
        quantity--;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
