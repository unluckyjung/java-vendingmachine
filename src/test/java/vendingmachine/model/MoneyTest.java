package vendingmachine.model;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.assertj.core.api.ThrowableAssert;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class MoneyTest {

    @DisplayName("입금")
    @Test
    void addAllTest() {

        // given
        Coin[] coins = new Coin[]{Coin.TEN, Coin.FIFTY, Coin.FIVE_HUNDRED};
        Money money = Money.from(0);

        // when
        final Money addedMoney = money.addAll(coins);

        // then
        assertThat(addedMoney.getMoney()).isEqualTo(560);
    }


    @DisplayName("입금 금액 반환")
    @Test
    void printSumOfCoinsTest() {

        // given
        Money money = Money.from(1000);

        // when
        final int currentMoney = money.getMoney();

        // then
        assertThat(currentMoney).isEqualTo(1000);
    }

    @DisplayName("차감 성공")
    @Test
    void withdrawTest() {

        // given
        final Money input = Money.from(1000);
        final Money output = Money.from(500);

        // when
        final Money subtractedMoney = input.subtract(output);

        // then
        final int changes = subtractedMoney.getMoney();
        assertThat(changes).isEqualTo(500);
    }

    @DisplayName("차감 실패 - 입금 금액보다 큰 차감액")
    @Test
    void withdrawOverMoneyTest() {

        // given
        final Money input = Money.from(1000);
        final Money output = Money.from(1500);

        // when
        final ThrowableAssert.ThrowingCallable callable = () -> input.subtract(output);

        // then
        assertThatIllegalArgumentException().isThrownBy(callable)
                                            .withMessage("차감하려는 금액이 현재 입금 금액보다 큽니다.");
    }

    @DisplayName("잔돈 반환 성공 - 10원 반환")
    @Test
    void getChangesReturnTenTest() {

        // given
        Money money = Money.from(10);

        // when
        final List<Coin> changes = money.toCoins();

        // then
        assertThat(changes).containsExactly(Coin.TEN);
    }

    @DisplayName("잔돈 반환 성공 - 50원 반환")
    @Test
    void getChangesReturnFiftyTest() {

        // given
        Money money = Money.from(50);

        // when
        final List<Coin> changes = money.toCoins();

        // then
        assertThat(changes).containsExactly(Coin.FIFTY);
    }

    @DisplayName("잔돈 반환 성공 - 1000원 반환")
    @Test
    void getChangesReturnThousandTest() {

        // given
        Money money = Money.from(1000);

        // when
        final List<Coin> changes = money.toCoins();

        // then
        assertThat(changes).containsExactly(Coin.FIVE_HUNDRED, Coin.FIVE_HUNDRED);
    }

    @DisplayName("잔돈 반환 성공 - 20원 반환")
    @Test
    void getChangesReturnTwentyTest() {

        // given
        Money money = Money.from(20);

        // when
        final List<Coin> changes = money.toCoins();

        // then
        assertThat(changes).containsExactly(Coin.TEN, Coin.TEN);
    }

    @DisplayName("잔돈 반환 성공 - 600원 반환")
    @Test
    void getChangesReturnSixHundredTest() {

        // given
        Money money = Money.from(600);

        // when
        final List<Coin> changes = money.toCoins();

        // then
        assertThat(changes).containsExactly(Coin.FIVE_HUNDRED, Coin.ONE_HUNDRED);
    }

    @DisplayName("잔돈 반환 성공 - 650원 반환")
    @Test
    void getChangesReturnSixHundredFiftyTest() {

        // given
        Money money = Money.from(650);

        // when
        final List<Coin> changes = money.toCoins();

        // then
        assertThat(changes).containsExactly(Coin.FIVE_HUNDRED, Coin.ONE_HUNDRED, Coin.FIFTY);
    }

    @DisplayName("잔돈 반환 성공 - 1원 반환")
    @Test
    void getChangesReturnNothingTest() {

        // given
        Money money = Money.from(1);

        // when
        final List<Coin> changes = money.toCoins();

        // then
        assertThat(changes).isEmpty();
    }

    @DisplayName("잔돈 반환 성공 - 659원 반환")
    @Test
    void getChangesReturnSixHundredFiftyNineTest() {

        // given
        Money money = Money.from(659);

        // when
        final List<Coin> changes = money.toCoins();

        // then
        assertThat(changes).containsExactly(Coin.FIVE_HUNDRED, Coin.ONE_HUNDRED, Coin.FIFTY);
    }
}
