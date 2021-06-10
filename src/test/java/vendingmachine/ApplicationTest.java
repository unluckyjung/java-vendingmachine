package vendingmachine;

import org.assertj.core.util.Strings;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

public class ApplicationTest {
    private static final Duration SIMPLE_TEST_TIMEOUT = Duration.ofSeconds(1L);
    private static final Duration RANDOM_TEST_TIMEOUT = Duration.ofSeconds(10L);

    private static final String MAIN_EXIT_NUMBER = "3";

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

    private PrintStream standardOut;
    private OutputStream captor;

    @BeforeEach
    void setUp() {
        standardOut = System.out;
        captor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captor));
    }

    @DisplayName("메인 메뉴 실패 테스트")
    @Nested
    class MainMenuTestFail {

        @DisplayName("메인메뉴 잘못 선택한 경우 테스트")
        @ParameterizedTest
        @ValueSource(strings = {"4", "5", "-1"})
        void 잘못된_메인메뉴_숫자_선택(final String selectNumber) {
            // DONE : 스택오버플로우가 나오는 상황을 테스터가 수정함
            assertSimpleTest(() -> {
                subject(
                        // 메인화면
                        selectNumber,
                        MAIN_EXIT_NUMBER
                );
                assertThat(captor.toString().trim()).contains("없는 메뉴 번호입니다.");
            });
        }

        @DisplayName("메인메뉴 잘못 선택한 경우 테스트")
        @ParameterizedTest
        @ValueSource(strings = {"a", "b"})
        void 실패_하는테스트_잘못된_메인메뉴_문자_선택(final String selectNumber) {
            // 숫자가 아닌 문자가 입력인 경우에 대해서 예외처리가 일관적이지 못하다.

            assertSimpleTest(() -> {
                subject(
                        selectNumber,
                        MAIN_EXIT_NUMBER
                );
                assertThat(captor.toString().trim()).contains("없는 메뉴 번호입니다.");
            });
        }
    }


    @DisplayName("관리자 모드 잘못된 입력 테스트")
    @Nested
    class OwnerFailTest {

        @DisplayName("관리자 화면에서 잘못된 메뉴를 선택하면 예외가 발생한다.")
        @ParameterizedTest
        @ValueSource(strings = {"-1", "5", "a"})
            // -1인 경우는 IllegalArgument
            // 5, a의 경우는 스택 오버플로우 에러가 발생한다.
        void 잘못된_메뉴_선택_테스트(final String menuNumber) {
            assertSimpleTest(() -> {
                assertThatThrownBy(() ->
                        subject(
                                // 메인화면 (사용자 화면 선택)
                                "1",
                                // 사용자 화면 (입금 선택)
                                menuNumber
                        )
                ).isInstanceOf(IllegalArgumentException.class);
            });
        }

        @DisplayName("상품가격이 음수이면, 예외가 발생한다.")
        @ParameterizedTest
        @CsvSource({"시드,-1", "포츈,-2"})
        void 잘못된_상품가격_테스트(final String name, final String price) {
            assertSimpleTest(() -> {
                assertThatThrownBy(() ->
                        subject("1", "1", name + " " + price)
                ).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("돈은 음수가 될 수 없습니다.");
            });
        }


        @DisplayName("상품가격이 숫자가 아니면, 예외가 발생한다.")
        @ParameterizedTest
        @CsvSource({"시드,aa", "포츈,bb"})
        void 문자_상품가격_테스트(final String name, final String price) {
            assertSimpleTest(() -> {
                assertThatThrownBy(() ->
                        subject("1", "1", name + " " + price)
                ).isInstanceOf(IllegalArgumentException.class);
            });
        }

        @DisplayName("중복된 이름의 삼품을 등록하려고 하면, 예외가 발생한다.")
        @ParameterizedTest
        @CsvSource({"콜라,300"})
        void 상품_잘못된_등록_테스트(final String name, final String price) {
            assertSimpleTest(() -> {
                assertThatThrownBy(() -> {
                    subject(
                            // 상품 등록
                            "1", "1", name + " " + price,
                            // 종료
                            MAIN_EXIT_NUMBER
                    );
                }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("이미 자판기에 등록되어 있는 상품입니다.");
            });
        }

        @DisplayName("등록되지 않은 상품 삭제 테스트")
        @ParameterizedTest
        @ValueSource(strings = {"시드", "포츈"})
        void 상품_잘못된_삭제_테스트(final String name) {
            // 예외처리가 일관적이지 못하다. ArrayIndexOutOfBoundsException 가 의도된것인지?
            assertSimpleTest(() -> {
                assertThatThrownBy(() -> {
                    subject(
                            // 관리자 화면 접근
                            "1",
                            // 상품 삭제
                            "1", "2", name,
                            // 종료
                            MAIN_EXIT_NUMBER
                    );
                }).isInstanceOf(ArrayIndexOutOfBoundsException.class);
            });
        }
    }

    @DisplayName("관리자 화면 기능 테스트")
    @Nested
    class OwnerTest {

        @DisplayName("상품 등록 테스트")
        @ParameterizedTest
        @CsvSource({"시드,300"})
        void 상품_정상_등록_테스트(final String name, final String price) {
            assertSimpleTest(() -> {
                subject(
                        // 상품 등록
                        "1", "1", name + " " + price,
                        // 종료
                        MAIN_EXIT_NUMBER
                );
                assertThat(captor.toString().trim()).contains(name, price);
            });
        }

        @DisplayName("상품 등록, 삭제 테스트")
        @ParameterizedTest
        @CsvSource({"시드,300"})
        void 상품_정상_삭제_테스트(final String name, final String price) {
            assertSimpleTest(() -> {
                subject(
                        // 상품 등록
                        "1", "1", name + " " + price,
                        // 상품 삭제
                        "1", "2", name,
                        // 종료
                        MAIN_EXIT_NUMBER
                );
            });
        }
    }

    @DisplayName("사용자 화면 기능 테스트")
    @Nested
    class UserFunctionTest {

        @DisplayName("사용자 화면에서 잘못된 메뉴를 선택하면 예외가 발생한다.")
        @ParameterizedTest
        @ValueSource(strings = {"-1", "5", "a"})
            // -1 , 5인 경우는 스택 오버플로우 발생
        void 잘못된_메뉴_선택_테스트(final String menuNumber) {
            assertSimpleTest(() -> {
                assertThatThrownBy(() ->
                        subject(
                                // 메인화면 (사용자 화면 선택)
                                "2",
                                menuNumber
                        )
                ).isInstanceOf(IllegalArgumentException.class);
            });
        }

        @DisplayName("잘못된 금액을 입금하면 예외가 발생한다.")
        @ParameterizedTest
        @ValueSource(strings = {"-1", "5", "a"})
        void 잘못된_금액_입금_테스트(final String amount) {
            assertSimpleTest(() -> {
                assertThatThrownBy(() ->
                        subject(
                                // 메인화면 (사용자 화면 선택)
                                "2",
                                // 사용자 화면 (입금 선택)
                                "1",
                                // 입금할 금액
                                amount
                        )
                ).isInstanceOf(IllegalArgumentException.class);
            });
        }

        @DisplayName("올바른 금액을 입금하면, 돈이 들어간다.")
        @ParameterizedTest
        @ValueSource(strings = {"100", "500"})
        void 올바른_금액_입금_테스트(final String amount) {
            assertSimpleTest(() -> {
                subject(
                        // 메인화면 (사용자 화면 선택)
                        "2",
                        // 사용자 화면 (입금 선택)
                        "1",
                        // 입금할 금액
                        amount,
                        MAIN_EXIT_NUMBER
                );
                assertThat(captor.toString().trim()).contains(amount + "원");
            });
        }
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}
