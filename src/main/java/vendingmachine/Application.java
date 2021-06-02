package vendingmachine;

import vendingmachine.model.Product;
import vendingmachine.model.VendingMachine;
import vendingmachine.view.OutputView;

public class Application {
    public static void main(String[] args) {
        System.out.println("우테코 자판기 전원 ON!");

        VendingMachine vendingMachine = new VendingMachine();
        vendingMachine.addProduct(new Product("콜라", 1000));
        vendingMachine.addProduct(new Product("사이다", 1500));
        vendingMachine.addProduct(new Product("쿨피스", 700));

        while (vendingMachine.isRunning()) {
            MainController.control(vendingMachine);
        }

        OutputView.printPurchasedProducts(vendingMachine.getPurchasedProducts());
        OutputView.printChanges(vendingMachine.returnChanges());
    }
}
