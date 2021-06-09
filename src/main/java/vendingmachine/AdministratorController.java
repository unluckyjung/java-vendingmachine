package vendingmachine;

import vendingmachine.model.Product;
import vendingmachine.model.VendingMachine;
import vendingmachine.view.InputView;

import java.util.Arrays;
import java.util.Scanner;
import java.util.function.BiConsumer;

public enum AdministratorController {
    ADD(1, (vendingMachine, scanner) -> {
        final String productNameAndPrice = InputView.inputFromAddProductView(scanner);
        final String[] nameAndPrice = productNameAndPrice.split(" ");
        vendingMachine.addProduct(new Product(nameAndPrice[0], nameAndPrice[1]));
    }),

    REMOVE(2, (vendingMachine, scanner) -> {
        final String productName = InputView.inputFromRemoveProductView(scanner);
        vendingMachine.removeProduct(productName);
    }),

    FALL_BACK(3, (vendingMachine, scanner) -> {
    });

    private final int functionNumber;
    private final BiConsumer<VendingMachine, Scanner> vendingMachineConsumer;

    AdministratorController(int functionNumber, BiConsumer<VendingMachine, Scanner> vendingMachineConsumer) {
        this.functionNumber = functionNumber;
        this.vendingMachineConsumer = vendingMachineConsumer;
    }

    public static void control(VendingMachine vendingMachine, Scanner scanner) {
        final int functionNumber = InputView.inputFromAdministratorView(scanner);
        final AdministratorController function = findFunction(functionNumber);
        function.vendingMachineConsumer.accept(vendingMachine, scanner);
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
