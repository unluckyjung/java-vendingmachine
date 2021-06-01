package vendingmachine.view;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {
    private static final Scanner SCANNER = new Scanner(System.in);
    public static final List<Integer> MODE_NUMBERS = Arrays.asList(1, 2);
    public static final List<Integer> PRODUCT_CUSTOM_NUMBERS = Arrays.asList(1, 2);
    public static final List<Integer> CUSTOMER_SELECT_NUMBERS = Arrays.asList(1, 2, 3);

    public static int getModeNumber() {
        System.out.println();
        System.out.print("관리자 모드는 1번, 사용자 모드는 2번을 입력해주세요 : ");
        int modeNumber = Integer.parseInt(SCANNER.nextLine());
        validateModeNumber(modeNumber);
        return modeNumber;
    }

    private static void validateModeNumber(final int modeNumber) {
        if (!MODE_NUMBERS.contains(modeNumber)) {
            throw new IllegalArgumentException("잘못된 번호를 입력하셨습니다.");
        }
    }

    public static int getProductCustomNumber() {
        System.out.print("상품 추가는 1번, 상품 삭제는 2번을 입력해주세요 : ");
        int productCustomNumber = Integer.parseInt(SCANNER.nextLine());
        validateProductCustomNumber(productCustomNumber);
        return productCustomNumber;
    }

    private static void validateProductCustomNumber(final int productCustomNumber) {
        if (!PRODUCT_CUSTOM_NUMBERS.contains(productCustomNumber)) {
            throw new IllegalArgumentException("잘못된 번호를 입력하셨습니다.");
        }
    }

    public static String getAddProductName() {
        System.out.print("추가할 상품의 이름을 입력해주세요 : ");
        return SCANNER.nextLine();
    }

    public static String getDeleteProductName() {
        System.out.print("삭제할 상품의 이름을 입력해주세요 : ");
        return SCANNER.nextLine();
    }

    public static String getBuyProductName() {
        System.out.print("구매할 상품의 이름을 입력해주세요 : ");
        return SCANNER.nextLine();
    }

    public static int getProductPrice() {
        System.out.print("추가할 상품의 가격을 입력해주세요 : ");
        return Integer.parseInt(SCANNER.nextLine());
    }

    public static int getCustomerSelectNumber() {
        System.out.print("상품 구매는 1번, 잔돈 반환은 2번, 동전 추가는 3번을 입력해주세요 : ");
        int customerSelectNumber = Integer.parseInt(SCANNER.nextLine());
        validateCustomerSelectNumber(customerSelectNumber);
        return customerSelectNumber;
    }

    private static void validateCustomerSelectNumber(final int customerSelectNumber) {
        if (!CUSTOMER_SELECT_NUMBERS.contains(customerSelectNumber)) {
            throw new IllegalArgumentException("잘못된 번호를 입력하셨습니다.");
        }
    }

    public static int insertCoin() {
        System.out.println("넣을 동전을 입력해주세요 [500원, 100원, 50원, 10원 종류만 입금 가능합니다.]");
        return Integer.parseInt(SCANNER.nextLine());
    }
}
