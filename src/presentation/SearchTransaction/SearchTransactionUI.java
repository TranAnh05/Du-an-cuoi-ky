package presentation.SearchTransaction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import presentation.Subscriber;

public class SearchTransactionUI extends JFrame implements Subscriber {
    private JTextField txtSearch;
    private JButton btnSearch;
    private JTable table;
    private DefaultTableModel tableModel; // Renamed to avoid conflict with model
    private SearchTransactionModel model; // Model for Publisher-Subscriber
    private SearchTransactionController controller;

    public SearchTransactionUI() {
        super("Search Transactions");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(5, 5));
        txtSearch = new JTextField(20);
        btnSearch = new JButton("Search");
        panel.add(txtSearch, BorderLayout.CENTER);
        panel.add(btnSearch, BorderLayout.EAST);

        String[] cols = {"STT", "Mã GD", "Ngày giao dịch", "Loại giao dịch", "Đơn giá", "Diện tích", "Thành tiền"};
        tableModel = new DefaultTableModel(cols, 0); // Initialize table model
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(panel, BorderLayout.NORTH);

        btnSearch.addActionListener(e -> {
            try {
                controller.execute(txtSearch.getText());
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Search failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void setModel(SearchTransactionModel model) {
        this.model = model;
        model.addSubscriber(this);
    }

    public void setController(SearchTransactionController controller) {
        this.controller = controller;
    }

    @Override
    public void update() {
        tableModel.setRowCount(0); // Clear existing rows
        if (model != null && model.transactionItems != null) {
            for (SearchTransactionItem item : model.transactionItems) {
                Object[] row = {
                    item.stt,
                    item.transactionId,
                    item.transactionDate,
                    item.transactionType,
                    item.unitPrice,
                    item.area,
                    String.valueOf(item.unitPrice * item.area), // Simplified amount
                };
                tableModel.addRow(row);
            }
        }
    }
}