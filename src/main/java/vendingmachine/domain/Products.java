package vendingmachine.domain;

import java.util.ArrayList;
import java.util.List;

public class Products {
    private final List<Product> products;

    public Products() {
        this(new ArrayList<>());
    }

    public Products(List<Product> products) {
        this.products = new ArrayList<>(products);
    }

    public void add(final Product product) {
        validateNameDuplication(product);
        products.add(product);
    }

    private void validateNameDuplication(final Product product) {
        products.stream().filter(product1 -> product1.hasSameName(product))
                .findAny().ifPresent(
                product1 -> {
                    throw new IllegalArgumentException("이미 존재하는 상품명 입니다.");
                }
        );
    }
}