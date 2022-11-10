import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private Market market;

    public ClientHandler(Socket socket, Market market) {
        this.socket = socket;
        this.market = market;
    }

    @Override
    public void run() {
        int traderId = 0;
        try (
                Scanner scanner = new Scanner(socket.getInputStream());
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
            try {
                traderId = market.nextTraderId;
                System.out.println("New connection; trader ID " + traderId);
                writer.println(traderId);
                if (market.assignedStockholder == false) {
                    market.createTrader(traderId, 1);
                    market.nextTraderId++;
                    market.assignedStockholder = true;
                } else {
                    market.createTrader(traderId, 0);
                    market.nextTraderId++;
                }

                writer.println("SUCCESS");

                while (true) {
                    String line = scanner.nextLine();
                    String[] substrings = line.split(" ");
                    List<Integer> listOfTraders;
                    switch (substrings[0].toLowerCase()) {
                        case "traders":
                            listOfTraders = market.getListOfTraders();
                            writer.println(listOfTraders.size());
                            for (Integer trader : listOfTraders)
                                writer.println(trader);
                            break;

                        case "stock":
                            int trader = Integer.parseInt(substrings[1]);
                            writer.println(market.checkStockholder(trader));
                            break;

                        case "find":
                            listOfTraders = market.getListOfTraders();
                            int stockholder = 0;
                            for (Integer traderFind : listOfTraders)
                                if (market.checkStockholder(traderFind) == 1) {
                                    stockholder = traderFind;
                                }
                            writer.println(stockholder);
                            break;

                        case "transfer":
                            int traderIdFrom = Integer.parseInt(substrings[1]);
                            int traderIdTo = Integer.parseInt(substrings[2]);
                            market.transferStock(traderId, traderIdFrom, traderIdTo);
                            writer.println("SUCCESS");
                            break;

                        default:
                            throw new Exception("Unknown command: " + substrings[0]);
                    }
                }
            } catch (Exception e) {
                writer.println("ERROR " + e.getMessage());
                socket.close();
            }
        } catch (Exception ignored) {
        } finally {
            System.out.println("Trader " + traderId + " disconnected.");
            market.removeTrader(traderId);
        }
    }
}
