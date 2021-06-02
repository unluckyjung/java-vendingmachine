package vendingmachine.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import vendingmachine.model.Coin;
import vendingmachine.model.Product;
import vendingmachine.model.Products;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class OutputView {

    public static void printPurchasedProducts(Products purchasedProducts) {
        Map<String, Long> purchaseProductMap = purchasedProducts.getProducts()
                                                                .stream()
                                                                .collect(groupingBy(Product::getName, counting()));

        List<String> products = new ArrayList<>();
        for (Map.Entry<String, Long> entry : purchaseProductMap.entrySet()) {
            products.add(String.format("%s %d개", entry.getKey(), entry.getValue()));
        }

        System.out.printf("\n구입한 상품들은 %s 입니다.", String.join(", ", products));

    }

    public static void printChanges(List<Coin> changes) {
        System.out.printf("\n남은 잔돈은 %s 입니다.", changes);
    }

    public static void printCurrentProducts(List<Product> currentProducts) {
        System.out.println("\n# 자판기 상품 목록");
        for (Product product : currentProducts) {
            System.out.printf("%s - %d 원\n", product.getName(), product.getMoney().getMoney());
        }
        System.out.println();
    }

    public static void printCurrentMoney(int currentMoney) {
        System.out.printf("# 잔액\n%d원\n", currentMoney);
    }

    public static void printBoughtProduct(String productName) {
        System.out.printf("\n%s 구입에 성공했습니다!\n", productName);
    }
}
