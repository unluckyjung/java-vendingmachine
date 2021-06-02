package vendingmachine.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Products {

    private final List<Product> products;

    public Products(Product... products) {
        this(Arrays.asList(products));
    }

    public Products(List<Product> products) {
        this.products = new ArrayList<>(products);
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public void add(Product product) {
        if (findOptionalProductByName(product.getName()).isPresent()) {
            throw new IllegalArgumentException("이미 자판기에 등록되어 있는 상품입니다.");
        }

        products.add(product);
    }

    public void remove(String name) {
        Product product = findByName(name);
        products.remove(product);
    }

    public Product findByName(String name) {
        return findOptionalProductByName(name)
                .orElseThrow(() -> new IllegalArgumentException("자판기에 등록되지 않은 상품 이름입니다."));
    }

    private Optional<Product> findOptionalProductByName(String name) {
        return products.stream()
                       .filter(product -> product.isNameEqualsTo(name))
                       .findAny();
    }
}
