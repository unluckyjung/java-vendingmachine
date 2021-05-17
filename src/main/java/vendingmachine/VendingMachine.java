package vendingmachine;

public class VendingMachine {

    private final Products products;
    private final ChangesModule changesModule;

    public VendingMachine(Products products) {
        this(products, new ChangesModule());
    }

    public VendingMachine(Products products, ChangesModule changesModule) {
        this.products = products;
        this.changesModule = changesModule;
    }

    public void insertCoins(Coin... coins) {
        changesModule.insertCoins(coins);
    }

    public BuyResult buy(String productName) {
        final Product product = Product.valueOf(productName);
        changesModule.subtractCurrentMoney(Money.from(product.getPrice()));
        if (!canBuyAnything()) {
            return new BuyResult(product, changesModule.withdrawChanges());
        }
        return new BuyResult(product);
    }

    private boolean canBuyAnything() {
        return products.canBuyAnything(changesModule.getCurrentMoney());
    }

    public int getCurrentMoney() {
        return changesModule.getCurrentMoney();
    }
}
