package presentation;

import javax.swing.*;
import java.awt.*;

public class TransactionAverageShowUI extends JDialog{
    private JLabel resultLabel;

    public TransactionAverageShowUI(Frame parent, String average) 
    {
        super(parent, "Kết quả trung bình giao dịch", true);
        setLayout(new BorderLayout());
        resultLabel = new JLabel("Trung bình các giao dịch: " + average);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(resultLabel, BorderLayout.CENTER);

        JButton btnClose = new JButton("Đóng");
        btnClose.addActionListener(e -> dispose());
        add(btnClose, BorderLayout.SOUTH);

        setSize(300, 150);
        setLocationRelativeTo(parent);
    }
}

