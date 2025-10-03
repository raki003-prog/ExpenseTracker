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
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: addExpense(); break;
                case 2: viewExpenses(); break;
                case 3: viewTotal(); break;
                case 4: System.out.println("Exiting..."); System.exit(0);
                default: System.out.println("❌ Invalid choice!");
            }
        }
    }

    private static void addExpense() {
        try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
            System.out.print("Enter Description: ");
            String desc = sc.nextLine();
            System.out.print("Enter Amount: ");
            double amount = sc.nextDouble();
            sc.nextLine();

            String line = desc + "," + amount + "\n";
            fw.write(line);
            System.out.println("✅ Expense Added!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void viewExpenses() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("⚠️ No expenses recorded yet!");
            return;
        }
        System.out.println("\n📜 Expenses List:");
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                System.out.println("Description: " + parts[0] + " | Amount: ₹" + parts[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void viewTotal() {
        double total = 0;
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("⚠️ No expenses recorded yet!");
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                total += Double.parseDouble(parts[1]);
            }
            System.out.println("💵 Total Expenses: ₹" + total);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
