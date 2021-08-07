package vendingmachine.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import vendingmachine.VendingMachine;
import vendingmachine.domain.ChangeModule;
import vendingmachine.domain.Money;
import vendingmachine.domain.Products;
import vendingmachine.view.InputView;

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
    }

    private void inputProducts() {
        List<String> productInfos = new ArrayList<>();
        Matcher matcher = GET_PRODUCT_INFO.matcher(InputView.getProductInfo());
        while (matcher.find()) {
            productInfos.add(matcher.group());
        }
        vendingMachine.addProducts(productInfos);
    }
}
