public class Trader {
    private final int traderId;
    private int stockholder;

    public Trader(int traderId) {
        this.traderId = traderId;
    }

    public int getTraderId() {
        return traderId;
    }

    public int isStockholder() {
        return stockholder;
    }

    public void setStockholder(int stockholderChange) {
        stockholder = stockholderChange;
    }
}
