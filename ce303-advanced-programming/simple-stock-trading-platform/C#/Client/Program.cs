using System;
using System.Collections.Generic;
using System.Threading;

namespace Client
{
    class Program
    {
        static void Main(string[] args)
        {
            try
            {
                using (Client client = new Client())
                {
                    Console.WriteLine("Logged in successfully as trader " + client.traderId + ".");
                    Console.Title = "Trader " + client.traderId;
                    int traderCount = 0;
                    int[] oldTraderIds = new int[0];
                    int stockholderId = 0;
                    bool forceUpdate = false;
                    string user = "(You)";
                    while (true) {
                        int[] traderIds = client.getTraderIds();
                        if ((traderCount != traderIds.Length) || forceUpdate || (stockholderId != client.findStockholder())) {
                            forceUpdate = false;
                            if ((traderCount != traderIds.Length)) {
                                Console.WriteLine("----------------------------------");
                                if (traderCount > traderIds.Length) {
                                    List<int> list = new List<int>();
                                    foreach (int entry in traderIds) {
                                        list.Add(entry);
                                    }
                                    foreach (int id in oldTraderIds) {
                                        if (!list.Contains(id)) {
                                            Console.WriteLine("Trader ID " + id + " Disconnected.");
                                        }
                                    }
                                } else {
                                    Console.WriteLine("Trader ID " + traderIds[traderIds.Length - 1] + " Connected.");
                                }
                            }
                            Console.WriteLine("----------------------------------");
                            Console.WriteLine("Connected Traders:");
                            foreach (int trader in traderIds) {
                                if (trader == client.traderId)
                                    Console.WriteLine($"  Trader {trader,5}: Stocks {client.checkStockholder(trader),5} {user,10}");
                                else
                                    Console.WriteLine($"  Trader {trader,5}: Stocks {client.checkStockholder(trader),5}");
                            }
                            traderCount = traderIds.Length;
                            oldTraderIds = traderIds;

                            Console.WriteLine("----------------------------------");
                            Console.WriteLine("Current Stockholder:");
                            if (stockholderId != client.findStockholder()) {
                                if (client.traderId == client.findStockholder())
                                    Console.WriteLine("New Stockholder: You are the current stockholder.");
                                else
                                    Console.WriteLine($"  Trader {client.findStockholder(),5}");
                                stockholderId = client.findStockholder();
                            } else {
                                if (client.traderId == stockholderId)
                                    Console.WriteLine("You are the current stockholder.");
                                else
                                    Console.WriteLine($"  Trader {stockholderId,5}");
                            }
                        }

                        if (client.checkStockholder(client.traderId) == 1) {
                            Thread.Sleep(1000);
                            Console.WriteLine("\nChoose a trader to trade stocks with (T), or check active traders for updates (U):");
                            String choice = Console.ReadLine().Trim().ToUpper();
                            switch (choice) {
                                case "T":
                                    Console.WriteLine("\nEnter the trader ID number to transfer to or type 'BACK' to return to the trader list:");
                                    String response = Console.ReadLine();
                                    if (!(response.Trim().ToUpper() == ("BACK"))) {
                                        int traderIdFrom = client.traderId;
                                        int traderIdTo = int.Parse(response);
                                        client.transfer(traderIdFrom, traderIdTo);
                                    }
                                    forceUpdate = true;
                                    break;

                                case "U":
                                    forceUpdate = true;
                                    break;

                                default:
                                    Console.WriteLine("----------------------------------");
                                    Console.WriteLine("Unknown command: " + choice);
                                    break;
                            }
                        }
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }
    }
}