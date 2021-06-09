package vendingmachine;

import vendingmachine.model.Coin;
import vendingmachine.model.VendingMachine;
import vendingmachine.view.InputView;
import vendingmachine.view.OutputView;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;

public enum ClientController {
    INSERT_MONEY(1, (vendingMachine, scanner) -> {
        List<Coin> coins = Coin.toCoins(InputView.inputFromInsertMoneyView(scanner));
        vendingMachine.insertCoins(coins);
    }),

    BUY_PRODUCT(2, (vendingMachine, scanner) -> {
        final String productName = InputView.inputFromBuyProductView(scanner);
        vendingMachine.buy(productName);
        OutputView.printBoughtProduct(productName);
    }),

    RETURN_CHANGES(3, (vendingMachine, scanner) ->{
        vendingMachine.end();
    }),

    FALL_BACK(4, (vendingMachine, scanner) -> {});

    private final int functionNumber;
    private final BiConsumer<VendingMachine, Scanner> vendingMachineConsumer;

    ClientController(int functionNumber, BiConsumer<VendingMachine, Scanner> vendingMachineConsumer) {
        this.functionNumber = functionNumber;
        this.vendingMachineConsumer = vendingMachineConsumer;
    }

    public static void control(VendingMachine vendingMachine, Scanner scanner) {
        final int functionNumber = InputView.inputFromClientView(scanner);
        final ClientController function = findFunction(functionNumber);
        function.vendingMachineConsumer.accept(vendingMachine, scanner);
    }

    private static ClientController findFunction(int functionNumber) {
        return Arrays.stream(ClientController.values())
                     .filter(function -> function.functionNumber == functionNumber)
                     .findAny()
                     .orElseGet(() -> {
                         System.out.println("없는 메뉴 번호입니다. 다시 선택해주세요!");
                         return ClientController.findFunction(functionNumber);
                     });
    }
}
