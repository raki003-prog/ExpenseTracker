import java.io.*;
import java.util.*;

public class ExpenseTracker {
    private static final String FILE_NAME = "expenses.csv";
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("💰 Welcome to Expense Tracker!");

        while (true) {
            System.out.println("\n1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. View Total");
            System.out.println("4. Delete Expense");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: addOrUpdateExpense(); break;
                case 2: viewExpenses(); break;
                case 3: viewTotal(); break;
                case 4: deleteExpense(); break;
                case 5: System.out.println("Exiting..."); System.exit(0);
                default: System.out.println("❌ Invalid choice!");
            }
        }
    }

    // ✅ Add or update existing expense
    private static void addOrUpdateExpense() {
        Map<String, Double> expenses = loadExpenses();

        System.out.print("Enter Description: ");
        String desc = sc.nextLine().trim();
        System.out.print("Enter Amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();

        if (expenses.containsKey(desc)) {
            expenses.put(desc, expenses.get(desc) + amount); // add to existing
            System.out.println("🔄 Updated existing expense!");
        } else {
            expenses.put(desc, amount); // new entry
            System.out.println("✅ Expense Added!");
        }

        saveExpenses(expenses);
    }

    // ✅ View all expenses
    private static void viewExpenses() {
        Map<String, Double> expenses = loadExpenses();
        if (expenses.isEmpty()) {
            System.out.println("⚠️ No expenses recorded yet!");
            return;
        }
        System.out.println("\n📜 Expenses List:");
        expenses.forEach((desc, amt) ->
            System.out.println("Description: " + desc + " | Amount: ₹" + amt));
    }

    // ✅ View total
    private static void viewTotal() {
        Map<String, Double> expenses = loadExpenses();
        double total = expenses.values().stream().mapToDouble(Double::doubleValue).sum();
        System.out.println("💵 Total Expenses: ₹" + total);
    }

    // ✅ Delete an expense
    private static void deleteExpense() {
        Map<String, Double> expenses = loadExpenses();

        System.out.print("Enter Description to Delete: ");
        String desc = sc.nextLine().trim();

        if (expenses.containsKey(desc)) {
            expenses.remove(desc);
            saveExpenses(expenses);
            System.out.println("🗑 Expense Deleted!");
        } else {
            System.out.println("❌ Expense not found!");
        }
    }

    // ✅ Helper → load expenses from file
    private static Map<String, Double> loadExpenses() {
        Map<String, Double> expenses = new LinkedHashMap<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return expenses;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                expenses.put(parts[0], Double.parseDouble(parts[1]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return expenses;
    }

    // ✅ Helper → save expenses back to file
    private static void saveExpenses(Map<String, Double> expenses) {
        try (FileWriter fw = new FileWriter(FILE_NAME, false)) {
            for (Map.Entry<String, Double> entry : expenses.entrySet()) {
                fw.write(entry.getKey() + "," + entry.getValue() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
