// Expense.java
// This class stores ONE expense, like one row in the table.

public class Expense {

    // Data for one expense
    private int id;
    private String date;
    private String category;
    private double amount;
    private String description;

    // Constructor: creates a new expense with all its details
    public Expense(int id, String date, String category, double amount, String description) {
        this.id = id;
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.description = description;
    }

    // Getter methods: give other classes a way to read the data
    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    // Converts this expense into one line of text for the file.
    // The fields are separated by the | symbol. Example: 1|2026-10-17|Food|120.0|Lunch
    public String toLine() {
        return id + "|" + date + "|" + category + "|" + amount + "|" + description;
    }

    // The opposite of toLine(): reads one line of text and builds an Expense from it
    public static Expense fromLine(String line) {
        String[] parts = line.split("\\|", 5);
        int id = Integer.parseInt(parts[0]);
        double amount = Double.parseDouble(parts[3]);
        return new Expense(id, parts[1], parts[2], amount, parts[4]);
    }
}
