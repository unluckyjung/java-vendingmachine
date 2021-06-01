package vendingmachine.model;

public class Product {
    private final String name;
    private final Money money;

    public Product(String name, String money) {
        this(name, Integer.parseInt(money));
    }

    public Product(String name, int money) {
        this(name, Money.from(money));
    }

    public Product(String name, Money money) {
        this.name = name;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public Money getMoney() {
        return money;
    }

    public boolean isNameEqualsTo(String name) {
        return this.name.equals(name);
    }
}
