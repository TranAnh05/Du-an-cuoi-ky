package presentation.SaveEditTransaction;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

import presentation.Subscriber;

public class SaveEditTransactionUI extends JFrame implements Subscriber {
    private JTextField txtTransactionId, txtTransactionDate, txtUnitPrice, txtArea, txtTransactionType, txtLandType, txtHouseType, txtAddress;
    private JButton btnSave;
    private SaveEditTransactionModel model;
    private SaveEditTransactionController controller;

    public SaveEditTransactionUI() {
        super("Save Edited Transaction");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(9, 2, 5, 5));
        panel.add(new JLabel("Transaction ID:"));
        txtTransactionId = new JTextField();
        panel.add(txtTransactionId);
        panel.add(new JLabel("Transaction Date:"));
        txtTransactionDate = new JTextField();
        panel.add(txtTransactionDate);
        panel.add(new JLabel("Unit Price:"));
        txtUnitPrice = new JTextField();
        panel.add(txtUnitPrice);
        panel.add(new JLabel("Area:"));
        txtArea = new JTextField();
        panel.add(txtArea);
        panel.add(new JLabel("Transaction Type:"));
        txtTransactionType = new JTextField();
        panel.add(txtTransactionType);
        panel.add(new JLabel("Land Type:"));
        txtLandType = new JTextField();
        panel.add(txtLandType);
        panel.add(new JLabel("House Type:"));
        txtHouseType = new JTextField();
        panel.add(txtHouseType);
        panel.add(new JLabel("Address:"));
        txtAddress = new JTextField();
        panel.add(txtAddress);
        btnSave = new JButton("Save");
        panel.add(btnSave);

        add(panel);

        btnSave.addActionListener(e -> {
            try {
                boolean saved = controller.execute(
                    txtTransactionId.getText(),
                    txtTransactionDate.getText(),
                    Double.parseDouble(txtUnitPrice.getText()),
                    Double.parseDouble(txtArea.getText()),
                    txtTransactionType.getText(),
                    txtLandType.getText(),
                    txtHouseType.getText(),
                    txtAddress.getText()
                );
                if (saved) {
                    JOptionPane.showMessageDialog(this, "Transaction saved successfully!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to save transaction.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for Unit Price and Area.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void setModel(SaveEditTransactionModel model) {
        this.model = model;
        model.addSubscriber(this);
    }

    public void setController(SaveEditTransactionController controller) {
        this.controller = controller;
    }

    @Override
    public void update() {
        if (model.transactionItems != null && !model.transactionItems.isEmpty()) {
            SaveEditTransactionItem item = model.transactionItems.get(0);
            txtTransactionId.setText(item.transactionId);
            txtTransactionDate.setText(item.transactionDate.toString());
            txtUnitPrice.setText(String.valueOf(item.unitPrice));
            txtArea.setText(String.valueOf(item.area));
            txtTransactionType.setText(item.transactionType);
            txtLandType.setText(item.landType);
            txtHouseType.setText(item.houseType);
            txtAddress.setText(item.address);
        }
    }
}