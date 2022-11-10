import java.util.*;

public class Market {
    private final Map<Integer, Trader> traders = new TreeMap<>();

    public int nextTraderId = 1;
    public boolean assignedStockholder = false;

    public void createTrader(int traderId, int stockholder)
    {
        Trader trader = new Trader(traderId);
        trader.setStockholder(stockholder);
        traders.put(traderId, trader);
    }

    public List<Integer> getListOfTraders() {
        List<Integer> result = new ArrayList<Integer>();

        for (Trader trader : traders.values())
            result.add(trader.getTraderId());

        return result;
    }

    public int checkStockholder(int traderId) throws Exception {
        if (!traders.containsKey(traderId))
            throw new Exception("Trader " + traderId + " + is not a valid trader, or the client has disconnected.");

        return traders.get(traderId).isStockholder();
    }

    public void transferStock(int traderId, int traderIdFrom, int traderIdTo) throws Exception {
        synchronized (traders)
        {
            if (!traders.containsKey(traderId))
                throw new Exception("Trader " + traderId + " is not a valid trader, or the client has disconnected.");
            if (!traders.containsKey(traderIdFrom))
                throw new Exception("Trader " + traderIdFrom + " is not a valid trader, or the client has disconnected.");
            if (!traders.containsKey(traderIdTo))
                throw new Exception("Trader " + traderIdTo + " is not a valid trader, or the client has disconnected.");
            if (traders.get(traderIdFrom).isStockholder() == 0)
                throw new Exception("Trader " + traderId + " is not the current stockholder.");
            if (traderId != traderIdFrom)
                throw new Exception("Trader can only transfer their stock.");
            traders.get(traderIdFrom).setStockholder(0);
            traders.get(traderIdTo).setStockholder(1);
            System.out.println("Stock transferred to trader " + traderIdTo + ".");
        }
    }

    public void removeTrader(int traderId) {
        if (traders.get(traderId).isStockholder() == 1) {
            if (traders.size() >= 2) {
                traders.get(traderId).setStockholder(0);
                for (Trader trader : traders.values())
                    if (traderId != trader.getTraderId()) {
                        traders.get(trader.getTraderId()).setStockholder(1);
                        System.out.println("Stockholder trader " + traderId + " disconnected. " +
                                "Stock transferred to trader " + trader.getTraderId() + ".");
                        break;
                    }
            } else {
                assignedStockholder = false;
                System.out.println("Stockholder trader " + traderId + " disconnected. " +
                        "No other traders connected: Stock returned to market.");
            }
        }
        traders.remove(traderId);
    }
}
