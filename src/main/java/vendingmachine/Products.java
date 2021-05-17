package vendingmachine;

import java.util.Arrays;
import java.util.List;

public class Products {

    private final List<Product> products;

    public Products(Product... products) {
        this(Arrays.asList(products));
    }

    public Products(List<Product> products) {
        this.products = products;
    }

    public boolean canBuyAnything(int money) {
        return products.stream().anyMatch(product -> product.getPrice() <= money);
    }

    public List<Product> getProducts() {
        return products;
    }
}
