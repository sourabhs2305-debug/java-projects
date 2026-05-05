public class Stock {
    private String symbol;
    private String name;
    private double price;
    private double change;

    public Stock(String symbol, String name, double price) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
        this.change = 0.0;
    }

    public String getSymbol() { return symbol; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public double getChange() { return change; }

    public void updatePrice(double newPrice) {
        this.change = newPrice - this.price;
        this.price = newPrice;
    }

    public void display() {
        String arrow = change >= 0 ? "+" : "";
        System.out.printf("  %-6s %-20s Rs.%-10.2f (%s%.2f)%n",
                symbol, name, price, arrow, change);
    }
}