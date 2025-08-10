package presentation.TransactionListView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import presentation.Subscriber;
import presentation.CalculateLandAverage.CalculateLandAverageController;

import java.awt.*;
import java.sql.SQLException;

public class TransactionListViewUI extends JFrame implements Subscriber 
{
    private JTextField txtSearch;
    private JButton btnAdd;
    private JButton btnEdit; 
    private JButton btnDelete; 
    private JButton btnSearch;
    private JButton btnTotal;
    private JButton btnAverage;
    private JButton btnMonth;
    private JTable table;
    private DefaultTableModel model;

    // relative to month

    // relative to average
    private CalculateLandAverageController averageController;

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
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
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

        String[] cols = { "STT", "Mã GD", "Ngày giao dịch", "Loại giao dịch", "Đơn giá", "Diện tích", "Thành tiền"};
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

        // Event
        btnAverage.addActionListener(e -> {
            try {
                averageController.execute();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    // relative to average
    public void setAverageController(CalculateLandAverageController averageController){
        this.averageController = averageController;
    }

    public void setViewModel(TransactionViewModel viewModel) 
    {
        this.viewModel = viewModel;
        viewModel.addSubscriber(this);
    }
    
    public void showList(TransactionViewModel transactionViewModel) {

        model.setRowCount(0);
        for (TransactionViewItem item : transactionViewModel.transactionList) {
            Object[] row = {
                    item.stt,
                    item.transactionId,
                    item.transactionDate,
                    item.transactionType,
                    item.unitPrice,
                    item.area,
                    item.amountTotal
            };
            model.addRow(row);
        }
    }

    @Override
    public void update() {
         this.showList(viewModel);
    }
}