// ExpenseManager.java
// This class keeps the list of all expenses and saves them to a text file.

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ExpenseManager {

    // The list that holds every expense
    private ArrayList<Expense> expenses = new ArrayList<Expense>();

    // The file where expenses are saved
    private String fileName = "expenses.txt";

    // Constructor: runs when the manager is created. Loads old data from the file.
    public ExpenseManager() {
        loadFromFile();
    }

    // Adds a new expense to the list, then saves the list to the file
    public void addExpense(String date, String category, double amount, String description) {
        int newId = findNextId();
        Expense e = new Expense(newId, date, category, amount, description);
        expenses.add(e);
        saveToFile();
    }

    // Removes the expense that has the given id, then saves the file
    public void deleteExpense(int id) {
        for (int i = 0; i < expenses.size(); i++) {
            if (expenses.get(i).getId() == id) {
                expenses.remove(i);
                saveToFile();
                return;
            }
        }
    }

    // Returns all expenses as text, one expense per line
    public String listAsText() {
        String text = "";
        for (Expense e : expenses) {
            text = text + e.toLine() + "\n";
        }
        return text;
    }

    // Finds a new id number that no expense is using yet
    private int findNextId() {
        int biggest = 0;
        for (Expense e : expenses) {
            if (e.getId() > biggest) {
                biggest = e.getId();
            }
        }
        return biggest + 1;
    }

    // Writes every expense to the file, one per line
    private void saveToFile() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(fileName));
            for (Expense e : expenses) {
                writer.println(e.toLine());
            }
            writer.close();
        } catch (IOException ex) {
            System.out.println("Could not save file: " + ex.getMessage());
        }
    }

    // Reads the file (if it exists) and fills the list
    private void loadFromFile() {
        File file = new File(fileName);
        if (!file.exists()) {
            return;
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                if (line.trim().length() > 0) {
                    expenses.add(Expense.fromLine(line));
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException ex) {
            System.out.println("Could not read file: " + ex.getMessage());
        }
    }
}
