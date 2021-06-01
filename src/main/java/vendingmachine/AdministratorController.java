package vendingmachine;

import java.util.Arrays;
import java.util.function.Consumer;

import vendingmachine.model.Product;
import vendingmachine.model.VendingMachine;
import vendingmachine.view.InputView;

public enum AdministratorController {
    ADD(1, vendingMachine -> {
        final String productNameAndPrice = InputView.inputFromAddProductView();
        final String[] nameAndPrice = productNameAndPrice.split(" ");
        vendingMachine.addProduct(new Product(nameAndPrice[0], nameAndPrice[1]));
    }),

    REMOVE(2, vendingMachine -> {
        final String productName = InputView.inputFromRemoveProductView();
        vendingMachine.removeProduct(productName);
    }),

    FALL_BACK(3, vendingMachine -> {});

    private final int functionNumber;
    private final Consumer<VendingMachine> vendingMachineConsumer;

    AdministratorController(int functionNumber, Consumer<VendingMachine> vendingMachineConsumer) {
        this.functionNumber = functionNumber;
        this.vendingMachineConsumer = vendingMachineConsumer;
    }

    public static void control(VendingMachine vendingMachine) {
        final int functionNumber = InputView.inputFromAdministratorView();
        final AdministratorController function = findFunction(functionNumber);
        function.vendingMachineConsumer.accept(vendingMachine);
    }

    private static AdministratorController findFunction(int functionNumber) {
        return Arrays.stream(AdministratorController.values())
                     .filter(function -> function.functionNumber == functionNumber)
                     .findAny()
                     .orElseGet(() -> {
                         System.out.println("없는 메뉴 번호입니다. 다시 선택해주세요!");
                         return AdministratorController.findFunction(functionNumber);
                     });
    }
}
