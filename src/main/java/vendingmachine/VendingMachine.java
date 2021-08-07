package vendingmachine;

import java.util.List;
import vendingmachine.domain.ChangeModule;
import vendingmachine.domain.Money;
import vendingmachine.domain.Product;
import vendingmachine.domain.Products;

public class VendingMachine {

    private final Products products;
    private final ChangeModule changeModule;

    public VendingMachine(final Products products, final ChangeModule changeModule) {
        this.products = products;
        this.changeModule = changeModule;
    }

    public void initVendingMachine(final Money money) {
        changeModule.increase(money);
    }

    public void addProducts(final List<String> productInfos) {
        for (String productInfo : productInfos) {
            String[] splitProductInfo = productInfo.split(",");
            products.add(new Product(splitProductInfo[0], splitProductInfo[1], splitProductInfo[2]));
        }
    }
}
