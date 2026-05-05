import java.util.HashMap;
import java.util.Map;

public class Portfolio {
    private String owner;
    private double balance;
    private HashMap<String, Integer> holdings;
    private double totalInvested;

    public Portfolio(String owner, double balance) {
        this.owner = owner;
        this.balance = balance;
        this.holdings = new HashMap<>();
        this.totalInvested = 0;
    }

    public String getOwner() { return owner; }
    public double getBalance() { return balance; }

    public boolean buyStock(Stock stock, int quantity) {
        double cost = stock.getPrice() * quantity;
        if (cost > balance) {
            System.out.println("  [!] Insufficient balance!");
            return false;
        }
        balance -= cost;
        totalInvested += cost;
        holdings.put(stock.getSymbol(),
                holdings.getOrDefault(stock.getSymbol(), 0) + quantity);
        System.out.println("  [OK] Bought " + quantity + " shares of " + stock.getSymbol());
        return true;
    }

    public boolean sellStock(Stock stock, int quantity) {
        int owned = holdings.getOrDefault(stock.getSymbol(), 0);
        if (owned < quantity) {
            System.out.println("  [!] Not enough shares!");
            return false;
        }
        double earned = stock.getPrice() * quantity;
        balance += earned;
        holdings.put(stock.getSymbol(), owned - quantity);
        if (holdings.get(stock.getSymbol()) == 0) {
            holdings.remove(stock.getSymbol());
        }
        System.out.println("  [OK] Sold " + quantity + " shares of " + stock.getSymbol());
        return true;
    }

    public void displayPortfolio(HashMap<String, Stock> market) {
        System.out.println("  Owner   : " + owner);
        System.out.printf("  Balance : Rs.%.2f%n", balance);
        System.out.println("  -------------------------------------------------");
        if (holdings.isEmpty()) {
            System.out.println("  No stocks owned.");
        } else {
            double totalValue = 0;
            System.out.printf("  %-8s %-6s %-12s %-12s%n",
                    "Symbol", "Qty", "Price", "Value");
            System.out.println("  -------------------------------------------------");
            for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
                String sym = entry.getKey();
                int qty = entry.getValue();
                Stock s = market.get(sym);
                if (s != null) {
                    double value = s.getPrice() * qty;
                    totalValue += value;
                    System.out.printf("  %-8s %-6d Rs.%-9.2f Rs.%-9.2f%n",
                            sym, qty, s.getPrice(), value);
                }
            }
            System.out.println("  -------------------------------------------------");
            System.out.printf("  Total Stock Value : Rs.%.2f%n", totalValue);
            System.out.printf("  Total Portfolio   : Rs.%.2f%n", totalValue + balance);
        }
    }
}