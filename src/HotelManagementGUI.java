import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HotelManagementGUI extends JFrame {
    private JTabbedPane tabbedPane;
    private JTextArea dashboardArea;
    private JTextArea availabilityArea;
    private JComboBox<String> roomTypeCombo;
    private JTextField roomNumberField;
    private JTextField customerNameField;
    private JTextField contactField;
    private JComboBox<String> genderCombo;

    private final Color PRIMARY = new Color(37, 99, 235);
    private final Color SECONDARY = new Color(248, 250, 252);
    private final Color ACCENT = new Color(16, 185, 129);
    private final Color TEXT = new Color(30, 41, 59);

    public HotelManagementGUI() {
        setTitle("Hotel Management System");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(SECONDARY);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY);
        headerPanel.setBorder(new EmptyBorder(20, 25, 20, 25));

        JLabel header = new JLabel("🏨 Hotel Management System");
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 32));

        JLabel subtitle = new JLabel("Smart Hotel Operations Dashboard");
        subtitle.setForeground(new Color(219, 234, 254));
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setOpaque(false);
        titlePanel.add(header);
        titlePanel.add(subtitle);
        headerPanel.add(titlePanel, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 15));
        tabbedPane.setBackground(Color.WHITE);

        tabbedPane.addTab("Dashboard", createDashboardPanel());
        tabbedPane.addTab("Book Room", createBookingPanel());
        tabbedPane.addTab("Order Food", createFoodPanel());
        tabbedPane.addTab("Checkout", createCheckoutPanel());
        tabbedPane.addTab("Availability", createAvailabilityPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createCardLayout() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(new CompoundBorder(
                new LineBorder(new Color(226, 232, 240), 1, true),
                new EmptyBorder(20, 20, 20, 20)
        ));
        return panel;
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(SECONDARY);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 15, 15));
        statsPanel.setBackground(SECONDARY);
        statsPanel.add(createStatCard("Total Rooms", "60"));
        statsPanel.add(createStatCard("Available", "60"));
        statsPanel.add(createStatCard("Occupied", "0"));
        statsPanel.add(createStatCard("Today's Revenue", "₹0"));

        dashboardArea = new JTextArea();
        dashboardArea.setEditable(false);
        dashboardArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        dashboardArea.setBackground(Color.WHITE);
        dashboardArea.setForeground(TEXT);
        dashboardArea.setText(
                "Welcome to the Hotel Management System\n\n" +
                        "✔ Room Booking and Reservation\n" +
                        "✔ Food Ordering Management\n" +
                        "✔ Automated Billing and Checkout\n" +
                        "✔ Live Room Availability\n" +
                        "✔ Customer Information Management\n" +
                        "✔ Professional Dashboard Analytics"
        );

        JScrollPane scrollPane = new JScrollPane(dashboardArea);
        scrollPane.setBorder(new TitledBorder("System Overview"));

        panel.add(statsPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createStatCard(String title, String value) {
        JPanel card = createCardLayout();
        card.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        titleLabel.setForeground(Color.GRAY);

        JLabel valueLabel = new JLabel(value, JLabel.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(PRIMARY);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    private JPanel createBookingPanel() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(SECONDARY);

        JPanel panel = createCardLayout();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new TitledBorder(new LineBorder(PRIMARY, 1, true), "Room Reservation"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        roomTypeCombo = new JComboBox<>(new String[]{
                "Luxury Double Room", "Deluxe Double Room",
                "Luxury Single Room", "Deluxe Single Room"
        });

        roomNumberField = new JTextField(18);
        customerNameField = new JTextField(18);
        contactField = new JTextField(18);
        genderCombo = new JComboBox<>(new String[]{"Male", "Female", "Other"});

        addField(panel, gbc, 0, "Room Type", roomTypeCombo);
        addField(panel, gbc, 1, "Room Number", roomNumberField);
        addField(panel, gbc, 2, "Customer Name", customerNameField);
        addField(panel, gbc, 3, "Contact Number", contactField);
        addField(panel, gbc, 4, "Gender", genderCombo);

        JButton bookButton = createStyledButton("Book Room", PRIMARY);
        JButton clearButton = createStyledButton("Clear", ACCENT);

        bookButton.addActionListener(e -> bookRoom());
        clearButton.addActionListener(e -> clearBookingForm());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(bookButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(buttonPanel, gbc);

        wrapper.add(panel);
        return wrapper;
    }

    private JPanel createFoodPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(SECONDARY);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        String[] columns = {"Item", "Category", "Price"};
        Object[][] data = {
                {"Sandwich", "Snacks", "₹50"},
                {"Pasta", "Main Course", "₹60"},
                {"Noodles", "Main Course", "₹70"},
                {"Coke", "Beverage", "₹30"}
        };

        JTable table = new JTable(new DefaultTableModel(data, columns));
        table.setRowHeight(32);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        JButton orderButton = createStyledButton("Place Order", ACCENT);
        orderButton.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Food order placed successfully!", "Success",
                JOptionPane.INFORMATION_MESSAGE));

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(orderButton, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createCheckoutPanel() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(SECONDARY);

        JPanel panel = createCardLayout();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new TitledBorder("Customer Checkout"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);

        JTextField checkoutRoomField = new JTextField(18);
        JButton checkoutButton = createStyledButton("Generate Bill & Checkout", PRIMARY);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Room Number:"), gbc);
        gbc.gridx = 1;
        panel.add(checkoutRoomField, gbc);
        gbc.gridy = 1;
        panel.add(checkoutButton, gbc);

        checkoutButton.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Checkout completed successfully!\nBill generated.",
                "Checkout Successful", JOptionPane.INFORMATION_MESSAGE));

        wrapper.add(panel);
        return wrapper;
    }

    private JPanel createAvailabilityPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(SECONDARY);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        availabilityArea = new JTextArea();
        availabilityArea.setEditable(false);
        availabilityArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        availabilityArea.setText(
                "Room Availability Status\n\n" +
                        "Luxury Double Rooms  : 10 Available\n" +
                        "Deluxe Double Rooms  : 20 Available\n" +
                        "Luxury Single Rooms  : 10 Available\n" +
                        "Deluxe Single Rooms  : 20 Available"
        );

        JScrollPane scrollPane = new JScrollPane(availabilityArea);
        scrollPane.setBorder(new TitledBorder("Live Availability"));
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
        return button;
    }

    private void addField(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent component) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label + ":"), gbc);
        gbc.gridx = 1;
        panel.add(component, gbc);
    }

    private void bookRoom() {
        if (customerNameField.getText().trim().isEmpty() ||
                contactField.getText().trim().isEmpty() ||
                roomNumberField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Room booked successfully for " + customerNameField.getText() + "!",
                "Booking Confirmed", JOptionPane.INFORMATION_MESSAGE);
        clearBookingForm();
    }

    private void clearBookingForm() {
        roomNumberField.setText("");
        customerNameField.setText("");
        contactField.setText("");
        genderCombo.setSelectedIndex(0);
        roomTypeCombo.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HotelManagementGUI().setVisible(true));
    }
}