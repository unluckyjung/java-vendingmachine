package vendingmachine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mockStatic;

import com.woowahan.techcourse.utils.Randoms;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.Duration;
import org.assertj.core.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

public class ApplicationTest {

    private static final Duration SIMPLE_TEST_TIMEOUT = Duration.ofSeconds(1L);

    private OutputStream captor;

    private void subject(final String... args) {
        command(args);
        Application.main(new String[]{});
    }

    private void command(final String... args) {
        final byte[] buf = Strings.join(args).with("\n").getBytes();
        System.setIn(new ByteArrayInputStream(buf));
    }

    private void assertSimpleTest(final Executable executable) {
        assertTimeoutPreemptively(SIMPLE_TEST_TIMEOUT, executable);
    }

    @BeforeEach
    void setUp() {
        captor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captor));
    }

    @DisplayName("자판기 보유 금액 실패테스트")
    @Nested
    class MachineInitMoneyFailTest {

        @DisplayName("자판기 초기 보유 금액이 0을 포함한 양의 정수가 아닌경우, 예외가 발생한다.")
        @ParameterizedTest
        @ValueSource(strings = {"-1", "-132"})
        void invalidMachineMoneyTest1(final String vendingMachineMoney) {
            assertSimpleTest(() -> assertThatThrownBy(() -> subject(
                vendingMachineMoney
            )).isExactlyInstanceOf(IllegalArgumentException.class));
        }

        @DisplayName("자판기 초기 보유 금액이 숫자가 아닌경우, 예외가 발생한다.")
        @ParameterizedTest
        @ValueSource(strings = {"jason", "jjang", "{"})
        void invalidMachineMoneyTest2(final String vendingMachineMoney) {
            assertSimpleTest(() -> assertThatThrownBy(() -> subject(
                vendingMachineMoney
            )).isInstanceOf(Exception.class));
        }
    }

    @DisplayName("상품 등록 실패테스트")
    @Nested
    class ProductFailTest {

        @DisplayName("상품 이름이 공백이면, 예외가 발생한다.")
        @ParameterizedTest
        @ValueSource(strings = {" ", ""})
        void invalidNameTest1(final String productName) {
            String vendingMachineMoney = "10000";

            assertSimpleTest(() -> assertThatThrownBy(() -> subject(
                vendingMachineMoney,
                String.format("[%s,20,1000]", productName)
            )).isInstanceOf(Exception.class));
        }

        @DisplayName("중복된 상품 이름이면, 예외가 발생한다.")
        @ParameterizedTest
        @CsvSource({"콜라,콜라"})
        void duplicateNameTest(final String productName1, final String productName2) {
            String vendingMachineMoney = "10000";

            assertSimpleTest(() -> assertThatThrownBy(() -> subject(
                vendingMachineMoney,
                String.format("[%s,20,1500];[%s,20,1000]", productName1, productName2)
            )).isInstanceOf(Exception.class));
        }

        @DisplayName("수량이 0을 포함하는 정수가 아니면, 예외가 발생한다.")
        @ParameterizedTest
        @ValueSource(strings = {"-1000", "-1", "abc"})
        void invalidQuantityTest(final String productQuantity) {
            String vendingMachineMoney = "10000";

            assertSimpleTest(() -> assertThatThrownBy(() -> subject(
                vendingMachineMoney,
                String.format("[콜라,20,1500];[사이다,%s,1000]", productQuantity)
            )).isExactlyInstanceOf(IllegalArgumentException.class));
        }

        @DisplayName("상품 가격이 양의 정수가 아니면, 예외가 발생한다.")
        @ParameterizedTest
        @ValueSource(strings = {"-1000", "-1", "abc",})
        void invalidPriceTest1(final String productPrice) {
            String vendingMachineMoney = "10000";

            assertSimpleTest(() -> assertThatThrownBy(() -> subject(
                vendingMachineMoney,
                String.format("[콜라,20,%s]", productPrice)
            )).isExactlyInstanceOf(IllegalArgumentException.class));
        }

        @DisplayName("상품 가격이 100원 이하의 양의 정수이면, 예외가 발생한다.")
        @ParameterizedTest
        @ValueSource(strings = {"1", "99"})
        void invalidPriceTest2(final String productPrice) {
            String vendingMachineMoney = "10000";

            assertSimpleTest(() -> assertThatThrownBy(() -> subject(
                vendingMachineMoney,
                String.format("[콜라,20,%s]", productPrice)
            )).isInstanceOf(IllegalArgumentException.class));
        }

        @DisplayName("상품 가격이 10으로 나누어 떨어지지 않으면, 예외가 발생한다.")
        @ParameterizedTest
        @ValueSource(strings = {"101", "109"})
        void invalidPriceTest3(final String productPrice) {
            String vendingMachineMoney = "10000";

            assertSimpleTest(() -> assertThatThrownBy(() -> subject(
                vendingMachineMoney,
                String.format("[콜라,20,%s]", productPrice)
            )).isInstanceOf(IllegalArgumentException.class));
        }
    }

    @DisplayName("투입 금액 실패테스트")
    @Nested
    class InputMoneyTest {

        @DisplayName("투입 금액이 0을 포함하는 정수가 아니면, 예외가 발생한다.")
        @ParameterizedTest
        @ValueSource(strings = {"-1", "-100"})
        void invalidInputMoneyTest(final String inputMoney) {
            String vendingMachineMoney = "10000";
            String productInfos = "[콜라,20,1500]";

            assertSimpleTest(() -> assertThatThrownBy(() -> subject(
                vendingMachineMoney,
                productInfos,
                inputMoney
            )).isExactlyInstanceOf(IllegalArgumentException.class));
        }

        @DisplayName("투입 금액이 숫자가 아니면, 예외가 발생한다.")
        @ParameterizedTest
        @ValueSource(strings = {"a", "A", "}"})
        void invalidInputMoneyTest2(final String inputMoney) {
            String vendingMachineMoney = "10000";
            String productInfos = "[콜라,20,1500]";

            assertSimpleTest(() -> assertThatThrownBy(() -> subject(
                vendingMachineMoney,
                productInfos,
                inputMoney
            )).isInstanceOf(Exception.class));
        }
    }

    @DisplayName("상품 구매 실패테스트")
    @Nested
    class ProductBuyTest {

        @DisplayName("없는 상품을 구매하려고 하면 예외가 발생한다.")
        @ParameterizedTest
        @ValueSource(strings = {"꼴라", "싸이다"})
        void invalidProductNameTest(final String productName) {
            String vendingMachineMoney = "10000";
            String productInfos = "[콜라,20,1500];[사이다,10,1000]";
            String inputMoney = "3000";

            assertSimpleTest(() -> assertThatThrownBy(() -> subject(
                vendingMachineMoney,
                productInfos,
                inputMoney,
                productName
            )).isExactlyInstanceOf(IllegalArgumentException.class));
        }
    }

    @DisplayName("상품 수량 이상의 상품개수를 구매하려고 하면, 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({"콜라,1,2", "사이다,1,100"})
    void invalidQuantityTest(final String productName, final int productQuantity, final int tryBuyCount) {
        String vendingMachineMoney = "10000";
        String productInfos = String.format("[%s,%s,1500]", productName, productQuantity);
        String inputMoney = "3000";

        StringBuilder buyString = new StringBuilder();

        for (int i = 0; i < tryBuyCount; ++i) {
            buyString.append(productName);
        }

        assertSimpleTest(() -> assertThatThrownBy(() -> subject(
            vendingMachineMoney,
            productInfos,
            inputMoney,
            buyString.toString()
        )).isExactlyInstanceOf(IllegalArgumentException.class));
    }

    @DisplayName("기능 테스트")
    @Nested
    class FunctionTest {

        @DisplayName("남은 금액보다, 최소 상품 금액이 더 크다면, 반환이 가능한내에서 잔돈(동전)을 반환한다.")
        @Test
        void noMoneyReturnTest() {
            String[] arguments = {"450", "[콜라,1,1500];[사이다,1,1000]", "3000", "콜라", "사이다"};

            try (MockedStatic<Randoms> mock = mockStatic(Randoms.class)) {
                mock.when(() -> Randoms.pickNumberInList(anyList())).thenReturn(100, 100, 100, 100, 50);
                subject(
                    arguments
                );
            }

            String output = captor.toString().trim();
            assertThat(output).contains("남은 금액: 50원");
            assertThat(output).contains("100원 - 4개");
            assertThat(output).contains("50원 - 1개");
        }

        @DisplayName("상품을 구매한뒤 모든 상품이 소진되면, 반환이 가능한내에서 잔돈(동전)을 반환한다.")
        @Test
        void noProductReturnTest() {
            String[] arguments = {"950", "[콜라,1,1500]", "3000", "콜라"};

            try (MockedStatic<Randoms> mock = mockStatic(Randoms.class)) {
                mock.when(() -> Randoms.pickNumberInList(anyList())).thenReturn(500, 100, 100, 100, 100, 50);
                subject(
                    arguments
                );
            }

            String output = captor.toString().trim();
            assertThat(output).contains("남은 금액: 550원");
            assertThat(output).contains("500원 - 1개");
            assertThat(output).contains("100원 - 4개");
            assertThat(output).contains("50원 - 1개");
        }

        @DisplayName("상품이 처음부터 0개인경우, 바로 돈을 반환한다.")
        @Test
        void noProductReturnTest2() {
            // 0개를 입력할 수 있게 할것인가? 에 대한 논의를 가질것인지?
            String[] arguments = {"950", "[콜라,0,1500]", "1000"};

            try (MockedStatic<Randoms> mock = mockStatic(Randoms.class)) {
                mock.when(() -> Randoms.pickNumberInList(anyList())).thenReturn(500, 100, 100, 100, 100, 50);
                subject(
                    arguments
                );
            }

            String output = captor.toString().trim();
            assertThat(output).contains("남은 금액: 50원");
            assertThat(output).contains("500원 - 1개");
            assertThat(output).contains("100원 - 4개");
            assertThat(output).contains("50원 - 1개");
        }

        @DisplayName("동전을 돌려줄 수 있는 경우의 수가 많은경우, 최소 동전의 개수로 반환한다.")
        @Test
        void minimumCoinReturn() {
            String[] arguments = {"1600", "[콜라,0,1500]", "1000"};

            try (MockedStatic<Randoms> mock = mockStatic(Randoms.class)) {
                mock.when(() -> Randoms.pickNumberInList(anyList())).thenReturn(100, 100, 500, 500, 100, 100, 100, 50, 50);
                subject(
                    arguments
                );
            }

            String output = captor.toString().trim();
            assertThat(output).contains("남은 금액: 0원");
            assertThat(output).contains("500원 - 2개");
        }

        @DisplayName("자판기에 남은 동전이 남은 금액보다 커서, 동전(잔돈)으로 반환하지 못하는 경우, 잔돈이 반환되지 않는다.")
        @Test
        void noCoinTest() {
            String[] arguments = {"1100", "[콜라,0,1500]", "50"};

            try (MockedStatic<Randoms> mock = mockStatic(Randoms.class)) {
                mock.when(() -> Randoms.pickNumberInList(anyList())).thenReturn(500, 500, 100);
                subject(
                    arguments
                );
            }
            String output = captor.toString().trim();

            // 실패하는 상황
            assertThat(output).contains("남은 금액: 50원");
            assertThat(output).doesNotContain("500원");
            assertThat(output).doesNotContain("100원");
            assertThat(output).doesNotContain("50원");

            // 아래로 변경시 통과
            assertThat(output).contains("남은 금액: 50원");
            assertThat(output).contains("500원 - 0개");
            assertThat(output).contains("100원 - 0개");
        }
    }

    @DisplayName("상품 구매후, 남은 금액이 자판기가 가지고 보유하고 있는남은 잔돈(동전)을 자판기가 최대한 반환한다")
    @Test
    void largeInputTest() {
        String[] arguments = {"1600", "[콜라,1,1500]", "100000", "콜라"};

        try (MockedStatic<Randoms> mock = mockStatic(Randoms.class)) {
            mock.when(() -> Randoms.pickNumberInList(anyList())).thenReturn(500, 500, 100, 100, 100, 100, 100, 50, 50);
            subject(
                arguments
            );
        }

        String output = captor.toString().trim();
        assertThat(output).contains("남은 금액: 96900원");
        assertThat(output).contains("500원 - 2개");
        assertThat(output).contains("100원 - 5개");
        assertThat(output).contains("50원 - 2개");
    }
}
