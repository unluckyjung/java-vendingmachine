package vendingmachine.model;

import java.util.Collections;
import java.util.List;

public class BuyResult {
    private final Products products;
    private final List<Coin> coins;

    public BuyResult(Product product) {
        this(product, Collections.emptyList());
    }

    public BuyResult(Product product, List<Coin> coins) {
        this(new Products(product), coins);
    }

    public BuyResult(Products products, List<Coin> coins) {
        this.products = products;
        this.coins = coins;
    }

    public Products getProducts() {
        return products;
    }

    public List<Coin> getCoins() {
        return coins;
    }
}
