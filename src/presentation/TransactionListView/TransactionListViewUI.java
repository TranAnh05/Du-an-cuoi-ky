package presentation.TransactionListView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import business.OpenAddTransactionForm.OpenAddTransactionFormUsecase;
import business.OpenChooseMonthForm.OpenChooseMonthFormUsecase;
import business.OpenChoseTransactionForm.OpenChoseTransactionFormUseCase;
import business.TotalTransaction.TotalTransactionUseCase;
import persistence.OpenAddTransactionForm.OpenAddTransactionFormDAO;
import persistence.OpenChooseMonthForm.OpenChooseMonthFormDAO;
import persistence.OpenChoseTransactionForm.OpenChoseTransactionDAO;
import persistence.TotalTransaction.TotalTransactionDAO;
import presentation.Subscriber;
import presentation.CalculateLandAverage.CalculateLandAverageController;
import presentation.OpenAddTransactionForm.OpenAddTransactionFormController;
import presentation.OpenAddTransactionForm.OpenAddTransactionFormModel;
import presentation.OpenAddTransactionForm.OpenAddTransactionFormView;
import presentation.OpenEditTransactionForm.OpenEditTransactionFormController;
import presentation.OpenEditTransactionForm.OpenEditTransactionFormUI;
import presentation.OpenChooseMonthForm.OpenChooseMonthFormController;
import presentation.OpenChooseMonthForm.OpenChooseMonthFormModel;
import presentation.OpenChooseMonthForm.OpenChooseMonthFormView;
import presentation.SearchTransaction.SearchTransactionController;
import presentation.SearchTransaction.SearchTransactionModel;
import presentation.SearchTransaction.SearchTransactionItem;
import presentation.OpenChoseTransactionForm.OpenChoseTransactionFormController;
import presentation.OpenChoseTransactionForm.OpenChoseTransactionFormModel;
import presentation.OpenChoseTransactionForm.OpenChoseTransactionFormView;
import presentation.TotalTransaction.TotalTransactionController;
import presentation.TotalTransaction.TotalTransactionModel;

