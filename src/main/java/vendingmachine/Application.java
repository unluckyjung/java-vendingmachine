package vendingmachine;

import vendingmachine.model.*;
import vendingmachine.view.OutputView;

public class Application {
    public static void main(String[] args) {
        System.out.println("우테코 자판기 전원 ON!");

        VendingMachine vendingMachine = vendingMachineWithDefaultProducts();
        while (vendingMachine.isRunning()) {
            MainController.control(vendingMachine);
        }

        OutputView.printPurchasedProducts(vendingMachine.getPurchasedProducts());
        OutputView.printChanges(vendingMachine.returnChanges());
    }

    private static VendingMachine vendingMachineWithDefaultProducts() {
        VendingMachine vendingMachine = new VendingMachine(new ChangesModule(coinBoxWithDefaultCounts()));
        vendingMachine.addProduct(new Product("콜라", 1000));
        vendingMachine.addProduct(new Product("사이다", 1500));
        vendingMachine.addProduct(new Product("쿨피스", 700));
        return vendingMachine;
    }

    private static CoinBox coinBoxWithDefaultCounts() {
        CoinBox coinBox = new CoinBox();
        coinBox.put(Coin.TEN, Integer.MAX_VALUE);
        coinBox.put(Coin.FIFTY, 1);
        coinBox.put(Coin.ONE_HUNDRED, 1);
        coinBox.put(Coin.FIVE_HUNDRED, 1);
        return coinBox;
    }
}
