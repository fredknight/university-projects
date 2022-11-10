using System;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Threading;

namespace Server
{
    class Program
    {
        private const int port = 8888;

        private static readonly Market market = new Market();

        static void Main(string[] args)
        {
            RunServer();
        }

        private static void RunServer()
        {
            TcpListener listener = new TcpListener(IPAddress.Loopback, port);
            listener.Start();
            Console.WriteLine("Waiting for incoming connections...");
            while (true)
            {
                TcpClient tcpClient = listener.AcceptTcpClient();
                new Thread(HandleIncomingConnection).Start(tcpClient);
            }
        }

        private static void HandleIncomingConnection(object param)
        {
            TcpClient tcpClient = (TcpClient) param;
            using (Stream stream = tcpClient.GetStream())
            {
                StreamWriter writer = new StreamWriter(stream);
                StreamReader reader = new StreamReader(stream);
                int traderId = 0;
                try
                {
                    traderId = market.nextTraderId;
                    Console.WriteLine($"New connection; customer ID {traderId}");
                    writer.WriteLine(traderId);
                    writer.Flush();
                    if (market.assignedStockholder == false) {
                        market.createTrader(traderId, 1);
                        market.nextTraderId++;
                        market.assignedStockholder = true;
                    } else {
                        market.createTrader(traderId, 0);
                        market.nextTraderId++;
                    }
                    writer.WriteLine("SUCCESS");
                    writer.Flush();

                    while (true)
                    {
                        string line = reader.ReadLine();
                        string[] substrings = line.Split(' ');
                        int[] listOfTraders;
                        switch (substrings[0].ToLower())
                        {
                            case "traders":
                                listOfTraders = market.getListOfTraders();
                                writer.WriteLine(listOfTraders.Length);
                                foreach (int listId in listOfTraders)
                                    writer.WriteLine(listId);
                                writer.Flush();
                                break;

                            case "stock":
                                int trader = int.Parse(substrings[1]);
                                writer.WriteLine(market.checkStockholder(trader));
                                writer.Flush();
                                break;

                            case "find":
                                listOfTraders = market.getListOfTraders();
                                int stockholder = 0;
                                foreach (int traderFind in listOfTraders)
                                    if (market.checkStockholder(traderFind) == 1) {
                                        stockholder = traderFind;
                                    }
                                writer.WriteLine(stockholder);
                                writer.Flush();
                                break;

                            case "transfer":
                                int traderIdFrom = int.Parse(substrings[1]);
                                int traderIdTo = int.Parse(substrings[2]);
                                market.transferStock(traderId, traderIdFrom, traderIdTo);
                                writer.WriteLine("SUCCESS");
                                writer.Flush();
                                break;

                            default:
                                throw new Exception($"Unknown command: {substrings[0]}.");
                        }
                    }
                }
                catch (Exception e)
                {
                    try
                    {
                        writer.WriteLine("ERROR " + e.Message);
                        writer.Flush();
                        tcpClient.Close();
                    }
                    catch
                    {
                        Console.WriteLine("Failed to send error message.");
                    }
                }
                finally
                {
                    Console.WriteLine($"Customer {traderId} disconnected.");
                    market.removeTrader(traderId);
                }
            }
        }
    }
}