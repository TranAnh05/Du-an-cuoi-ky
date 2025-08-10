package presentation.CalculateLandAverage;

import javax.swing.*;

import presentation.Subscriber;

import java.awt.*;

public class CalculateLandAverageView extends JDialog implements Subscriber{
    private JLabel label;
    private CalculateLandAverageModel model;

    public CalculateLandAverageView()  {
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

    public void setModel(CalculateLandAverageModel model) {
        this.model = model;
        model.addSubscriber(this);
    }

    private void show(String amountAverage) {
        label.setText("Trung bình thành tiền của các giao dịch đất là: " + amountAverage);
        setVisible(true);
    }

    @Override
    public void update() {
        this.show(model.amountAverage);
    }
}
