package vendingmachine.view;

import java.util.Scanner;

public class InputView {
    public static final Scanner SCANNER = new Scanner(System.in);
    public static final String MAIN_VIEW = "\n메인 화면\n===\n1. 관리자 모드\n2. 사용자 모드\n3. 종료\n";
    public static final String ADMINISTRATOR_VIEW = "\n관리자 화면\n===\n1. 상품 등록\n2. 상품 삭제\n3. 처음으로\n";
    public static final String CLIENT_VIEW = "\n사용자 화면\n===\n1. 입금\n2. 상품 구입\n3. 잔돈 반환\n4. 처음으로\n";

    public static final String ADD_PRODUCT_VIEW = "\n등록할 상품의 이름과 가격을 입력해주세요.";
    public static final String REMOVE_PRODUCT_VIEW = "\n삭제할 상품의 이름을 입력해주세요.";

    public static final String INSERT_MONEY_VIEW = "\n입금하실 금액을 입력해주세요.";
    public static final String BUY_PRODUCT_VIEW = "\n구매할 상품의 이름을 입력해주세요.";

    public static String input(String message, Scanner scanner) {
        System.out.println(message);
        return scanner.nextLine();
    }

    public static int inputFromMainView(Scanner scanner) {
        return Integer.parseInt(input(MAIN_VIEW, scanner));
    }

    public static int inputFromAdministratorView(Scanner scanner) {
        return Integer.parseInt(input(ADMINISTRATOR_VIEW, scanner));
    }

    public static int inputFromClientView(Scanner scanner) {
        return Integer.parseInt(input(CLIENT_VIEW, scanner));
    }

    public static String inputFromAddProductView(Scanner scanner) {
        return input(ADD_PRODUCT_VIEW, scanner);
    }

    public static String inputFromRemoveProductView(Scanner scanner) {
        return input(REMOVE_PRODUCT_VIEW, scanner);
    }

    public static String inputFromBuyProductView(Scanner scanner) {
        return input(BUY_PRODUCT_VIEW, scanner);
    }

    public static String inputFromInsertMoneyView(Scanner scanner) {
        return input(INSERT_MONEY_VIEW, scanner);
    }
}
