package presentation.OpenChoseTransactionForm;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import presentation.Subscriber;

public class OpenChoseTransactionFormView extends JFrame implements Subscriber 
{
    private JComboBox<String> transactionTypeCombo;
    private JTextField totalField;
    private JButton calculateBtn;
    private OpenChoseTransactionFormModel transactionModel;

    public OpenChoseTransactionFormView() 
    {
        initUI();
        // loadTransactionType(); // Chuyển load sau khi setModel để có dữ liệu
    }

    private void initUI() 
    {
        setTitle("Transaction Total Viewer");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel trên: Combo box chọn loại giao dịch
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Transaction Type:"));

        transactionTypeCombo = new JComboBox<>();
        topPanel.add(transactionTypeCombo);

        add(topPanel, BorderLayout.NORTH);

        // Panel dưới: Hiển thị tổng và nút Calculate
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(new JLabel("Total:"));
        totalField = new JTextField(10);
        totalField.setEditable(false);
        bottomPanel.add(totalField);

        add(bottomPanel, BorderLayout.SOUTH);

        // Xử lý sự kiện: khi chọn loại giao dịch thay đổi, tự động cập nhật tổng
        transactionTypeCombo.addActionListener(e -> 
        {
            updateTotalForSelectedType();
        });

    }
    private void updateTotalForSelectedType() 
    {
        String selectedType = (String) transactionTypeCombo.getSelectedItem();
        if (selectedType == null || selectedType.isEmpty()) {
            totalField.setText("");
            return;
        }
        if (transactionModel == null) {
            JOptionPane.showMessageDialog(this, 
                "Model chưa được khởi tạo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int total = transactionModel.getTotalByType(selectedType);
            totalField.setText(String.valueOf(total));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tính tổng: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setModel(OpenChoseTransactionFormModel transactionModel) 
    {
        this.transactionModel = transactionModel;
        transactionModel.addSubscriber(this);
        loadTransactionType();
    }

    private void loadTransactionType() 
    {
        transactionTypeCombo.removeAllItems();
        if (transactionModel != null && transactionModel.typeItems != null) 
        {
            for (TransactionTypeItem item : transactionModel.typeItems) 
            {
                transactionTypeCombo.addItem(item.type);
            }
        }
    }

    @Override
    public void update() {
        loadTransactionType();
        this.setVisible(true);
    }
}