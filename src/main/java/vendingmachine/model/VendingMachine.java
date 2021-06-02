package vendingmachine.model;

import java.util.List;

public class VendingMachine {

    private final Products products;
    private final ChangesModule changesModule;
    private final Products purchasedProducts;
    private boolean isRunning;

    public VendingMachine() {
        this(new Products());
    }

    public VendingMachine(Products products) {
        this(products, new ChangesModule());
    }

    public VendingMachine(Products products, ChangesModule changesModule) {
        this(products, changesModule, new Products(), true);
    }

    public VendingMachine(Products products, ChangesModule changesModule, Products purchasedProducts, boolean isRunning) {
        this.products = products;
        this.changesModule = changesModule;
        this.purchasedProducts = purchasedProducts;
        this.isRunning = isRunning;
    }

    public Products getPurchasedProducts() {
        return purchasedProducts;
    }

    public List<Product> getCurrentProducts() {
        return products.getProducts();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(String productName) {
        products.remove(productName);
    }

    public void insertCoins(List<Coin> coins) {
        changesModule.insertCoins(coins.toArray(new Coin[0]));
    }

    public void insertCoins(Coin... coins) {
        changesModule.insertCoins(coins);
    }

    public void buy(String productName) {
        final Product product = products.findByName(productName);
        changesModule.subtractCurrentMoney(product.getMoney());
        purchasedProducts.add(product);
    }

    public List<Coin> returnChanges() {
        return changesModule.returnChanges();
    }

    public void end() {
        isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
