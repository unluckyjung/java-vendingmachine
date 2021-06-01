package vendingmachine;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import vendingmachine.model.Coin;
import vendingmachine.model.VendingMachine;
import vendingmachine.view.InputView;

public enum ClientController {
    INSERT_MONEY(1, vendingMachine -> {
        List<Coin> coins = Coin.toCoins(InputView.inputFromInsertMoneyView());
        vendingMachine.insertCoins(coins);
    }),

    BUY_PRODUCT(2, vendingMachine -> {
        final String productName = InputView.inputFromBuyProductView();
        vendingMachine.buy(productName);
    }),

    RETURN_CHANGES(3, VendingMachine::end),

    FALL_BACK(4, vendingMachine -> {});

    private final int functionNumber;
    private final Consumer<VendingMachine> vendingMachineConsumer;

    ClientController(int functionNumber, Consumer<VendingMachine> vendingMachineConsumer) {
        this.functionNumber = functionNumber;
        this.vendingMachineConsumer = vendingMachineConsumer;
    }

    public static void control(VendingMachine vendingMachine) {
        final int functionNumber = InputView.inputFromClientView();
        final ClientController function = findFunction(functionNumber);
        function.vendingMachineConsumer.accept(vendingMachine);
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
