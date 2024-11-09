import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class ConsumoTrack extends JFrame {
    // Class variables for user authentication, expense tracking, goals, and analytics
    private Map<String, String> userDatabase = new HashMap<>(); // Mock user data
    private String currentUser;
    private List<Expense> expenses = new ArrayList<>();
    private List<ShoppingItem> shoppingList = new ArrayList<>();
    private Map<String, Integer> categoryGoals = new HashMap<>();
    private Map<String, Integer> categoryItemGoals = new HashMap<>();
    private int totalFinancialGoal = 0, totalItemGoal = 0;
    private int virtualPoints = 0;

    // Constructor for UI setup
    public ConsumoTrack() {
        setTitle("ConsumoTrack");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new CardLayout());

        // Login panel initialization
        JPanel loginPanel = createLoginPanel();
        add(loginPanel, "login");

        // Register panel initialization
        JPanel registerPanel = createRegisterPanel();
        add(registerPanel, "register");

        // Main App panel with feature options
        JPanel mainPanel = createMainPanel();
        add(mainPanel, "main");

        // Show login panel initially
        showPanel("login");
    }

    // Utility to show specified panel
    private void showPanel(String name) {
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), name);
    }

    // Creating Login panel
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2));
        
        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        
        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");

        // Event for login button
        loginBtn.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());
            if (userDatabase.containsKey(user) && userDatabase.get(user).equals(pass)) {
                currentUser = user;
                showPanel("main");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.");
            }
        });

        // Event for register button
        registerBtn.addActionListener(e -> showPanel("register"));
        
        // Add to panel
        panel.add(userLabel); panel.add(userField);
        panel.add(passLabel); panel.add(passField);
        panel.add(loginBtn); panel.add(registerBtn);
        
        return panel;
    }

    // Register panel creation
    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2));
        
        JLabel userLabel = new JLabel("New Username:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("New Password:");
        JPasswordField passField = new JPasswordField();
        
        JButton registerBtn = new JButton("Register");
        JButton backBtn = new JButton("Back");

        // Register event
        registerBtn.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());
            if (!userDatabase.containsKey(user)) {
                userDatabase.put(user, pass);
                JOptionPane.showMessageDialog(this, "Registration successful!");
                showPanel("login");
            } else {
                JOptionPane.showMessageDialog(this, "User already exists.");
            }
        });

        // Back to login
        backBtn.addActionListener(e -> showPanel("login"));

        // Add to panel
        panel.add(userLabel); panel.add(userField);
        panel.add(passLabel); panel.add(passField);
        panel.add(registerBtn); panel.add(backBtn);
        
        return panel;
    }

    // Main application panel with feature buttons
    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2));
        
        JButton expenseBtn = new JButton("Expense Tracker");
        expenseBtn.addActionListener(e -> showExpenseTracker());

        JButton shoppingBtn = new JButton("Shopping List");
        shoppingBtn.addActionListener(e -> showShoppingList());

        JButton goalBtn = new JButton("Goal Setting");
        goalBtn.addActionListener(e -> showGoalSetting());

        JButton notificationBtn = new JButton("Notification Manager");
        notificationBtn.addActionListener(e -> showNotificationManager());

        JButton analysisBtn = new JButton("Consumption Analysis");
        analysisBtn.addActionListener(e -> showConsumptionAnalysis());

        JButton dashboardBtn = new JButton("Analytics Dashboard");
        dashboardBtn.addActionListener(e -> showAnalyticsDashboard());

        JButton reminderBtn = new JButton("Reminder Manager");
        reminderBtn.addActionListener(e -> showReminderManager());

        JButton rewardsBtn = new JButton("Rewards Manager");
        rewardsBtn.addActionListener(e -> showRewardsManager());

        // Adding to main panel
        panel.add(expenseBtn); panel.add(shoppingBtn);
        panel.add(goalBtn); panel.add(notificationBtn);
        panel.add(analysisBtn); panel.add(dashboardBtn);
        panel.add(reminderBtn); panel.add(rewardsBtn);

        return panel;
    }

    // Expense Tracker UI and logic
    private void showExpenseTracker() {
        JFrame frame = new JFrame("Expense Tracker");
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(4, 2));

        JTextField amountField = new JTextField();
        JTextField itemField = new JTextField();
        JTextField categoryField = new JTextField();

        JButton saveBtn = new JButton("Save Expense");
        saveBtn.addActionListener(e -> {
            int amount = Integer.parseInt(amountField.getText());
            String item = itemField.getText();
            String category = categoryField.getText();

            expenses.add(new Expense(amount, item, category));
            JOptionPane.showMessageDialog(frame, "Expense added!");

            // Update virtual points based on savings
            if (amount < totalFinancialGoal) {
                virtualPoints += (totalFinancialGoal - amount) / 10; // Example points system
            }
        });

        frame.add(new JLabel("Expense Amount:"));
        frame.add(amountField);
        frame.add(new JLabel("Item Name:"));
        frame.add(itemField);
        frame.add(new JLabel("Category:"));
        frame.add(categoryField);
        frame.add(saveBtn);

        frame.setVisible(true);
    }

    // Shopping List UI and logic
    private void showShoppingList() {
        JFrame frame = new JFrame("Shopping List");
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 2));

        JTextField itemField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField dateField = new JTextField();

        JButton addBtn = new JButton("Add to Shopping List");
        addBtn.addActionListener(e -> {
            String item = itemField.getText();
            String category = categoryField.getText();
            String date = dateField.getText();
            shoppingList.add(new ShoppingItem(item, category, date));
            JOptionPane.showMessageDialog(frame, "Item added for " + date);
        });

        frame.add(new JLabel("Item Name:"));
        frame.add(itemField);
        frame.add(new JLabel("Category:"));
        frame.add(categoryField);
        frame.add(new JLabel("Estimated Buying Date:"));
        frame.add(dateField);
        frame.add(addBtn);

        frame.setVisible(true);
    }

    // Goal Setting UI and logic
    private void showGoalSetting() {
        JFrame frame = new JFrame("Goal Setting");
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(6, 2));

        JTextField totalGoalField = new JTextField();
        JTextField totalItemsField = new JTextField();
        JTextField categoryGoalField = new JTextField();
        JTextField categoryItemGoalField = new JTextField();

        JButton setBtn = new JButton("Set Goals");
        setBtn.addActionListener(e -> {
            totalFinancialGoal = Integer.parseInt(totalGoalField.getText());
            totalItemGoal = Integer.parseInt(totalItemsField.getText());
            categoryGoals.put("General", Integer.parseInt(categoryGoalField.getText()));
            categoryItemGoals.put("General", Integer.parseInt(categoryItemGoalField.getText()));
            JOptionPane.showMessageDialog(frame, "Goals set!");
        });

        frame.add(new JLabel("Total Financial Goal:"));
        frame.add(totalGoalField);
        frame.add(new JLabel("Total Item Goal:"));
        frame.add(totalItemsField);
        frame.add(new JLabel("Category Goal:"));
        frame.add(categoryGoalField);
        frame.add(new JLabel("Category Item Goal:"));
        frame.add(categoryItemGoalField);
        frame.add(setBtn);

        frame.setVisible(true);
    }

    // Notification Manager logic
    private void showNotificationManager() {
        JFrame frame = new JFrame("Notification Manager");
        frame.setSize(400, 200);
        
        JButton notifyBtn = new JButton("Check Notifications");
        notifyBtn.addActionListener(e -> {
            StringBuilder notifications = new StringBuilder();
            int totalSpent = expenses.stream().mapToInt(Expense::getAmount).sum();
            
            if (totalSpent > totalFinancialGoal) {
                notifications.append("Total expense has crossed the financial goal!\n");
            }
            
            for (String category : categoryGoals.keySet()) {
                int categorySpent = expenses.stream()
                        .filter(expense -> expense.getCategory().equals(category))
                        .mapToInt(Expense::getAmount)
                        .sum();
                if (categorySpent > categoryGoals.get(category)) {
                    notifications.append("Expense for " + category + " has crossed the category goal!\n");
                }
            }
            JOptionPane.showMessageDialog(frame, notifications.length() > 0 ? notifications : "No notifications.");
        });

        frame.setLayout(new FlowLayout());
        frame.add(notifyBtn);
        frame.setVisible(true);
    }

    // Reminder Manager UI and logic
    private void showReminderManager() {
        JFrame frame = new JFrame("Reminder Manager");
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(4, 2));

        JTextField itemField = new JTextField();
        JTextField dateField = new JTextField();

        JButton addReminderBtn = new JButton("Set Reminder");
        addReminderBtn.addActionListener(e -> {
            String item = itemField.getText();
            String date = dateField.getText();
            JOptionPane.showMessageDialog(frame, "Reminder set for " + item + " on " + date);
            // In a real scenario, you would set up a timer or scheduler here
        });

        frame.add(new JLabel("Item Name:"));
        frame.add(itemField);
        frame.add(new JLabel("Reminder Date:"));
        frame.add(dateField);
        frame.add(addReminderBtn);

        frame.setVisible(true);
    }

    // Consumption Analysis UI and logic
    private void showConsumptionAnalysis() {
        JFrame frame = new JFrame("Consumption Analysis");
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(4, 2));

        JButton analyzeBtn = new JButton("Analyze");
        analyzeBtn.addActionListener(e -> {
            int totalMonthlyConsumption = expenses.stream().mapToInt(Expense::getAmount).sum();
            JOptionPane.showMessageDialog(frame, "Total Monthly Financial Consumption: " + totalMonthlyConsumption);
        });

        frame.add(new JLabel("Click to Analyze Monthly Consumption:"));
        frame.add(analyzeBtn);

        // Display number of items consumed
        int totalItemsConsumed = expenses.size();
        frame.add(new JLabel("Total Items Consumed: " + totalItemsConsumed));
        
        frame.setVisible(true);
    }

    // Rewards Manager UI and logic
    private void showRewardsManager() {
        JFrame frame = new JFrame("Rewards Manager");
        frame.setSize(400, 200);
        frame.setLayout(new FlowLayout());

        JButton pointsBtn = new JButton("Check Virtual Points");
        pointsBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "You have " + virtualPoints + " virtual points.");
        });

        frame.add(pointsBtn);
        frame.setVisible(true);
    }

    // Analytics Dashboard UI
    private void showAnalyticsDashboard() {
        JFrame frame = new JFrame("Analytics Dashboard");
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Dummy data for the graph (in real scenarios, you would use a charting library)
        DefaultListModel<String> listModel = new DefaultListModel<>();
        expenses.forEach(expense -> listModel.addElement(expense.getCategory() + ": " + expense.getAmount()));
        
        JList<String> expenseList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(expenseList);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(new JLabel("Monthly Category-wise Financial Expenses"), BorderLayout.NORTH);
        frame.setVisible(true);
    }

    // Main method to run the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ConsumoTrack::new);
    }

    // Expense class
    class Expense {
        private int amount;
        private String item;
        private String category;

        public Expense(int amount, String item, String category) {
            this.amount = amount;
            this.item = item;
            this.category = category;
        }

        public int getAmount() {
            return amount;
        }

        public String getCategory() {
            return category;
        }
    }

    // ShoppingItem class
    class ShoppingItem {
        private String item;
        private String category;
        private String estimatedDate;

        public ShoppingItem(String item, String category, String estimatedDate) {
            this.item = item;
            this.category = category;
            this.estimatedDate = estimatedDate;
        }
    }
}