package presentation;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TransactionMonthShowUI extends JDialog implements Subscriber{
    private JTable table;
    private DefaultTableModel tableModel;
    private TransactionViewModel viewModel;

    public TransactionMonthShowUI() {
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

    public void setViewModel(TransactionViewModel viewModel) {
        this.viewModel = viewModel;
        viewModel.addSubscriber(this);

    }

    private void showMonthList(TransactionViewModel transactionViewModel) {
    tableModel.setRowCount(0); 
    int stt = 1;
    if (transactionViewModel != null && transactionViewModel.transactionList != null) {
        for (TransactionViewItem item : transactionViewModel.transactionList) {
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
    }
}

    @Override
    public void update() {
        this.showMonthList(viewModel);
    }

    @Override
    public void updateData(Object data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateData'");
    }
}
