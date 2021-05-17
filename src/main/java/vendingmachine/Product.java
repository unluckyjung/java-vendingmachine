package vendingmachine;

public enum Product {
    WATER(100), CIDER(130), COKE(150);

    private final int price;

    Product(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
