package vendingmachine.domain;

import java.util.List;

public class VendingMachine {
    private final ChangeModule changeModule;
    private final Products products;

    public VendingMachine(final ChangeModule changeModule, final Products products) {
        this.changeModule = changeModule;
        this.products = products;
    }

    public void insertCoin(final CoinSet coin) {
        changeModule.inputCoin(coin);
    }

    public int getAmount() {
        return changeModule.getAmount();
    }

    public List<CoinSet> withDrawToCoins() {
        return changeModule.withDrawToCoins();
    }

    public void addProduct(final Product product) {
        products.add(product);
    }

    public void deleteProductByName(final String productName) {
        products.delete(productName);
    }
}
