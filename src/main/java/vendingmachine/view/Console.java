package vendingmachine.view;

import com.woowahan.techcourse.utils.Scanners;
import java.util.Map;
import java.util.stream.Collectors;
import vendingmachine.domain.coin.Coin;

public class Console {

    public int askVendingMachineAmount() {
        System.out.println("자판기가 보유하고 있는 금액을 입력해 주세요.");
        return Scanners.nextInt();
    }

    public String askProducts() {
        System.out.println("\n상품명과 수량, 금액을 입력해 주세요.");
        return Scanners.next();
    }

    public int askAmount() {
        System.out.println("\n투입 금액을 입력해 주세요.");
        return Scanners.nextInt();
    }

    public String askProduct() {
        System.out.println("구매할 상품명을 입력해 주세요.");
        return Scanners.next();
    }

    public void printBalance(final int balance) {
        System.out.println(String.format("\n남은 금액: %d원", balance));
    }

    public void printChanges(final Map<Coin, Integer> changes) {
        System.out.println("잔돈");
        final String result = changes.entrySet().stream()
            .map(it -> String.format("%d원 - %d개", it.getKey().getAmount(), it.getValue()))
            .collect(Collectors.joining("\n"));
        System.out.println(result);
    }
}
