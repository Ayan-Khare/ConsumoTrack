import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class MainApp extends JFrame {
    private LoginManager loginManager = new LoginManager();
    private ExpenseTracker expenseTracker = new ExpenseTracker();
    private SavingsTracker savingsTracker = new SavingsTracker();
    private RealTimeBudget realTimeBudget = new RealTimeBudget(10000); // Initial budget
    private CardLayout cardLayout = new CardLayout(); // For smooth transitions between screens
    private JPanel mainPanel = new JPanel(cardLayout);

    // Constructor to initialize the main application window
    public MainApp() {
        setTitle("Personal Tracker");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window on screen

        mainPanel.add(createTitlePanel("Welcome to Personal Tracker"), "TITLE");
        mainPanel.add(createRegistrationPanel(), "REGISTER");
        mainPanel.add(createLoginPanel(), "LOGIN");
        mainPanel.add(createMainMenuPanel(), "MAIN_MENU");

        add(mainPanel);
        cardLayout.show(mainPanel, "TITLE");
    }

    // Method to create a title panel with a welcome message
    private JPanel createTitlePanel(String message) {
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        titlePanel.setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel(message, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        JButton startButton = new JButton("Get Started");
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(new Color(60, 179, 113));
        startButton.addActionListener(e -> cardLayout.show(mainPanel, "REGISTER"));

        titlePanel.add(startButton, BorderLayout.SOUTH);
        return titlePanel;
    }

    // Method to create the registration screen
    private JPanel createRegistrationPanel() {
        JPanel registrationPanel = new JPanel(new BorderLayout());
        registrationPanel.setBackground(new Color(255, 255, 255));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        formPanel.setBackground(new Color(255, 255, 255));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        formPanel.add(new JLabel("New Username:"));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("New Password:"));
        formPanel.add(passwordField);

        JButton registerButton = new JButton("Register");
        registerButton.setBackground(new Color(60, 179, 113));
        registerButton.setForeground(Color.WHITE);
        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (loginManager.registerUser(username, password)) {
                JOptionPane.showMessageDialog(this, "Registration Successful! Please log in.");
                cardLayout.show(mainPanel, "LOGIN");
            } else {
                JOptionPane.showMessageDialog(this, "Username already exists. Try a different one.");
            }
        });

        registrationPanel.add(new JLabel("Register Account", SwingConstants.CENTER), BorderLayout.NORTH);
        registrationPanel.add(formPanel, BorderLayout.CENTER);
        registrationPanel.add(registerButton, BorderLayout.SOUTH);
        return registrationPanel;
    }

    // Method to create the login screen
    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel(new BorderLayout());
        loginPanel.setBackground(new Color(255, 255, 255));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        formPanel.setBackground(new Color(255, 255, 255));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        formPanel.add(new JLabel("Username:"));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(60, 179, 113));
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (loginManager.loginUser(username, password)) {
                cardLayout.show(mainPanel, "MAIN_MENU");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials. Try again.");
            }
        });

        loginPanel.add(new JLabel("Login to Account", SwingConstants.CENTER), BorderLayout.NORTH);
        loginPanel.add(formPanel, BorderLayout.CENTER);
        loginPanel.add(loginButton, BorderLayout.SOUTH);
        return loginPanel;
    }

    // Method to display the main menu
    private JPanel createMainMenuPanel() {
        JPanel mainMenuPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        mainMenuPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        mainMenuPanel.setBackground(new Color(245, 245, 245));

        JButton expenseButton = new JButton("Add Expense");
        JButton savingsButton = new JButton("Add Savings");
        JButton viewExpensesButton = new JButton("View Total Expenses");
        JButton viewSavingsButton = new JButton("View Total Savings");
        JButton budgetButton = new JButton("Adjust Budget");
        JButton exitButton = new JButton("Exit");

        styleButton(expenseButton, e -> addExpense());
        styleButton(savingsButton, e -> addSavings());
        styleButton(viewExpensesButton, e -> viewTotalExpenses());
        styleButton(viewSavingsButton, e -> viewTotalSavings());
        styleButton(budgetButton, e -> adjustBudget());
        styleButton(exitButton, e -> System.exit(0));

        mainMenuPanel.add(new JLabel("Main Menu", SwingConstants.CENTER));
        mainMenuPanel.add(expenseButton);
        mainMenuPanel.add(savingsButton);
        mainMenuPanel.add(viewExpensesButton);
        mainMenuPanel.add(viewSavingsButton);
        mainMenuPanel.add(budgetButton);
        mainMenuPanel.add(exitButton);
        return mainMenuPanel;
    }

    // Method to style buttons
    private void styleButton(JButton button, ActionListener actionListener) {
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.addActionListener(actionListener);
    }

    // Expense handling
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

    private void viewTotalExpenses() {
        double totalExpenses = expenseTracker.getTotalExpenses();
        JOptionPane.showMessageDialog(this, "Total Expenses: ₹" + totalExpenses);
    }

    private void viewTotalSavings() {
        double totalSavings = savingsTracker.getTotalSavings();
        JOptionPane.showMessageDialog(this, "Total Savings: ₹" + totalSavings);
    }

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
        SwingUtilities.invokeLater(() -> new MainApp().setVisible(true));
    }
}

// Additional classes for managing user login, expenses, and budget omitted for brevity (same as previous)


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

// Supporting classes (add these below in the same file or as separate files)
class ExpenseTracker {
    private double totalExpenses = 0;

    public void addExpense(double amount) {
        totalExpenses += amount;
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }
}

class SavingsTracker {
    private double totalSavings = 0;

    public void addSavings(double amount) {
        totalSavings += amount;
    }

    public double getTotalSavings() {
        return totalSavings;
    }
}

class RealTimeBudget {
    private double budget;

    public RealTimeBudget(double budget) {
        this.budget = budget;
    }

    public double getBudget() {
        return budget;
    }
}
