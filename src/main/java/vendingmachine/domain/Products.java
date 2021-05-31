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
        if (isExistName(product.getName())) {
            throw new IllegalArgumentException("이미 존재하는 상품명 입니다.");
        }
    }

    public void delete(final String name) {
        validateDeleteName(name);
    }

    private void validateDeleteName(final String name) {
        if (!isExistName(name)) {
            throw new IllegalArgumentException("존재하지 않는 상품명 입니다.");
        }
    }

    public boolean isExistName(final String name) {
        return products.stream().anyMatch(product -> product.hasSameName(name));
    }

    public Product getProductByName(final String name) {
        return products.stream()
                .filter(product -> product.hasSameName(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("상품이 없습니다."));
    }

    public List<Product> toList() {
        return products;
    }
}