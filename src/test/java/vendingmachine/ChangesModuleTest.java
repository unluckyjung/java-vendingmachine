package vendingmachine;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.assertj.core.api.ThrowableAssert;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class ChangesModuleTest {

    @DisplayName("입금 금액 출력")
    @Test
    void printSumOfCoinsTest() {

        // given
        ChangesModule changesModule = new ChangesModule(Money.from(600));

        // when
        final int money = changesModule.printCurrentMoney();

        // then
        assertThat(money).isEqualTo(600);
    }

    @DisplayName("차감 성공")
    @Test
    void withdrawTest() {

        // given
        final Money input = Money.from(600);
        final Money money = Money.from(350);
        ChangesModule changesModule = new ChangesModule(input);

        // when
        changesModule.withdraw(money);

        // then
        final int changes = changesModule.printCurrentMoney();
        assertThat(changes).isEqualTo(250);
    }

    @DisplayName("차감 실패 - 입금 금액보다 큰 차감액")
    @Test
    void withdrawOverMoneyTest() {

        // given
        final Money input = Money.from(600);
        final Money money = Money.from(650);

        // when
        final ThrowableAssert.ThrowingCallable callable = () -> new ChangesModule(input).withdraw(money);

        // then
        assertThatIllegalArgumentException().isThrownBy(callable)
                                            .withMessage("차감하려는 금액이 입금 금액보다 큽니다.");
    }

    @DisplayName("잔돈 반환 성공 - 10원 반환")
    @Test
    void getChangesReturnTenTest() {

        // given
        final ChangesModule changesModule = new ChangesModule(10);

        // when
        final List<Coin> coins = changesModule.getChanges();

        // then
        final int changes = changesModule.printCurrentMoney();
        assertThat(changes).isEqualTo(0);
        assertThat(coins).containsExactly(Coin.TEN);
    }

    @DisplayName("잔돈 반환 성공 - 50원 반환")
    @Test
    void getChangesReturnFiftyTest() {

        // given
        final ChangesModule changesModule = new ChangesModule(50);

        // when
        final List<Coin> coins = changesModule.getChanges();

        // then
        final int changes = changesModule.printCurrentMoney();
        assertThat(changes).isEqualTo(0);
        assertThat(coins).containsExactly(Coin.FIFTY);
    }

    @DisplayName("잔돈 반환 성공 - 1000원 반환")
    @Test
    void getChangesReturnThousandTest() {

        // given
        final ChangesModule changesModule = new ChangesModule(1000);

        // when
        final List<Coin> coins = changesModule.getChanges();

        // then
        final int changes = changesModule.printCurrentMoney();
        assertThat(changes).isEqualTo(0);
        assertThat(coins).containsExactly(Coin.FIVE_HUNDRED, Coin.FIVE_HUNDRED);
    }

    @DisplayName("잔돈 반환 성공 - 20원 반환")
    @Test
    void getChangesReturnTwentyTest() {

        // given
        final ChangesModule changesModule = new ChangesModule(20);

        // when
        final List<Coin> coins = changesModule.getChanges();

        // then
        final int changes = changesModule.printCurrentMoney();
        assertThat(changes).isEqualTo(0);
        assertThat(coins).containsExactly(Coin.TEN, Coin.TEN);
    }

    @DisplayName("잔돈 반환 성공 - 600원 반환")
    @Test
    void getChangesReturnSixHundredTest() {

        // given
        final ChangesModule changesModule = new ChangesModule(600);

        // when
        final List<Coin> coins = changesModule.getChanges();

        // then
        final int changes = changesModule.printCurrentMoney();
        assertThat(changes).isEqualTo(0);
        assertThat(coins).containsExactly(Coin.FIVE_HUNDRED, Coin.ONE_HUNDRED);
    }

    @DisplayName("잔돈 반환 성공 - 650원 반환")
    @Test
    void getChangesReturnSixHundredFiftyTest() {

        // given
        final ChangesModule changesModule = new ChangesModule(650);

        // when
        final List<Coin> coins = changesModule.getChanges();

        // then
        final int changes = changesModule.printCurrentMoney();
        assertThat(changes).isEqualTo(0);
        assertThat(coins).containsExactly(Coin.FIVE_HUNDRED, Coin.ONE_HUNDRED, Coin.FIFTY);
    }

    @DisplayName("잔돈 반환 성공 - 1원 반환")
    @Test
    void getChangesReturnNothingTest() {

        // given
        final ChangesModule changesModule = new ChangesModule(1);

        // when
        final List<Coin> coins = changesModule.getChanges();

        // then
        final int changes = changesModule.printCurrentMoney();
        assertThat(changes).isEqualTo(0);
        assertThat(coins).isEmpty();
    }

    @DisplayName("잔돈 반환 성공 - 659원 반환")
    @Test
    void getChangesReturnSixHundredFiftyNineTest() {

        // given
        final ChangesModule changesModule = new ChangesModule(659);

        // when
        final List<Coin> coins = changesModule.getChanges();

        // then
        final int changes = changesModule.printCurrentMoney();
        assertThat(changes).isEqualTo(0);
        assertThat(coins).containsExactly(Coin.FIVE_HUNDRED, Coin.ONE_HUNDRED, Coin.FIFTY);
    }
}
