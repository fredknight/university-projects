using System;
using System.Collections.Generic;

namespace Server
{
    public class Market
    {
        private readonly Dictionary<int, Trader> traders = new Dictionary<int, Trader>();

        public int nextTraderId = 1;
        public bool assignedStockholder = false;

        public void createTrader(int traderId, int stockholder)
        {
            Trader trader = new Trader(traderId);
            trader.setStockholder(stockholder);
            traders.Add(traderId, trader);
        }

        public int[] getListOfTraders() {
            List<int> result = new List<int>();

            foreach (Trader trader in traders.Values)
                result.Add(trader.getTraderId());

            return result.ToArray();
        }

        public int checkStockholder(int traderId) {
            if (!traders.ContainsKey(traderId))
                throw new Exception("Trader " + traderId + " + is not a valid trader, or the client has disconnected.");

            return traders[traderId].isStockholder();
        }

        public void transferStock(int traderId, int traderIdFrom, int traderIdTo) {
            lock (traders)
            {
                if (!traders.ContainsKey(traderId))
                    throw new Exception("Trader " + traderId + " is not a valid trader, or the client has disconnected.");
                if (!traders.ContainsKey(traderIdFrom))
                    throw new Exception("Trader " + traderIdFrom + " is not a valid trader, or the client has disconnected.");
                if (!traders.ContainsKey(traderIdTo))
                    throw new Exception("Trader " + traderIdTo + " is not a valid trader, or the client has disconnected.");
                if (traders[traderIdFrom].isStockholder() == 0)
                    throw new Exception("Trader " + traderId + " is not the current stockholder.");
                if (traderId != traderIdFrom)
                    throw new Exception("Trader can only transfer their stock.");
                traders[traderIdFrom].setStockholder(0);
                traders[traderIdTo].setStockholder(1);
                Console.WriteLine("Stock transferred to trader " + traderIdTo + ".");
            }
        }

        public void removeTrader(int traderId) {
            if (traders[traderId].isStockholder() == 1) {
                if (traders.Count >= 2) {
                    traders[traderId].setStockholder(0);
                    foreach (Trader trader in traders.Values)
                        if (traderId != trader.getTraderId()) {
                            traders[trader.getTraderId()].setStockholder(1);
                            Console.WriteLine("Stockholder trader " + traderId + " disconnected. " +
                                    "Stock transferred to trader " + trader.getTraderId() + ".");
                            break;
                        }
                } else {
                    assignedStockholder = false;
                    Console.WriteLine("Stockholder trader " + traderId + " disconnected. " +
                            "No other traders connected: Stock returned to market.");
                }
            }
            traders.Remove(traderId);
        }
    }
}