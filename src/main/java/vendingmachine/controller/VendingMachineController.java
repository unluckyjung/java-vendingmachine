package vendingmachine.controller;

import vendingmachine.domain.CoinSet;
import vendingmachine.domain.CommandNumberSet;
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
        if (modeNumber == CommandNumberSet.ADMIN_MODE_NUMBER.getValue()) {
            adminMode();
        }
        if (modeNumber == CommandNumberSet.CUSTOM_MODE_NUMBER.getValue()) {
            customerMode();
        }
    }

    private int getModeNumber() {
        int modeNumber = CommandNumberSet.DEFAULT_NUMBER.getValue();
        try {
            modeNumber = InputView.getModeNumber();
        } catch (IllegalArgumentException e) {
            OutputView.printWrongInputError(e.getMessage());
        }
        return modeNumber;
    }

    private void adminMode() {
        int productCustomNumber = CommandNumberSet.DEFAULT_NUMBER.getValue();
        try {
            productCustomNumber = getProductCustomNumber();
        } catch (IllegalArgumentException e) {
            OutputView.printWrongInputError(e.getMessage());
        }
        if (productCustomNumber == CommandNumberSet.PRODUCT_ADD_NUMBER.getValue()) {
            productAdd();
        }
        if (productCustomNumber == CommandNumberSet.PRODUCT_DELETE_NUMBER.getValue()) {
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
        int productCustomNumber = CommandNumberSet.DEFAULT_NUMBER.getValue();
        try {
            productCustomNumber = InputView.getProductCustomNumber();
        } catch (IllegalArgumentException e) {
            OutputView.printWrongInputError(e.getMessage());
        }
        return productCustomNumber;
    }

    private void customerMode() {
        int customerSelectNumber = CommandNumberSet.DEFAULT_NUMBER.getValue();
        OutputView.printAmount(vendingMachine.getAmount());
        try {
            customerSelectNumber = getCustomerSelectNumber();
        } catch (IllegalArgumentException e) {
            OutputView.printWrongInputError(e.getMessage());
        }
        if (customerSelectNumber == CommandNumberSet.PRODUCT_BUY_NUMBER.getValue()) {
            productBuy();
        }
        if (customerSelectNumber == CommandNumberSet.WITH_DRAW_NUMBER.getValue()) {
            withDraw();
        }
        if (customerSelectNumber == CommandNumberSet.INSERT_COIN_NUMBER.getValue()) {
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
        int customerSelectNumber = CommandNumberSet.DEFAULT_NUMBER.getValue();
        try {
            customerSelectNumber = InputView.getCustomerSelectNumber();
        } catch (IllegalArgumentException e) {
            OutputView.printWrongInputError(e.getMessage());
        }
        return customerSelectNumber;
    }
}
