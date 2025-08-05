package presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import business.TransactionViewItem;
import business.TransactionViewModel;
import java.awt.*;

public class TransactionListViewUI extends JFrame implements Subscriber {
    private JTextField txtSearch;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnSearch;
    private JTable table;
    private DefaultTableModel model;
    private TransactionListViewController controller;

    public TransactionListViewUI() {
        super("Quản lý danh sách giao dịch");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        txtSearch = new JTextField(20);
        txtSearch.addActionListener(e -> controller.searchTransaction(txtSearch.getText()));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnSearch = new JButton("Tìm kiếm");
        btnSearch.addActionListener(e -> controller.searchTransaction(txtSearch.getText()));
        btnEdit.addActionListener(e -> controller.editTransaction(table.getSelectedRow()));
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnSearch);

        topPanel.add(txtSearch, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        String[] cols = { "STT", "Mã GD", "Ngày giao dịch", "Loại giao dịch", "Đơn giá", "Diện tích", "Thành tiền", "Loại đất", "Loại nhà", "Địa chỉ" };
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void showList(TransactionViewModel transactionViewModel) {
        model.setRowCount(0);
        if (transactionViewModel != null && transactionViewModel.transactionList != null) {
            for (TransactionViewItem item : transactionViewModel.transactionList) {
                Object[] row = {
                    item.stt,
                    item.transactionId,
                    item.transactionDate,
                    item.transactionType,
                    item.unitPrice,
                    item.area,
                    item.amountTotal,
                    item.landType != null ? item.landType : "",
                    item.houseType != null ? item.houseType : "",
                    item.address != null ? item.address : ""
                };
                model.addRow(row);
            }
        }
    }

    public void setController(TransactionListViewController controller) {
        this.controller = controller;
    }

    @Override
    public void update(Object data) {
        if (data instanceof TransactionViewModel) {
            showList((TransactionViewModel) data);
        }
    }
}