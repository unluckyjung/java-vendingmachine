package vendingmachine.domain;

import java.util.ArrayList;
import java.util.List;

public class Products {

    private final List<Product> products;

    public Products() {
        products = new ArrayList<>();
    }

    public void add(final Product product) {
        validateNameDuplication(product);
        products.add(product);
    }

    private void validateNameDuplication(final Product product) {
        if (isExistName(product)) {
            throw new IllegalArgumentException("이미 존재하는 상품명 입니다.");
        }
    }

    private boolean isExistName(final Product product) {
        return products.stream().anyMatch(product1 -> product1.hasSameName(product));
    }

    public List<Product> toList() {
        return products;
    }
}
