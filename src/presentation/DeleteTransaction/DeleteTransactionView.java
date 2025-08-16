package presentation.DeleteTransaction;

import javax.swing.*;
import java.awt.*;
import business.DeleteTransaction.DeleteTransactionViewDTO;

public class DeleteTransactionView extends JFrame {
    private final JTextField txtId = new JTextField(20);
    private final JButton btnDelete = new JButton("Delete");

    private DeleteTransactionController controller;
    private DeleteTransactionModel model;

    public DeleteTransactionView() {
        super("Delete Transaction");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 140);
        setLocationRelativeTo(null);

        JPanel top = new JPanel(new BorderLayout(5,5));
        top.add(new JLabel("Transaction ID:"), BorderLayout.WEST);
        top.add(txtId, BorderLayout.CENTER);
        add(top, BorderLayout.NORTH);
        add(btnDelete, BorderLayout.SOUTH);

        btnDelete.addActionListener(e -> {
            String id = txtId.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập ID");
                return;
            }
            try {
                boolean ok = controller != null && controller.execute(id);
                if (ok) {
                    DeleteTransactionViewDTO info = model != null ? model.getDeletedTransaction() : null;
                    JOptionPane.showMessageDialog(this, info != null ? ("Đã xóa: " + info.getTransactionId()) : "Đã xóa");
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy giao dịch: " + id);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Xóa thất bại: " + ex.getMessage());
            }
        });
    }

    public void setController(DeleteTransactionController controller) { this.controller = controller; }
    public void setModel(DeleteTransactionModel model) { this.model = model; }
}
