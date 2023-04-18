import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ShoppingCartApp extends JFrame {
    private JLabel productLabel, priceLabel, quantityLabel;
    private JTextField productField, priceField, quantityField;
    private JButton addButton, removeButton, checkoutButton;
    private JList<String> cartList;
    private DefaultListModel<String> cartModel;
    private double total;
    private JPanel cartPanel; // declare cartPanel as a private instance variable


    public ShoppingCartApp() {
        setTitle("Shopping Cart Application");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        productLabel = new JLabel("Product:");
        priceLabel = new JLabel("Price:");
        quantityLabel = new JLabel("Quantity:");
        productField = new JTextField(10);
        priceField = new JTextField(10);
        quantityField = new JTextField(10);
        addButton = new JButton("Add");
        removeButton = new JButton("Remove");
        checkoutButton = new JButton("Checkout");
        cartModel = new DefaultListModel<String>();
        cartList = new JList<String>(cartModel);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(productLabel);
        inputPanel.add(productField);
        inputPanel.add(priceLabel);
        inputPanel.add(priceField);
        inputPanel.add(quantityLabel);
        inputPanel.add(quantityField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(checkoutButton);
        cartPanel = new JPanel(new BorderLayout()); // initialize cartPanel
        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.add(new JLabel("Shopping Cart"), BorderLayout.NORTH);
        cartPanel.add(new JScrollPane(cartList), BorderLayout.CENTER);
        cartPanel.add(new JLabel("Total: $0.00"), BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(cartPanel, BorderLayout.EAST);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String product = productField.getText();
                double price = Double.parseDouble(priceField.getText());
                int quantity = Integer.parseInt(quantityField.getText());
                double subtotal = price * quantity;
                cartModel.addElement(product + " x " + quantity + " = $" + subtotal);
                total += subtotal;
                updateTotalLabel();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = cartList.getSelectedIndex();
                if (index != -1) {
                    String item = cartModel.getElementAt(index);
                    String[] parts = item.split(" ");
                    double subtotal = Double.parseDouble(parts[3].substring(1));
                    cartModel.removeElementAt(index);
                    total -= subtotal;
                    updateTotalLabel();
                }
            }
        });

        checkoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(ShoppingCartApp.this,
                        "Thank you for your purchase!\nTotal: $" + total,
                        "Checkout", JOptionPane.INFORMATION_MESSAGE);
                cartModel.removeAllElements();
                total = 0.0;
                updateTotalLabel();
            }
        });
    }

    private void updateTotalLabel() {
        cartPanel.remove(2);
        cartPanel.add(new JLabel("Total: $" + String.format("%.2f", total)), BorderLayout.SOUTH);
        cartPanel.validate();
    }

    public static void main(String[] args) {
        ShoppingCartApp app = new ShoppingCartApp();
        app.setVisible(true);
    }
}
