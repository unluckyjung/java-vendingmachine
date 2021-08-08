package vendingmachine;

import java.util.List;
import vendingmachine.domain.ChangeModule;
import vendingmachine.domain.CoinSet;
import vendingmachine.domain.Money;
import vendingmachine.domain.Product;
import vendingmachine.domain.Products;

public class VendingMachine {

    public static final int PRODUCT_NAME = 0;
    public static final int PRODUCT_QUANTITY = 1;
    public static final int PRODUCT_PRICE = 2;
    private final Products products;
    private final ChangeModule changeModule;

    public VendingMachine(final Products products, final ChangeModule changeModule) {
        this.products = products;
        this.changeModule = changeModule;
    }

    public void addProducts(final List<String> productInfos) {
        if(productInfos.isEmpty()){
            throw new IllegalArgumentException("입력형식이 잘못되었습니다.");
        }

        for (String productInfo : productInfos) {
            String[] splitProductInfo = productInfo.split(",");
            products.add(new Product(splitProductInfo[PRODUCT_NAME], splitProductInfo[PRODUCT_QUANTITY], splitProductInfo[PRODUCT_PRICE]));
        }
    }

    public void inputMoney(final int inputMoney) {
        changeModule.increase(new Money(inputMoney));
    }

    public int getChangeAmount() {
        return changeModule.changeAmount();
    }

    public boolean isCanBuyAnything() {
        return products.isCanBuyAnything(changeModule);
    }

    public void buyProductByName(final String productName) {
        products.buy(productName, changeModule);
    }

    public List<CoinSet> getChangeCoins() {
        return changeModule.withDrawToCoin();
    }
}
