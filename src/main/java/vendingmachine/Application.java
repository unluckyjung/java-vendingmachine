package vendingmachine;

import vendingmachine.model.VendingMachine;
import vendingmachine.view.OutputView;

public class Application {
    public static void main(String[] args) {
        System.out.println("우테코 자판기 전원 ON!\n");

        VendingMachine vendingMachine = new VendingMachine();
        while (vendingMachine.isRunning()) {
            MainController.control(vendingMachine);
        }

        OutputView.printPurchasedProducts(vendingMachine.getPurchasedProducts());
        OutputView.printChanges(vendingMachine.returnChanges());
    }
}
