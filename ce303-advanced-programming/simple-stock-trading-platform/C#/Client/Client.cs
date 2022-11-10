using System;
using System.IO;
using System.Net.Sockets;

namespace Client
{
    class Client : IDisposable
    {
        const int port = 8888;

        private readonly StreamReader reader;
        private readonly StreamWriter writer;

        public int traderId;

        public Client()
        {
            // Connecting to the server and creating objects for communications
            TcpClient tcpClient = new TcpClient("localhost", port);
            NetworkStream stream = tcpClient.GetStream();
            reader = new StreamReader(stream);
            writer = new StreamWriter(stream);

            // Reading customer ID
            traderId = int.Parse(reader.ReadLine());

            // Parsing the response
            string line = reader.ReadLine();
            if (line.Trim().ToLower() != "success")
                throw new Exception(line);
        }

        public int[] getTraderIds()
        {
            // Sending command
            writer.WriteLine("TRADERS");
            writer.Flush();

            // Reading the number of traders
            string line = reader.ReadLine();
            int numberOfTraders = int.Parse(line);

            // Reading the trader IDs
            int[] traders = new int[numberOfTraders];
            for (int i = 0; i < numberOfTraders; i++)
            {
                line = reader.ReadLine();
                traders[i] = int.Parse(line);
            }

            return traders;
        }

        public int checkStockholder(int traderId)
        {
            // Writing the command
            writer.WriteLine("STOCK " + traderId);
            writer.Flush();

            // Reading the trader stock
            string line = reader.ReadLine();
            return int.Parse(line);
        }

        public int findStockholder() {
        // Writing the command
        writer.WriteLine("FIND");
        writer.Flush();

        // Reading the trader stock
        String line = reader.ReadLine();
        return int.Parse(line);
        }

        public void transfer(int traderIdFrom, int traderIdTo)
        {
            // Writing the command
            writer.WriteLine($"TRANSFER {traderIdFrom} {traderIdTo}");
            writer.Flush();

            // Reading the response
            string line = reader.ReadLine();
            if (line.Trim().ToLower() != "success")
                throw new Exception(line);
        }

        public void Dispose()
        {
            reader.Close();
            writer.Close();
        }
    }
}