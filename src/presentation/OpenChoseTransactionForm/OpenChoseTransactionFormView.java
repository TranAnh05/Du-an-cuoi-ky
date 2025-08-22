package presentation.OpenChoseTransactionForm;

import javax.swing.*;

import business.TotalTransaction.TotalTransactionUseCase;
import persistence.TotalTransaction.TotalTransactionDAO;
import persistence.TotalTransaction.TotalTransactionGateway;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import presentation.Subscriber;
import presentation.TotalTransaction.TotalTransactionController;
import presentation.TotalTransaction.TotalTransactionModel;

public class OpenChoseTransactionFormView extends JFrame implements Subscriber 
{
    private JComboBox<String> transactionTypeCombo;
    private JTextField totalField;
    private OpenChoseTransactionFormModel transactionModel;
    private JButton calculateBtn;

    public OpenChoseTransactionFormView() 
    {
        initUI();
        loadTransactionType();
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

        calculateBtn = new JButton("Calculate Total");
        bottomPanel.add(calculateBtn);
        add(bottomPanel, BorderLayout.SOUTH);
        calculateBtn.addActionListener(new ActionListener() 
        {

            @Override
            public void actionPerformed(ActionEvent e) 
            {
                TotalTransactionModel model = new TotalTransactionModel();
                TotalTransactionGateway gateway = null;
                try 
                {
                    gateway = new TotalTransactionDAO();
                } 
                catch (ClassNotFoundException | SQLException e1) 
                {
                    e1.printStackTrace();
                }
                TotalTransactionUseCase useCase = new TotalTransactionUseCase(gateway);
                TotalTransactionController controller = new TotalTransactionController(model, useCase);
                
                // Đăng ký subscriber để cập nhật UI
                model.addSubscriber(() -> totalField.setText(String.valueOf(model.total)));

                String selectedType = (String) transactionTypeCombo.getSelectedItem();
                if (selectedType != null) {
                    try 
                    {
                        controller.execute(selectedType);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(OpenChoseTransactionFormView.this, "Error: " + ex.getMessage());
                    }
                }
            }
            
        });
    }
    public void setModel(OpenChoseTransactionFormModel transactionModel) 
    {
        this.transactionModel = transactionModel;
        transactionModel.addSubscriber(this);
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
    public void update() 
    {
        loadTransactionType();
        this.setVisible(true);
    }
}
