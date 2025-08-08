package presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import business.TransactionAverageUsecase;
import java.awt.*;
import java.text.ParseException;

public class TransactionListViewUI extends JFrame implements Subscriber 
{
    private TransactionListViewController controller;
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
    private TotalTransactionViewUI totalTransactionViewUI;
    private TotalTransactionViewController totalTransactionController;

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
        txtSearch.addActionListener(e -> {
            try {
                controller.searchTransaction(txtSearch.getText());
            } catch (ParseException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAdd = new JButton("Add");
        btnEdit = new JButton("Edit");
        btnDelete = new JButton("Delete");
        btnSearch = new JButton("Seach");
        btnTotal = new JButton("Calculate Total");
        btnAverage = new JButton("Calculate Average");
        btnMonth = new JButton("Print Month's Transaction");
        btnSearch.addActionListener(e -> {
            try {
                controller.searchTransaction(txtSearch.getText());
            } catch (ParseException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        btnEdit.addActionListener(e -> controller.editTransaction(table.getSelectedRow()));
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

        String[] cols = { "STT", "Mã GD", "Ngày giao dịch", "Loại giao dịch", "Đơn giá", "Diện tích", "Thành tiền",
                "Loại đất", "Loại nhà", "Địa chỉ" };
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

        btnTotal.addActionListener(e -> 
        {
            totalTransactionViewUI.setVisible(true);
        });
    }

    // private void makeBtnAverageWork(JButton btnAverage) {
    //     btnAverage.addActionListener(e -> {
            
    //     });
    // }

    public JButton getBtnAverage() {return btnAverage;}

    public JButton getBtnMonth() {return btnMonth;}

    public void setViewModel(TransactionViewModel viewModel) {
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
                        item.amountTotal,
                        item.landType != null ? item.landType : "",
                        item.houseType != null ? item.houseType : "",
                        item.address != null ? item.address : ""
                };
                model.addRow(row);
            }
        
    }

    public void setController(TransactionListViewController controller) {
        this.controller = controller;
    }

    public void setTotalTransactionView(TotalTransactionViewUI view, TotalTransactionViewController controller)
    {
        this.totalTransactionViewUI = view;
        this.totalTransactionController = controller;
        this.totalTransactionViewUI.setController(controller);
    }

    @Override
    public void updateData(Object data) {
        if (data instanceof TransactionViewModel) {
            showList((TransactionViewModel) data);
        }
    }

    @Override
    public void update() {
         this.showList(viewModel);
        
    }
}