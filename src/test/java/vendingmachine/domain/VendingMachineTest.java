package vendingmachine.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class VendingMachineTest {
    @DisplayName("자판기에 돈을 넣으면, 잘들어간다.")
    @Test
    void insertCoinTest() {
        VendingMachine vendingMachine = new VendingMachine(new ChangeModule(), new Products());
        vendingMachine.insertCoin(CoinSet._500);
        assertThat(vendingMachine.getAmount()).isEqualTo(500);
    }

    @DisplayName("반환 요청을 하면, 전체 금액을 가장 적은 수의 동전으로 돌려준다.")
    @Test
    void withDrawToCoins() {
        VendingMachine vendingMachine = new VendingMachine(new ChangeModule(), new Products());
        vendingMachine.insertCoin(CoinSet._500);
        vendingMachine.insertCoin(CoinSet._500);

        List<CoinSet> expectedCoins = Arrays.asList(CoinSet._500, CoinSet._500);
        List<CoinSet> returnedCoins = vendingMachine.withDrawToCoins();

        assertThat(expectedCoins).usingRecursiveComparison().isEqualTo(returnedCoins);
    }

    @DisplayName("존재하지 않는 상품을 구매하면, 예외가 발생한다..")
    @Test
    void purchaseInvalidNameProduct() {
        VendingMachine vendingMachine = new VendingMachine(new ChangeModule(new Change(1000)), new Products());
        assertThatThrownBy(() -> {
            vendingMachine.buyProductByName("멍토");
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("존재하지 않는 상품을 구매할 수 없습니다.");
    }

    @DisplayName("상품을 이름을 통해 구매하면, 상품이 구매된다.")
    @Test
    void purchaseProduct() {
        VendingMachine vendingMachine = new VendingMachine(new ChangeModule(new Change(1000)), new Products());
        vendingMachine.addProduct(new Product("콜라", 1000));
        vendingMachine.buyProductByName("콜라");

        List<Product> expectedProducts = Arrays.asList(new Product("콜라", 1000));
        assertThat(expectedProducts).usingRecursiveComparison().isEqualTo(vendingMachine.getPurchasedProducts());
    }

    @DisplayName("가지고 있는 금액보다 비싼 상품을 구매하려고하면 예외가 발생한다.")
    @Test
    void purchaseInvalidPriceProduct() {
        VendingMachine vendingMachine = new VendingMachine(new ChangeModule(new Change(999)), new Products());
        vendingMachine.addProduct(new Product("콜라", 1000));
        assertThatThrownBy(() -> {
            vendingMachine.buyProductByName("콜라");
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
