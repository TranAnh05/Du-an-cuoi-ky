package presentation.OpenAddTransactionForm;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import business.SaveTransaction.SaveTransactionUsecase;
import business.SaveTransaction.SavedDataDTO;
import business.TransactionListView.TransactionListViewUseCase;
import persistence.SaveTransaction.SaveTransactionDAO;
import persistence.TransactionListView.TransactionListViewDAO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import presentation.Subscriber;
import presentation.TransactionListView.TransactionListViewController;
import presentation.TransactionListView.TransactionListViewUI;
import presentation.TransactionListView.TransactionViewModel;

public class OpenAddTransactionFormView extends JFrame implements Subscriber {
    // Form input fields
    private JTextField txtTransactionId;
    private JSpinner dateSpinner;
    private JTextField txtPrice;
    private JTextField txtArea;
    private JComboBox<TransactionTypeItem> cmbTransactionType;

    // Panels for conditional fields
    private JPanel pnlLandType;
    private JPanel pnlHouseInfo;
    private JTextField txtLandType;
    private JTextField txtHouseType;
    private JTextField txtAddress;

    // Action buttons
    private JButton btnSave;
    private JButton btnCancel;

    // Model reference
    private OpenAddTransactionFormModel model;

    public OpenAddTransactionFormView() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        loadTransactionTypes();
    }

    private void initializeComponents() {
        txtTransactionId = new JTextField(20);
        txtTransactionId.setText("GD");
        dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));
        txtPrice = new JTextField(20);
        txtArea = new JTextField(20);
        cmbTransactionType = new JComboBox<>();

        // Custom renderer for transaction type
        cmbTransactionType.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof TransactionTypeItem) {
                    TransactionTypeItem type = (TransactionTypeItem) value;
                    setText(type.transactionTypeName);
                }
                return this;
            }
        });

        txtLandType = new JTextField(20);
        txtHouseType = new JTextField(20);
        txtAddress = new JTextField(20);

        btnSave = new JButton("Lưu");
        btnCancel = new JButton("Hủy");

        pnlLandType = new JPanel(new GridBagLayout());
        pnlHouseInfo = new JPanel(new GridBagLayout());
    }

    private void setupLayout() {
        setTitle("Thêm Giao Dịch Mới");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Basic information
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Mã GD:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtTransactionId, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Ngày GD:"), gbc);
        gbc.gridx = 1;
        formPanel.add(dateSpinner, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Giá tiền:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtPrice, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Diện tích:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtArea, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Loại giao dịch:"), gbc);
        gbc.gridx = 1;
        formPanel.add(cmbTransactionType, gbc);

        // Land type panel
        pnlLandType.setLayout(new GridBagLayout());
        GridBagConstraints gbcLand = new GridBagConstraints();
        gbcLand.insets = new Insets(5, 5, 5, 5);
        gbcLand.anchor = GridBagConstraints.WEST;
        gbcLand.gridx = 0; gbcLand.gridy = 0;
        pnlLandType.add(new JLabel("Loại đất:"), gbcLand);
        gbcLand.gridx = 1;
        pnlLandType.add(txtLandType, gbcLand);

        // House info panel
        pnlHouseInfo.setLayout(new GridBagLayout());
        GridBagConstraints gbcHouse = new GridBagConstraints();
        gbcHouse.insets = new Insets(5, 5, 5, 5);
        gbcHouse.anchor = GridBagConstraints.WEST;
        gbcHouse.gridx = 0; gbcHouse.gridy = 0;
        pnlHouseInfo.add(new JLabel("Loại nhà:"), gbcHouse);
        gbcHouse.gridx = 1;
        pnlHouseInfo.add(txtHouseType, gbcHouse);
        gbcHouse.gridx = 0; gbcHouse.gridy = 1;
        pnlHouseInfo.add(new JLabel("Địa chỉ:"), gbcHouse);
        gbcHouse.gridx = 1;
        pnlHouseInfo.add(txtAddress, gbcHouse);

        // Add conditional panels to form
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        formPanel.add(pnlLandType, gbc);

        gbc.gridy = 6;
        formPanel.add(pnlHouseInfo, gbc);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        gbc.gridy = 7;
        formPanel.add(buttonPanel, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);

        // Initially hide conditional panels
        pnlLandType.setVisible(false);
        pnlHouseInfo.setVisible(false);
    }

    private void setupEventHandlers() {
        cmbTransactionType.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TransactionTypeItem selectedType = (TransactionTypeItem) cmbTransactionType.getSelectedItem();
                if (selectedType != null) {
                    if ("GDD".equals(selectedType.transactionTypeCode)) {
                        pnlLandType.setVisible(true);
                        pnlHouseInfo.setVisible(false);
                    } else if ("GDN".equals(selectedType.transactionTypeCode)) {
                        pnlLandType.setVisible(false);
                        pnlHouseInfo.setVisible(true);
                    } else {
                        pnlLandType.setVisible(false);
                        pnlHouseInfo.setVisible(false);
                    }
                } else {
                    pnlLandType.setVisible(false);
                    pnlHouseInfo.setVisible(false);
                }
                revalidate();
                repaint();
            }
        });

        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveTransaction();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void loadTransactionTypes() {
        cmbTransactionType.removeAllItems();
        if (model != null && model.transactionTypeItems != null) {
            for (TransactionTypeItem type : model.transactionTypeItems) {
                cmbTransactionType.addItem(type);
            }
        }
    }

    private void saveTransaction() {
        // initial
        SaveTransactionDAO saveTransactionDAO = null;
        SaveTransactionUsecase saveTransactionUsecase = null;

        // Basic UI validation
        if (txtTransactionId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã giao dịch!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (txtPrice.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập giá tiền!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (txtArea.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập diện tích!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (cmbTransactionType.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn loại giao dịch!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String transactionId = txtTransactionId.getText().trim();
            java.util.Date transactionDate = (java.util.Date) dateSpinner.getValue();

            // Validate và parse price
            double price;
            try {
                price = Double.parseDouble(txtPrice.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Giá đơn vị phải là số hợp lệ", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate và parse area
            double area;
            try {
                area = Double.parseDouble(txtArea.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Diện tích phải là số hợp lệ", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
                return;
            }

            TransactionTypeItem selectedType = (TransactionTypeItem) cmbTransactionType.getSelectedItem();
            String transactionType = selectedType != null ? selectedType.transactionTypeCode : "";

            String landType = txtLandType.getText().trim();
            String houseType = txtHouseType.getText().trim();
            String address = txtAddress.getText().trim();

            SavedDataDTO savedData = new SavedDataDTO();
            savedData.transactionId = transactionId;
            savedData.transactionDate = LocalDate.ofInstant(transactionDate.toInstant(), ZoneId.systemDefault());
            savedData.unitPrice = price;
            savedData.area = area;
            savedData.transactionType = transactionType;
            savedData.landType = landType;
            savedData.houseType = houseType;
            savedData.address = address;

            saveTransactionDAO = new SaveTransactionDAO();
            saveTransactionUsecase = new SaveTransactionUsecase(saveTransactionDAO);

            

            boolean result = saveTransactionUsecase.execute(savedData);

            if(!result) {
                JOptionPane.showMessageDialog(this, "Mã giao dịch đã tồn tại", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(this, "Giao dịch đã được lưu thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Có lỗi khi xử lý dữ liệu: " + ex.getMessage(),
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        clearForm();
    }

    private void clearForm() {
        txtTransactionId.setText("GD");
        dateSpinner.setValue(new java.util.Date());
        txtPrice.setText("");
        txtArea.setText("");
        cmbTransactionType.setSelectedIndex(0);
        txtLandType.setText("");
        txtHouseType.setText("");
        txtAddress.setText("");
        pnlLandType.setVisible(false);
        pnlHouseInfo.setVisible(false);
        pnlLandType.setVisible(true);
    }

    public void setModel(OpenAddTransactionFormModel model) {
        this.model = model;
        model.addSubscriber(this);
    }

    @Override
    public void update() {
        loadTransactionTypes();
        this.setVisible(true);
    }
}