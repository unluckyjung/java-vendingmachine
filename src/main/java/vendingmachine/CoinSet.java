package vendingmachine;

import java.util.Arrays;
import java.util.List;

public class CoinSet {
    private static final List<Integer> coins = Arrays.asList(10, 50, 100, 500);

    public static boolean containsValue(int price){
        return coins.contains(price);
    }
}
