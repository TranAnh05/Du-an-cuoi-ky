package presentation.PrintMonthTransaction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import presentation.Subscriber;

import java.awt.*;

public class PrintMonthTransactionView extends JDialog implements Subscriber {
    private JTable table;
    private DefaultTableModel tableModel;
    private PrintMonthTransactionModel monthModel;

    public PrintMonthTransactionView() {
        super((Frame) null, "Danh sách giao dịch theo tháng", true);
        setLayout(new BorderLayout());

        // Tạo bảng
        String[] columnNames = {"STT", "ID", "Ngày", "Đơn giá", "Diện tích", "Loại GD", "Tổng tiền"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnClose = new JButton("Đóng");
        btnClose.addActionListener(e -> dispose());
        add(btnClose, BorderLayout.SOUTH);

        setSize(900, 400);
        setLocationRelativeTo(null);
    }

     public void setViewModel(PrintMonthTransactionModel monthModel) {
        this.monthModel = monthModel;
        monthModel.addSubscriber(this);
    }

    private void showMonthList(PrintMonthTransactionModel monthModel) {
        tableModel.setRowCount(0);
        int stt = 1;

        for (TransactionMonthViewItem item : monthModel.transactionList) {
            tableModel.addRow(new Object[]{
                stt++,
                item.transactionId,
                item.transactionDate,
                item.unitPrice,
                item.area,
                item.transactionType,
                item.amountTotal,
            });
        }

        setVisible(true);
    }

     @Override
     public void update() {
        this.showMonthList(monthModel);
     }
}
