package vendingmachine;

public class Change {
    private int amount;

    public void addAmount(final int amount){
        this.amount += amount;
    }

    public void reduce(final int price) {
        this.amount -= price;
    }

    public int getAmount() {
        return amount;
    }
}
