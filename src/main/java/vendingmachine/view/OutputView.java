package vendingmachine.view;

import vendingmachine.domain.CoinSet;
import vendingmachine.domain.Product;

import java.util.List;

public class OutputView {
    public static void printWrongInputError(final String message) {
        System.out.println(message);
        System.out.println("잘못된 입력입니다.");
    }

    public static void printPurchasedProducts(final List<Product> purchasedProducts) {
        System.out.print("현재 구매완료한 상품 목록 : ");
        for (Product product : purchasedProducts) {
            System.out.print(product.getName() + " " + product.getPrice() + " ");
        }
        System.out.println();
    }

    public static void printWithDrawCoins(final List<CoinSet> coins) {
        System.out.println("동전을 인출했습니다.");
        System.out.print("인출된 동전 : ");
        for(CoinSet coin : coins){
            System.out.print(coin.getValue() + " ");
        }
        System.out.println();
        System.out.println();
    }

    public static void printAmount(final int amount) {
        System.out.println("현재 보유하고 있는 금액은 " + amount + " 원 입니다.");
    }

    public static void printProducts(final List<Product> products) {
        System.out.print("현재 보유하고 있는 상품 목록 : ");
        for (Product product : products) {
            System.out.print(product.getName() + " " + product.getPrice() + " ");
        }
        System.out.println();
    }
}
