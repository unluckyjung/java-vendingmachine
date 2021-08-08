package vendingmachine.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import vendingmachine.VendingMachine;
import vendingmachine.domain.ChangeModule;
import vendingmachine.domain.CoinSet;
import vendingmachine.domain.Money;
import vendingmachine.domain.Products;
import vendingmachine.view.InputView;
import vendingmachine.view.OutputView;

public class VendingMachineController {

    private static final Pattern GET_PRODUCT_INFO = Pattern.compile("(?<=\\[)(.*?)(?=\\])");
    private final VendingMachine vendingMachine;

    public VendingMachineController() {
        this.vendingMachine = new VendingMachine(
            new Products(),
            new ChangeModule(new Money(InputView.getVendingMachineInitMoney()))
        );
    }

    public void run() {
        inputProducts();
        inputMoney();
        productBuy();
    }

    private void inputProducts() {
        List<String> productInfos = new ArrayList<>();
        Matcher matcher = GET_PRODUCT_INFO.matcher(InputView.getProductInfo());

        while (matcher.find()) {
            productInfos.add(matcher.group());
        }
        vendingMachine.addProducts(productInfos);
    }

    private void inputMoney() {
        vendingMachine.inputMoney(InputView.getInputMoney());
    }

    private void productBuy() {
        while (vendingMachine.isCanBuyAnything()) {
            printChangeAmount();
            vendingMachine.buyProductByName(InputView.getProductName());
        }
        printChangeCoins();
    }

    private void printChangeCoins() {
        List<CoinSet> changeCoins = vendingMachine.getChangeCoins();
        Map<Integer, Integer> coinsMap = new LinkedHashMap<>();

        for (CoinSet coin : changeCoins) {
            coinListToMap(coinsMap, coin);
        }

        OutputView.printChangeCoins(coinsMap);
    }

    private void coinListToMap(final Map<Integer, Integer> map, final CoinSet coin) {
        if (map.containsKey(coin.getValue())) {
            map.put(coin.getValue(), map.get(coin.getValue() + 1));
            return;
        }
        map.put(coin.getValue(), 1);
    }

    private void printChangeAmount() {
        OutputView.printChangeAmount(vendingMachine.getChangeAmount());
    }
}
