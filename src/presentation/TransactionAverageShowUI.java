package presentation;

import javax.swing.*;
import java.awt.*;

public class TransactionAverageShowUI extends JDialog implements Subscriber{
    private JLabel label;
    private TransactionAverageModel model;

    public TransactionAverageShowUI()  {
        super((Frame) null, "Kết quả trung bình thành tiền", true);
        setLayout(new BorderLayout());

        label = new JLabel("", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        add(label, BorderLayout.CENTER);

        JButton btnClose = new JButton("Đóng");
        btnClose.addActionListener(e -> dispose());
        add(btnClose, BorderLayout.SOUTH);

        setSize(500, 150);
        setLocationRelativeTo(null);
    }

    public void setModel(TransactionAverageModel model) {
        this.model = model;
        model.addSubscriber(this);
    }

    private void show(double amountAverage) {
        label.setText("Trung bình thành tiền của các giao dịch là: " + String.format("%,.2f", amountAverage));
        setVisible(true);
    }

    @Override
    public void updateData(Object data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateData'");
    }

    @Override
    public void update() {
        this.show(model.amountAverage);
    }
}
