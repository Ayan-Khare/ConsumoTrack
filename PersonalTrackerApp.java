import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class PersonalTrackerApp extends JFrame {
    private LoginManager loginManager = new LoginManager();
    private ExpenseTracker expenseTracker = new ExpenseTracker();
    private SavingsTracker savingsTracker = new SavingsTracker();
    private RealTimeBudget realTimeBudget = new RealTimeBudget(10000);  // Initial budget

    // Constructor to initialize the main application window
    public PersonalTrackerApp() {
        setTitle("Personal Tracker");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 1));
        showRegistrationScreen();
    }

    // Method to show the registration screen
    private void showRegistrationScreen() {
        JPanel registrationPanel = new JPanel();
        registrationPanel.setLayout(new GridLayout(0, 2));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        registrationPanel.add(new JLabel("New Username:"));
        registrationPanel.add(usernameField);
        registrationPanel.add(new JLabel("New Password:"));
        registrationPanel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(null, registrationPanel,
                "Register", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (loginManager.registerUser(username, password)) {
                JOptionPane.showMessageDialog(this, "Registration Successful! Please log in.");
                showLoginScreen();
            } else {
                JOptionPane.showMessageDialog(this, "Username already exists. Try a different one.");
                showRegistrationScreen();
            }
        } else {
            System.exit(0);
        }
    }

    // Method to show the login screen
    private void showLoginScreen() {
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(0, 2));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(null, loginPanel,
                "Login", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (loginManager.loginUser(username, password)) {
                showMainMenu();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid credentials. Try again.");
                showLoginScreen();
            }
        } else {
            System.exit(0);
        }
    }

    // Method to display the main menu
    private void showMainMenu() {
        JPanel mainMenuPanel = new JPanel();
        mainMenuPanel.setLayout(new GridLayout(0, 1));

        JButton expenseButton = new JButton("Add Expense");
        JButton savingsButton = new JButton("Add Savings");
        JButton viewExpensesButton = new JButton("View Total Expenses");
        JButton viewSavingsButton = new JButton("View Total Savings");
        JButton budgetButton = new JButton("Adjust Budget");
        JButton exitButton = new JButton("Exit");

        // Adding listeners to buttons
        expenseButton.addActionListener(e -> addExpense());
        savingsButton.addActionListener(e -> addSavings());
        viewExpensesButton.addActionListener(e -> viewTotalExpenses());
        viewSavingsButton.addActionListener(e -> viewTotalSavings());
        budgetButton.addActionListener(e -> adjustBudget());
        exitButton.addActionListener(e -> System.exit(0));

        mainMenuPanel.add(expenseButton);
        mainMenuPanel.add(savingsButton);
        mainMenuPanel.add(viewExpensesButton);
        mainMenuPanel.add(viewSavingsButton);
        mainMenuPanel.add(budgetButton);
        mainMenuPanel.add(exitButton);

        add(mainMenuPanel);
        setVisible(true);
    }

    // Method for adding expense
    private void addExpense() {
        String input = JOptionPane.showInputDialog(this, "Enter expense amount:");
        if (input != null && !input.isEmpty()) {
            try {
                double amount = Double.parseDouble(input);
                expenseTracker.addExpense(amount);
                JOptionPane.showMessageDialog(this, "Expense added successfully!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid amount.");
            }
        }
    }

    // Method for adding savings
    private void addSavings() {
        String input = JOptionPane.showInputDialog(this, "Enter savings amount:");
        if (input != null && !input.isEmpty()) {
            try {
                double amount = Double.parseDouble(input);
                savingsTracker.addSavings(amount);
                JOptionPane.showMessageDialog(this, "Savings added successfully!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid amount.");
            }
        }
    }

    // Method for viewing total expenses
    private void viewTotalExpenses() {
        double totalExpenses = expenseTracker.getTotalExpenses();
        JOptionPane.showMessageDialog(this, "Total Expenses: ₹" + totalExpenses);
    }

    // Method for viewing total savings
    private void viewTotalSavings() {
        double totalSavings = savingsTracker.getTotalSavings();
        JOptionPane.showMessageDialog(this, "Total Savings: ₹" + totalSavings);
    }

    // Method for adjusting the budget
    private void adjustBudget() {
        String input = JOptionPane.showInputDialog(this, "Enter new budget amount:");
        if (input != null && !input.isEmpty()) {
            try {
                double newBudget = Double.parseDouble(input);
                realTimeBudget = new RealTimeBudget(newBudget);
                JOptionPane.showMessageDialog(this, "Budget adjusted successfully!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid amount.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PersonalTrackerApp());
    }
}

// LoginManager class to handle user registration and login
class LoginManager {
    private Map<String, String> users = new HashMap<>();

    // Register user
    public boolean registerUser(String username, String password) {
        if (users.containsKey(username)) {
            return false; // Username already exists
        }
        users.put(username, password);
        return true;
    }

    // Login user
    public boolean loginUser(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }
}
