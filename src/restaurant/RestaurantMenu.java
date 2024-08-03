package restaurant;
import javax.swing.JOptionPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.border.EmptyBorder;

public class RestaurantMenu {

    private JFrame frame;
    private JPanel menuPanel;
    private JPanel billPanel;
    private JCheckBox[] startersItems;
    private JCheckBox[] mainCourseItems;
    private JCheckBox[] dessertItems;
    private JButton addToBillButton;
    private JButton clearBillButton;
    private JButton viewBillButton;
    private double totalCost;
    private JLabel totalLabel;

    private final double[] startersPrices = {150.0, 200.0, 100.0};
    private final double[] mainCoursePrices = {300.0, 450.0, 400.0};
    private final double[] dessertPrices = {200.0, 150.0, 100.0};

    private final Font customFont = new Font("Arial", Font.PLAIN, 16);

    public RestaurantMenu() {
        frame = new JFrame("Restaurant Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.getContentPane().setBackground(new Color(255, 230, 230));

        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(3, 1));
        menuPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        billPanel = new JPanel();
        billPanel.setLayout(new GridLayout(0, 1));
        billPanel.setBorder(BorderFactory.createTitledBorder("Bill"));
        billPanel.setBackground(Color.WHITE);

        startersItems = new JCheckBox[3];
        mainCourseItems = new JCheckBox[3];
        dessertItems = new JCheckBox[3];

        createMenuItems();

        addToBillButton = createStyledButton("Add to Bill");
        clearBillButton = createStyledButton("Clear Bill");
        viewBillButton = createStyledButton("View Bill");

        totalLabel = new JLabel("Total: Rs 0.0");
        totalLabel.setFont(customFont);

        addToBillButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateTotalCost();
            }
        });

        clearBillButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearBill();
            }
        });

        viewBillButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    showBill();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        frame.add(menuPanel, BorderLayout.WEST);
        frame.add(billPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(addToBillButton);
        buttonPanel.add(clearBillButton);
        buttonPanel.add(viewBillButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(totalLabel, BorderLayout.NORTH);

        frame.setVisible(true);
    }

    private void createMenuItems() {
        createMenuSection("Starters", startersItems, startersPrices, new String[]{"Garlic Bread", "Paneer Chilly", "Chicken Tikka"});
        createMenuSection("Main Course", mainCourseItems, mainCoursePrices, new String[]{"Paneer Tikka Masala", "Paneer Butter Masala", "Mix Veg"});
        createMenuSection("Desserts", dessertItems, dessertPrices, new String[]{"Dark Chocolate Waffle", "Ice Cream", "Chocolates"});
    }

    private void createMenuSection(String sectionName, JCheckBox[] items, double[] prices, String[] itemNames) {
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));
        sectionPanel.setBorder(new EmptyBorder(10, 10, 10, 10));  // Increase the padding here

        JLabel label = new JLabel(sectionName);
        label.setFont(customFont);
        sectionPanel.add(label);

        for (int i = 0; i < items.length; i++) {
            items[i] = createStyledCheckBox(itemNames[i]);
            sectionPanel.add(items[i]);
        }

        menuPanel.add(sectionPanel);
    }


    private JCheckBox createStyledCheckBox(String text) {
        JCheckBox checkBox = new JCheckBox(text);
        checkBox.setFont(customFont);
        checkBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
        checkBox.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                checkBox.setForeground(Color.BLUE);
            }

            public void mouseExited(MouseEvent e) {
                checkBox.setForeground(Color.BLACK);
            }
        });
        return checkBox;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(customFont);
        button.setBackground(Color.PINK);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(255, 182, 193));
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.PINK);
            }
        });
        return button;
    }

    private void calculateTotalCost() {
        totalCost = 0.0;
        billPanel.removeAll();

        calculateSectionCost(startersItems, startersPrices);
        calculateSectionCost(mainCourseItems, mainCoursePrices);
        calculateSectionCost(dessertItems, dessertPrices);

        totalLabel.setText("Total: Rs " + String.format("%.2f", totalCost));
        billPanel.revalidate();
        billPanel.repaint();
    }

    private void calculateSectionCost(JCheckBox[] items, double[] prices) {
        for (int i = 0; i < items.length; i++) {
            if (items[i].isSelected()) {
                String itemName = items[i].getText();
                double itemPrice = prices[i];
                JLabel label = new JLabel(itemName + " - Rs " + String.format("%.2f", itemPrice));
                label.setFont(customFont);
                billPanel.add(label);
                totalCost += itemPrice;
            }
        }
    }

    private void clearBill() {
        billPanel.removeAll();
        totalCost = 0.0;
        totalLabel.setText("Total: Rs 0.0");
        billPanel.revalidate();
        billPanel.repaint();
    }

    private void showBill() throws SQLException {
        // Prompt the user for customer details
        String customerName = JOptionPane.showInputDialog("Enter customer's name:");
        String customerPhone = JOptionPane.showInputDialog("Enter customer's phone number:");

        if (customerName == null || customerName.isEmpty() || customerPhone == null || customerPhone.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Customer name and phone number are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else {

            JDialog billDialog = new JDialog(frame, "Final Bill", true);
            billDialog.setSize(400, 400);
            billDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            billDialog.setLocationRelativeTo(frame);

            JPanel billPanel = new JPanel(new BorderLayout());
            billPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            billPanel.setBackground(new Color(255, 230, 230));

            JLabel headerLabel = new JLabel("<html><div style='text-align: center;'><h1>Restaurant Bill</h1></div></html>");
            headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
            headerLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);

            JTextArea billTextArea = new JTextArea();
            billTextArea.setFont(customFont);
            billTextArea.setEditable(false);
            billTextArea.setOpaque(false);

            // Create a bill summary
            StringBuilder billSummary = new StringBuilder();
            billSummary.append("Customer Name: ").append(customerName).append("\n");
            billSummary.append("Customer Phone: ").append(customerPhone).append("\n");
            billSummary.append("Items in Bill:\n--------------------------\n");
            for (Component component : this.billPanel.getComponents()) {
                if (component instanceof JLabel) {
                    JLabel label = (JLabel) component;
                    billSummary.append(label.getText()).append("\n");
                }
            }
            billSummary.append("Total: Rs ").append(String.format("%.2f", totalCost));
            billSummary.append("\n--------------------------\nThank you for dining with us. We appreciate your patronage!");

            billTextArea.setText(billSummary.toString());

            billPanel.add(headerLabel, BorderLayout.NORTH);
            billPanel.add(billTextArea, BorderLayout.CENTER);

            billDialog.add(billPanel);
            billDialog.setVisible(true);


        }
        try{
                restaurant.Con con1 = new restaurant.Con();
                String q = "insert into restmenu values('"+ customerName +"','"+ customerPhone+"',' "+totalCost+ "')";
                con1.statement.executeUpdate(q);
            } catch (Exception E){
            E.printStackTrace();
        }

    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new RestaurantMenu();
            }
        });
    }
}
