import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client implements AutoCloseable {
    final int port = 8888;

    private final Scanner reader;
    private final PrintWriter writer;

    public int traderId;

    public Client() throws Exception {
        // Connecting to the server and creating objects for communications
        Socket socket = new Socket("localhost", port);
        reader = new Scanner(socket.getInputStream());

        // Automatically flushes the stream with every command
        writer = new PrintWriter(socket.getOutputStream(), true);

        // Reading customer ID
        traderId = Integer.parseInt(reader.nextLine().trim());

        // Parsing the response
        String line = reader.nextLine();
        if (line.trim().compareToIgnoreCase("success") != 0)
            throw new Exception(line);

    }

    public int[] getTraderIds() {
        // Sending command
        writer.println("TRADERS");

        // Reading the number of traders
        String line = reader.nextLine();
        int numberOfTraders = Integer.parseInt(line);

        // Reading the trader IDs
        int[] traders = new int[numberOfTraders];
        for (int i = 0; i < numberOfTraders; i++) {
            line = reader.nextLine();
            traders[i] = Integer.parseInt(line);
        }

        return traders;
    }

    public int checkStockholder(int traderId) {
        // Writing the command
        writer.println("STOCK " + traderId);

        // Reading the trader stock
        String line = reader.nextLine();
        return Integer.parseInt(line);
    }

    public int findStockholder() {
        // Writing the command
        writer.println("FIND");

        // Reading the trader stock
        String line = reader.nextLine();
        return Integer.parseInt(line);
    }

    public void transfer(int traderIdFrom, int traderIdTo) throws Exception {
        // Writing the command
        writer.println("TRANSFER " + traderIdFrom + " " + traderIdTo);

        // Reading the response
        String line = reader.nextLine();
        if (line.trim().compareToIgnoreCase("success") != 0)
            throw new Exception(line);
    }

    @Override
    public void close() throws Exception {
        reader.close();
        writer.close();
    }
}
