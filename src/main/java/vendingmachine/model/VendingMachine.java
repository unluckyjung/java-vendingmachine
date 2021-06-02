package vendingmachine.model;

import java.util.List;

public class VendingMachine {

    private final Products products;
    private final Products purchasedProducts;
    private final ChangesModule changesModule;
    private boolean isRunning;

    public VendingMachine(ChangesModule changesModule) {
        this(new Products(), changesModule);
    }

    public VendingMachine(Products products) {
        this(products, new ChangesModule());
    }

    public VendingMachine(Products products, ChangesModule changesModule) {
        this(products, new Products(), changesModule, true);
    }

    public VendingMachine(Products products, Products purchasedProducts, ChangesModule changesModule, boolean isRunning) {
        this.products = products;
        this.purchasedProducts = purchasedProducts;
        this.changesModule = changesModule;
        this.isRunning = isRunning;
    }

    public Products getPurchasedProducts() {
        return purchasedProducts;
    }

    public List<Product> getCurrentProducts() {
        return products.getProducts();
    }

    public ChangesModule getChangesModule() {
        return changesModule;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(String productName) {
        products.remove(productName);
    }

    public void insertCoins(List<Coin> coins) {
        insertCoins(coins.toArray(new Coin[0]));
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
