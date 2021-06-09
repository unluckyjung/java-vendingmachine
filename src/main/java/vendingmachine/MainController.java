package vendingmachine;

import vendingmachine.model.VendingMachine;
import vendingmachine.view.InputView;
import vendingmachine.view.OutputView;

import java.util.Arrays;
import java.util.Scanner;
import java.util.function.BiConsumer;

public enum MainController {
    ADMINISTRATOR_MODE(1, AdministratorController::control),

    CLIENT_MODE(2, ClientController::control),

    TURN_OFF(3, (vendingMachine, scanner) -> {
        vendingMachine.end();
    });

    private final int functionNumber;
    private final BiConsumer<VendingMachine, Scanner> vendingMachineConsumer;

    MainController(int functionNumber, BiConsumer<VendingMachine, Scanner> vendingMachineConsumer) {
        this.functionNumber = functionNumber;
        this.vendingMachineConsumer = vendingMachineConsumer;
    }

    public static void control(VendingMachine vendingMachine, Scanner scanner) {
        OutputView.printCurrentProducts(vendingMachine.getCurrentProducts());
        OutputView.printCurrentMoney(vendingMachine.getChangesModule().getCurrentMoney());
        OutputView.printCoinCount(vendingMachine.getChangesModule());

        final int functionNumber = InputView.inputFromMainView(scanner);
        final MainController function = findFunction(functionNumber, scanner);
        function.vendingMachineConsumer.accept(vendingMachine, scanner);
    }

    private static MainController findFunction(int functionNumber, Scanner scanner) {
        return Arrays.stream(MainController.values())
                .filter(function -> function.functionNumber == functionNumber)
                .findAny()
                .orElseGet(() -> {
                    System.out.println("없는 메뉴 번호입니다. 다시 선택해주세요!");
                    return MainController.findFunction(InputView.inputFromMainView(scanner), scanner);
                });
    }
}
