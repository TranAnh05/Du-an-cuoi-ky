package presentation.OpenEditTransactionForm;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

import presentation.Subscriber;
import presentation.SaveEditTransaction.SaveEditTransactionController;

public class OpenEditTransactionFormUI extends JFrame implements Subscriber {
    private JTextField txtTransactionId, txtTransactionDate, txtUnitPrice, txtArea, txtTransactionType, txtLandType, txtHouseType, txtAddress;
    private JButton btnSave;
    private OpenEditTransactionFormModel model;
    private SaveEditTransactionController saveController;

    public OpenEditTransactionFormUI() {
        super("Edit Transaction Form");
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
            if (saveController != null) {
                try {
                    String id = txtTransactionId.getText();
                    String date = txtTransactionDate.getText();
                    double price = Double.parseDouble(txtUnitPrice.getText());
                    double areaVal = Double.parseDouble(txtArea.getText());
                    String type = txtTransactionType.getText();
                    String land = txtLandType.getText();
                    String house = txtHouseType.getText();
                    String addr = txtAddress.getText();
                    boolean success = saveController.execute(id, date, price, areaVal, type, land, house, addr);
                    if (success) {
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
            } else {
                JOptionPane.showMessageDialog(this, "Save functionality is not initialized.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void setModel(OpenEditTransactionFormModel model) {
        this.model = model;
        model.addSubscriber(this);
    }

    public void setSaveController(SaveEditTransactionController saveController) {
        this.saveController = saveController;
    }

    @Override
    public void update() {
        if (model.transactionItems != null && !model.transactionItems.isEmpty()) {
            OpenEditTransactionFormItem item = model.transactionItems.get(0);
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