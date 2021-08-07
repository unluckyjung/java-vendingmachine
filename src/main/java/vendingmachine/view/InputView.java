package vendingmachine.view;

import com.woowahan.techcourse.utils.Scanners;

public class InputView {

    public static int getVendingMachineInitMoney() {
        System.out.println("\n자판기가 보유하고 있는 금액을 입력해 주세요.");
        return Integer.parseInt(Scanners.nextLine());
    }

    public static String getProductInfo() {
        System.out.println("\n상품명과 수량, 금액을 입력해 주세요.");
        return Scanners.nextLine();
    }

    public static int getInputMoney() {
        System.out.println("\n투입할 금액을 입력해 주세요.");
        return Integer.parseInt(Scanners.nextLine());
    }

    public static String getProductName() {
        System.out.println("\n구매할 상품명을 입력해 주세요.");
        return Scanners.nextLine();
    }
}
