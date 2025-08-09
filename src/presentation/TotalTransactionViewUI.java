package presentation;

import javax.swing.*;
import java.awt.*;

public class TotalTransactionViewUI extends JFrame implements Subscriber 
{
    private JRadioButton landButton, houseButton;
    private JTextField totalField;
    private TotalTransactionViewModel viewModel;

    public TotalTransactionViewUI() {
        setTitle("Transaction Total Viewer");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel trên: Radio buttons
        JPanel topPanel = new JPanel();
        landButton = new JRadioButton("TransactionLand");
        houseButton = new JRadioButton("TransactionHouse");

        ButtonGroup group = new ButtonGroup();
        group.add(landButton);
        group.add(houseButton);

        topPanel.add(landButton);
        topPanel.add(houseButton);
        add(topPanel, BorderLayout.NORTH);

        // Panel dưới: Hiển thị tổng
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(new JLabel("Total:"));
        totalField = new JTextField(10);
        totalField.setEditable(false);
        bottomPanel.add(totalField);
        add(bottomPanel, BorderLayout.SOUTH);

        
    }

    public JRadioButton getLandButton() { return landButton; }
    public JRadioButton getHouseButton() { return houseButton; }

    public void setViewModel(TotalTransactionViewModel viewModel) {
        this.viewModel = viewModel;
        viewModel.addSubscriber(this);
    }
    public void ShowTotal(int totalCount)
    {
        totalField.setText(String.valueOf(totalCount));
    }

    @Override
    public void update() 
    {
        ShowTotal(viewModel.getTotalTransactionCount());
    }
    
    @Override
    public void updateData(Object data) {
        throw new UnsupportedOperationException("Unimplemented method 'updateData'");
    }
}
