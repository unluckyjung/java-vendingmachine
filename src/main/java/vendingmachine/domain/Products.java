package vendingmachine.domain;

import java.util.ArrayList;
import java.util.List;

public class Products {

    private final List<Product> products;

    public Products() {
        products = new ArrayList<>();
    }

    public void add(final Product product) {
        validateNameDuplication(product.getName());
        products.add(product);
    }

    private void validateNameDuplication(final String productName) {
        if (isExistName(productName)) {
            throw new IllegalArgumentException("이미 존재하는 상품명 입니다.");
        }
    }

    public void buy(final String productName, final ChangeModule changeModule) {
        validateNameToBuy(productName);
        for (Product product : products) {
            if (product.hasSameName(productName)) {
                changeModule.reduce(product.getPrice());
                product.decrease();
                break;
            }
        }
    }

    private void validateNameToBuy(final String productName) {
        if (!isExistName(productName)) {
            throw new IllegalArgumentException("존재하지 않는 상품명 입니다.");
        }
    }

    private boolean isExistName(final String name) {
        return products.stream().anyMatch(product -> product.hasSameName(name));
    }
}
