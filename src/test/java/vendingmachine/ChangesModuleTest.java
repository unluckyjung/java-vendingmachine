package vendingmachine;

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
        ChangesModule changesModule = new ChangesModule(input).withdraw(money);

        // when
        final int changes = changesModule.printCurrentMoney();

        // then
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
}
