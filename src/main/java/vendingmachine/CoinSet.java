package vendingmachine;

import java.util.Arrays;
import java.util.List;

public class CoinSet {
    public static final List<Integer> coins = Arrays.asList(500, 100, 50, 10);

    public static boolean containsValue(int price){
        return coins.contains(price);
    }
}
