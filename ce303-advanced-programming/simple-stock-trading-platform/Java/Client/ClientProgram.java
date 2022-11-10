import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientProgram {
    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(System.in);
            try (Client client = new Client()) {
                System.out.println("Logged in successfully as trader " + client.traderId + ".");
                int traderCount = 0;
                int[] oldTraderIds = new int[0];
                int stockholderId = 0;
                boolean forceUpdate = false;
                while (true) {
                    int[] traderIds = client.getTraderIds();
                    if ((traderCount != traderIds.length) || forceUpdate || (stockholderId != client.findStockholder())) {
                        forceUpdate = false;
                        if ((traderCount != traderIds.length)) {
                            System.out.println("----------------------------------");
                            if (traderCount > traderIds.length) {
                                List<Integer> list = new ArrayList<>();
                                for (int entry : traderIds) {
                                    assert false;
                                    list.add(entry);
                                }
                                for (int id : oldTraderIds) {
                                    if (!list.contains(id)) {
                                        System.out.println("Trader ID " + id + " Disconnected.");
                                    }
                                }
                            } else {
                                System.out.println("Trader ID " + traderIds[traderIds.length - 1] + " Connected.");
                            }
                        }
                        System.out.println("----------------------------------");
                        System.out.println("Connected Traders:");
                        for (int trader : traderIds) {
                            if (trader == client.traderId)
                                System.out.printf("  Trader %5d: Stocks %5d %10s%n", trader, client.checkStockholder(trader), "(You)");
                            else
                                System.out.printf("  Trader %5d: Stocks %5d%n", trader, client.checkStockholder(trader));
                        }
                        traderCount = traderIds.length;
                        oldTraderIds = traderIds;

                        System.out.println("----------------------------------");
                        System.out.println("Current Stockholder:");
                        if (stockholderId != client.findStockholder()) {
                            if (client.traderId == client.findStockholder())
                                System.out.println("New Stockholder: You are the current stockholder.");
                            else
                                System.out.printf("New Stockholder: Trader %5d%n", client.findStockholder());
                            stockholderId = client.findStockholder();
                        } else {
                            if (client.traderId == stockholderId)
                                System.out.println("You are the current stockholder.");
                            else
                                System.out.printf("Trader %5d%n", stockholderId);
                        }
                    }

                    if (client.checkStockholder(client.traderId) == 1) {
                        Thread.sleep(1000);
                        System.out.println("\nChoose a trader to trade stocks with (T), or check active traders for updates (U):");
                        String choice = in.nextLine().trim().toUpperCase();
                        switch (choice) {
                            case "T":
                                System.out.println("\nEnter the trader ID number to transfer to or type 'BACK' to return to the trader list:");
                                String response = in.nextLine();
                                if (!response.trim().toUpperCase().equals("BACK")) {
                                    int traderIdFrom = client.traderId;
                                    int traderIdTo = Integer.parseInt(response);
                                    client.transfer(traderIdFrom, traderIdTo);
                                }
                                forceUpdate = true;
                                break;

                            case "U":
                                forceUpdate = true;
                                break;

                            default:
                                System.out.println("----------------------------------");
                                System.out.println("Unknown command: " + choice);
                                break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