import java.awt.*;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class TransactionListViewUI extends JFrame implements Subscriber {
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

    private TransactionViewModel viewModel; // For initial list and edit updates
    private SearchTransactionModel searchModel; // For search results

    // relative to edit form
    private OpenEditTransactionFormController editFormController;
    private OpenEditTransactionFormUI editFormUI;

    // relative to search
    private SearchTransactionController searchController;

    public TransactionListViewUI() {
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
        btnSearch = new JButton("Search");
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
        add(topPanel, BorderLayout.NORTH);

        String[] cols = {"STT", "Mã GD", "Ngày giao dịch", "Loại giao dịch", "Đơn giá", "Diện tích", "Thành tiền"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class; // Ensure all columns are treated as strings
            }
        };

        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Event handlers
        btnAdd.addActionListener(e -> {
            OpenAddTransactionFormDAO addDAO = null;
            OpenAddTransactionFormUsecase addUsecase = null;
            OpenAddTransactionFormController addController = null;

            OpenAddTransactionFormModel AddModel = new OpenAddTransactionFormModel();
            OpenAddTransactionFormView addView = new OpenAddTransactionFormView();
            addView.setModel(AddModel);
            
            try {
                addDAO = new OpenAddTransactionFormDAO();
                addUsecase = new OpenAddTransactionFormUsecase(addDAO);
                addController = new OpenAddTransactionFormController(addUsecase, AddModel);
                addController.execute();

            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });


        btnEdit.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String transactionId = (String) model.getValueAt(selectedRow, 1); // Column 1 is transactionId
                if (editFormController != null) {
                    try {
                        editFormController.execute(transactionId);
                        editFormUI.setVisible(true);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Failed to load transaction details.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Edit functionality is not initialized. Check database connection.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a transaction to edit.");
            }
        });

        btnSearch.addActionListener(e -> {
            if (searchController != null) {
                try {
                    searchController.execute(txtSearch.getText());
                    System.out.println("Search executed with term: " + txtSearch.getText() + ", UI update triggered");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Search failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Search functionality is not initialized.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnAverage.addActionListener(e -> {
            try {
                if (averageController != null) {
                    averageController.execute();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        btnMonth.addActionListener(e -> {
            OpenChooseMonthFormDAO monthFormDAO = null;
            OpenChooseMonthFormUsecase monthFormUsecase = null;
            OpenChooseMonthFormController monthFormController = null;

            OpenChooseMonthFormModel monthFormModel = new OpenChooseMonthFormModel();
            OpenChooseMonthFormView monthFormView = new OpenChooseMonthFormView();

            try {
                monthFormDAO = new OpenChooseMonthFormDAO();
                monthFormUsecase = new OpenChooseMonthFormUsecase(monthFormDAO);
                monthFormController = new OpenChooseMonthFormController(monthFormUsecase, monthFormModel);
                monthFormView.setModel(monthFormModel);
                monthFormController.execute();

            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

        // TotalTransaction
        btnTotal.addActionListener(e -> 
        {
        try 
        {
            // Tạo form chọn loại giao dịch
            OpenChoseTransactionFormModel openModel = new OpenChoseTransactionFormModel();
            OpenChoseTransactionDAO openDAO = new OpenChoseTransactionDAO();
            OpenChoseTransactionFormUseCase openUseCase = new OpenChoseTransactionFormUseCase(openDAO);
            OpenChoseTransactionFormController openController = new OpenChoseTransactionFormController(openModel, openUseCase);
            OpenChoseTransactionFormView openView = new OpenChoseTransactionFormView();
            openView.setModel(openModel);

            // Tạo model & controller cho phần tính tổng
            TotalTransactionModel totalModel = new TotalTransactionModel();
            TotalTransactionDAO totalDAO = new TotalTransactionDAO();
            TotalTransactionUseCase totalUseCase = new TotalTransactionUseCase(totalDAO);
            TotalTransactionController totalController = new TotalTransactionController(totalModel, totalUseCase);

            // Gắn nút Calculate với controller
            openView.bindCalculateButton(totalController, totalModel);

            // Load dữ liệu loại giao dịch
            openController.execute(); 

            } catch (ClassNotFoundException | SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    // relative to average
    public void setAverageController(CalculateLandAverageController averageController) {
        this.averageController = averageController;
    }

    public void setViewModel(TransactionViewModel viewModel) {
        this.viewModel = viewModel;
        viewModel.addSubscriber(this);
    }

    public void setSearchModel(SearchTransactionModel searchModel) {
        this.searchModel = searchModel;
        searchModel.addSubscriber(this); // Subscribe to SearchTransactionModel for search updates
    }

    public void showList(TransactionViewModel transactionViewModel) {
        model.setRowCount(0); // Clear existing rows
        System.out.println("Updating table with " + transactionViewModel.transactionList.size() + " items from viewModel"); // Debug
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
        System.out.println("UI update called"); // Debug
        if (searchModel != null && searchModel.transactionItems != null) {
            model.setRowCount(0); // Clear existing rows for search results
            System.out.println("Updating table with " + searchModel.transactionItems.size() + " items from searchModel"); // Debug
            DecimalFormat df = new DecimalFormat("#,##0.00"); // Format to avoid scientific notation
            for (SearchTransactionItem item : searchModel.transactionItems) {
                Object[] row = {
                    item.stt,
                    item.transactionId,
                    item.transactionDate.toString(),
                    item.transactionType,
                    String.valueOf(item.unitPrice),
                    String.valueOf(item.area),
                    df.format(item.unitPrice * item.area) // Format amountTotal
                };
                model.addRow(row);
            }
        } else if (viewModel != null) {
            showList(viewModel); // Fall back to viewModel for initial list and edit updates
        }
    }

    // relative to edit form
    public void setEditFormController(OpenEditTransactionFormController editFormController) {
        this.editFormController = editFormController;
    }

    public void setEditFormUI(OpenEditTransactionFormUI editFormUI) {
        this.editFormUI = editFormUI;
    }

    // relative to search
    public void setSearchController(SearchTransactionController searchController) {
        this.searchController = searchController;
    }
}