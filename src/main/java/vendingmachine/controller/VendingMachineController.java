package vendingmachine.controller;

import vendingmachine.domain.CoinSet;
import vendingmachine.domain.Product;
import vendingmachine.domain.VendingMachine;
import vendingmachine.view.InputView;
import vendingmachine.view.OutputView;

import java.util.List;

public class VendingMachineController {

    private final VendingMachine vendingMachine;

    public VendingMachineController(final VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    public void run() {
        while (vendingMachine.isRunning()) {
            modeSelect();
        }
    }

    private void modeSelect() {
        int modeNumber = getModeNumber();
        if (modeNumber == 1) {
            adminMode();
        }
        if (modeNumber == 2) {
            customerMode();
        }
    }

    private int getModeNumber() {
        int modeNumber = 0;
        try {
            modeNumber = InputView.getModeNumber();
        } catch (IllegalArgumentException e) {
            OutputView.printWrongInputError(e.getMessage());
        }
        return modeNumber;
    }

    private void adminMode() {
        int productCustomNumber = 0;
        try {
            productCustomNumber = getProductCustomNumber();
        } catch (IllegalArgumentException e) {
            OutputView.printWrongInputError(e.getMessage());
        }
        if (productCustomNumber == 1) {
            productAdd();
        }
        if (productCustomNumber == 2) {
            productDelete();
        }
        OutputView.printProducts(vendingMachine.getProducts());
    }

    private void productAdd() {
        String productName = InputView.getAddProductName();
        try {
            int productPrice = InputView.getProductPrice();
            vendingMachine.addProduct(new Product(productName, productPrice));
        } catch (IllegalArgumentException e) {
            OutputView.printWrongInputError(e.getMessage());
        }
    }

    private void productDelete() {
        String productName = InputView.getDeleteProductName();
        try {
            vendingMachine.deleteProductByName(productName);
        } catch (IllegalArgumentException e) {
            OutputView.printWrongInputError(e.getMessage());
        }
    }

    private int getProductCustomNumber() {
        int productCustomNumber = 0;
        try {
            productCustomNumber = InputView.getProductCustomNumber();
        } catch (IllegalArgumentException e) {
            OutputView.printWrongInputError(e.getMessage());
        }
        return productCustomNumber;
    }

    private void customerMode() {
        int customerSelectNumber = 0;
        OutputView.printAmount(vendingMachine.getAmount());
        try {
            customerSelectNumber = getCustomerSelectNumber();
        } catch (IllegalArgumentException e) {
            OutputView.printWrongInputError(e.getMessage());
        }
        if (customerSelectNumber == 1) {
            productBuy();
        }
        if (customerSelectNumber == 2) {
            withDraw();
        }
        if (customerSelectNumber == 3) {
            insertCoin();
        }
    }

    private void insertCoin() {
        try {
            CoinSet coin = getCoin();
            vendingMachine.insertCoin(coin);
        } catch (IllegalArgumentException e) {
            OutputView.printWrongInputError(e.getMessage());
        }
    }

    private CoinSet getCoin() {
        return CoinSet.getCoin(InputView.insertCoin());
    }

    private void withDraw() {
        List<CoinSet> coins = vendingMachine.withDrawToCoins();
        OutputView.printWithDrawCoins(coins);
        vendingMachine.stop();
    }

    private void productBuy() {
        OutputView.printProducts(vendingMachine.getProducts());
        String productName = InputView.getBuyProductName();
        try {
            vendingMachine.buyProductByName(productName);
        } catch (IllegalArgumentException e) {
            OutputView.printWrongInputError(e.getMessage());
        } finally {
            OutputView.printPurchasedProducts(vendingMachine.getPurchasedProducts());
        }
    }

    private int getCustomerSelectNumber() {
        int customerSelectNumber = 0;
        try {
            customerSelectNumber = InputView.getCustomerSelectNumber();
        } catch (IllegalArgumentException e) {
            OutputView.printWrongInputError(e.getMessage());
        }
        return customerSelectNumber;
    }
}
