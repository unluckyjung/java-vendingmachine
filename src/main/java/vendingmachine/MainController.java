package vendingmachine;

import java.util.Arrays;
import java.util.function.Consumer;

import vendingmachine.model.VendingMachine;
import vendingmachine.view.InputView;
import vendingmachine.view.OutputView;

public enum MainController {
    ADMINISTRATOR_MODE(1, AdministratorController::control),

    CLIENT_MODE(2, ClientController::control),

    TURN_OFF(3, VendingMachine::end);

    private final int functionNumber;
    private final Consumer<VendingMachine> vendingMachineConsumer;

    MainController(int functionNumber, Consumer<VendingMachine> vendingMachineConsumer) {
        this.functionNumber = functionNumber;
        this.vendingMachineConsumer = vendingMachineConsumer;
    }

    public static void control(VendingMachine vendingMachine) {
        OutputView.printCurrentProducts(vendingMachine.getCurrentProducts());
        final int functionNumber = InputView.inputFromMainView();
        final MainController function = findFunction(functionNumber);
        function.vendingMachineConsumer.accept(vendingMachine);
    }

    private static MainController findFunction(int functionNumber) {
        return Arrays.stream(MainController.values())
                     .filter(function -> function.functionNumber == functionNumber)
                     .findAny()
                     .orElseGet(() -> {
                         System.out.println("없는 메뉴 번호입니다. 다시 선택해주세요!");
                         return MainController.findFunction(functionNumber);
                     });
    }
}
