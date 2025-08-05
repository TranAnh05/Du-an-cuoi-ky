package presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import business.TransactionAverageUsecase;

import java.awt.*;
public class TransactionListViewUI extends JFrame implements Subscriber{
    private JTextField txtSearch;
    private JButton btnAdd;
    private JButton btnEdit; // Nút sửa
    private JButton btnDelete; // Nút xóa
    private JButton btnSearch;
    private JButton btnTotal;
    private JButton btnAverage;
    private JButton btnMonth;
    private JTable table;
    private DefaultTableModel model;

    private TransactionViewModel viewModel;


    public TransactionListViewUI() {
        // --- Cài đặt cho JFrame ---
        super("Quản lý danh sách giao dịch");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 600); 
        setLocationRelativeTo(null); 

        // --- Panel Top: Chứa các nút chức năng và ô tìm kiếm ---
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));

        // Ô tìm kiếm
        txtSearch = new JTextField();

        // Panel cho các nút
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 5, 5));
        btnAdd = new JButton("Add");
        btnEdit = new JButton("Edit");
        btnDelete = new JButton("Delete");
        btnSearch = new JButton("Seach");
        btnTotal = new JButton("Calculate Total");
        btnAverage = new JButton("Calculate Average");
        btnMonth = new JButton("Print Month's Transaction");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnTotal);
        buttonPanel.add(btnAverage);
        buttonPanel.add(btnMonth);

        // Thêm các thành phần vào topPanel
        topPanel.add(txtSearch, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        // Thêm topPanel vào JFrame
        add(topPanel, BorderLayout.NORTH);
        String[] cols = {"STT", "Mã GD", "Loại giao dịch", "Ngày giao dịch", "Đơn giá", "Diện tích", "Thành tiền"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setFillsViewportHeight(true); 
        table.setRowHeight(25);

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    // private void makeBtnAverageWork(JButton btnAverage) {
    //     btnAverage.addActionListener(e -> {
            
    //     });
    // }

    public JButton getBtnAverage() {return btnAverage;}

    public void setViewModel(TransactionViewModel viewModel) {
        this.viewModel = viewModel;
        viewModel.addSubscriber(this);
    }


    private void showList(TransactionViewModel transactionViewModel) {
        model.setRowCount(0);

        for (TransactionViewItem item : transactionViewModel.transactionList) {
            Object[] row = {
                    item.stt,
                    item.transactionId,
                    item.transactionType,
                    item.transactionDate,
                    item.unitPrice,
                    item.area,
                    item.amountTotal
            };
            model.addRow(row);
        }
     }

     public void update() {
        this.showList(viewModel);
     }
}
