package vendingmachine.domain;

import java.util.Arrays;
import java.util.List;

public class VendingMachine {
    private final ChangeModule changeModule;
    private final Coins coins;
    private final Products purchasedProducts;
    private final Products products;
    private boolean isRunning;

    public VendingMachine(final ChangeModule changeModule, final Products products, final Products purchasedProducts, final List<CoinSet> coins) {
        this.changeModule = changeModule;
        this.products = products;
        this.purchasedProducts = purchasedProducts;
        this.coins = new Coins(coins);
    }

    public VendingMachine(final ChangeModule changeModule, final Products products, final Products purchasedProducts) {
        this(changeModule, products, purchasedProducts,
                Arrays.asList(
                        CoinSet._500, CoinSet._500, CoinSet._500,
                        CoinSet._100, CoinSet._100, CoinSet._100,
                        CoinSet._50, CoinSet._50, CoinSet._50
                ));
    }

    public VendingMachine(final ChangeModule changeModule, final Products products) {
        this(changeModule, products, new Products());
    }

    public VendingMachine() {
        this(new ChangeModule(), new Products(), new Products());
        isRunning = true;
    }

    public void insertCoin(final CoinSet coin) {
        coins.add(coin);
        changeModule.inputCoin(coin);
    }

    public int getAmount() {
        return changeModule.getAmount();
    }

    public List<CoinSet> withDrawToCoins() {
        isRunning = false;
        return changeModule.withDrawToCoins(coins);
    }

    public void addProduct(final Product product) {
        products.add(product);
    }

    public void deleteProductByName(final String productName) {
        products.delete(productName);
    }

    public void buyProductByName(final String productName) {
        isValidateProductName(productName);
        Product purchasedProduct = products.getProductByName(productName);
        changeModule.reduce(purchasedProduct.getPrice());
        purchasedProducts.add(purchasedProduct);
    }

    private void isValidateProductName(final String productName) {
        if (!products.isExistName(productName)) {
            throw new IllegalArgumentException("존재하지 않는 상품을 구매할 수 없습니다.");
        }
    }

    public List<Product> getPurchasedProducts() {
        return purchasedProducts.toList();
    }

    public List<Product> getProducts() {
        return products.toList();
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void stop() {
        isRunning = false;
    }
}
