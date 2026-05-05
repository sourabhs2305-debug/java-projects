import java.util.HashMap;
import java.util.Scanner;

public class StockMarket {

    static HashMap<String, Stock> market = new HashMap<>();
    static Portfolio portfolio;
    static Scanner sc = new Scanner(System.in);

    static void initMarket() {
        market.put("TCS",    new Stock("TCS",    "Tata Consultancy",  3800.00));
        market.put("INFY",   new Stock("INFY",   "Infosys Ltd",       1500.00));
        market.put("RELIANCE",new Stock("RELIANCE","Reliance Ind",    2900.00));
        market.put("WIPRO",  new Stock("WIPRO",  "Wipro Ltd",          450.00));
        market.put("HDFC",   new Stock("HDFC",   "HDFC Bank",         1600.00));
    }

    static void printLine() {
        System.out.println("  -----------------------------------------------------");
    }

    static void showMarket() {
        System.out.println("\n  LIVE MARKET");
        printLine();
        System.out.printf("  %-10s %-22s %-14s %s%n",
                "Symbol", "Company", "Price", "Change");
        printLine();
        for (Stock s : market.values()) {
            s.display();
        }
        printLine();
    }

    static void simulatePriceChange() {
        for (Stock s : market.values()) {
            double change = (Math.random() * 200) - 100;
            double newPrice = Math.max(10, s.getPrice() + change);
            s.updatePrice(Math.round(newPrice * 100.0) / 100.0);
        }
        System.out.println("  [OK] Market prices updated!");
    }

    static void buyStock() {
        showMarket();
        System.out.print("  Enter stock symbol to buy: ");
        String sym = sc.nextLine().trim().toUpperCase();
        Stock s = market.get(sym);
        if (s == null) {
            System.out.println("  [!] Stock not found.");
            return;
        }
        System.out.print("  Enter quantity: ");
        try {
            int qty = Integer.parseInt(sc.nextLine().trim());
            if (qty <= 0) { System.out.println("  [!] Invalid quantity."); return; }
            portfolio.buyStock(s, qty);
        } catch (NumberFormatException e) {
            System.out.println("  [!] Invalid input.");
        }
    }

    static void sellStock() {
        portfolio.displayPortfolio(market);
        System.out.print("  Enter stock symbol to sell: ");
        String sym = sc.nextLine().trim().toUpperCase();
        Stock s = market.get(sym);
        if (s == null) {
            System.out.println("  [!] Stock not found.");
            return;
        }
        System.out.print("  Enter quantity: ");
        try {
            int qty = Integer.parseInt(sc.nextLine().trim());
            if (qty <= 0) { System.out.println("  [!] Invalid quantity."); return; }
            portfolio.sellStock(s, qty);
        } catch (NumberFormatException e) {
            System.out.println("  [!] Invalid input.");
        }
    }

    public static void main(String[] args) {
        initMarket();

        System.out.println("=====================================================");
        System.out.println("         STOCK TRADING PLATFORM                     ");
        System.out.println("=====================================================");
        System.out.print("  Enter your name: ");
        String name = sc.nextLine().trim();
        portfolio = new Portfolio(name, 100000.00);
        System.out.println("  Welcome " + name + "! Starting balance: Rs.1,00,000");

        boolean running = true;
        while (running) {
            System.out.println("\n  MAIN MENU");
            printLine();
            System.out.println("  1. View Market");
            System.out.println("  2. Buy Stock");
            System.out.println("  3. Sell Stock");
            System.out.println("  4. View My Portfolio");
            System.out.println("  5. Simulate Market Change");
            System.out.println("  6. Exit");
            printLine();
            System.out.print("  Choose (1-6): ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": showMarket(); break;
                case "2": buyStock(); break;
                case "3": sellStock(); break;
                case "4": portfolio.displayPortfolio(market); break;
                case "5": simulatePriceChange(); break;
                case "6": System.out.println("  Goodbye!"); running = false; break;
                default:  System.out.println("  [!] Choose 1-6.");
            }
        }
        sc.close();
    }
}