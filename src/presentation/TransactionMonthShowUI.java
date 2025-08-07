package presentation;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import business.TransactionViewDTO;

public class TransactionMonthShowUI extends JDialog{
    private JTable table;
    private DefaultTableModel tableModel;

    public TransactionMonthShowUI(Frame parent, List<TransactionViewDTO> listViewDTO) {
        super(parent, "Danh sách giao dịch theo tháng", true);
        setLayout(new BorderLayout());

        // Convert sang TransactionViewItem
        List<TransactionViewItem> items = convertToViewItems(listViewDTO);

        // Tạo bảng
        String[] columnNames = {"STT", "ID", "Ngày", "Đơn giá", "Diện tích", "Loại GD", "Tổng tiền"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        int stt = 1;
        for (TransactionViewItem item : items) {
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

        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnClose = new JButton("Đóng");
        btnClose.addActionListener(e -> dispose());
        add(btnClose, BorderLayout.SOUTH);

        setSize(900, 400);
        setLocationRelativeTo(parent);
    }

    // Hàm convert từ TransactionViewDTO sang TransactionViewItem
    private List<TransactionViewItem> convertToViewItems(List<TransactionViewDTO> listViewDTO) {
        java.util.List<TransactionViewItem> items = new java.util.ArrayList<>();
        int stt = 1;
        for (TransactionViewDTO dto : listViewDTO) {
            TransactionViewItem item = new TransactionViewItem();
            NumberFormat amountFormatter = new DecimalFormat("#,##0.###");
            item.stt = stt++;
            item.transactionId = dto.transactionId;
            item.transactionDate = dto.transactionDate != null ? dto.transactionDate.toString() : "";
            item.unitPrice = String.valueOf(dto.unitPrice);
            item.area = String.valueOf(dto.area);
            item.transactionType = dto.transactionType;
            item.amountTotal = amountFormatter.format(dto.amountTotal);
            items.add(item);
        }
        return items;
    }
}
