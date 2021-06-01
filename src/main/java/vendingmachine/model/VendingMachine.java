package vendingmachine.model;

import java.util.List;

public class VendingMachine {

    private final Products products;
    private final ChangesModule changesModule;

    public VendingMachine() {
        this(new Products());
    }

    public VendingMachine(Products products) {
        this(products, new ChangesModule());
    }

    public VendingMachine(Products products, ChangesModule changesModule) {
        this.products = products;
        this.changesModule = changesModule;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(String productName) {
        products.remove(productName);
    }

    public void insertCoins(Coin... coins) {
        changesModule.insertCoins(coins);
    }

    public Product buy(String productName) {
        final Product product = products.findByName(productName);
        changesModule.subtractCurrentMoney(product.getMoney());
        return product;
    }

    public int getCurrentMoney() {
        return changesModule.getCurrentMoney();
    }

    public List<Coin> returnChanges() {
        return changesModule.returnChanges();
    }
}
