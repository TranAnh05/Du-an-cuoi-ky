package presentation;

import javax.swing.*;
import persistence.TransactionDTO;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TransactionEditDialog extends JDialog {
    private JTextField txtDate, txtUnitPrice, txtArea, txtTransactionType, txtLandType, txtHouseType, txtAddress;
    private JButton btnSave;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public TransactionEditDialog(JFrame parent, String transactionId) {
        super(parent, "Sửa giao dịch: " + transactionId, true);
        setLayout(new GridLayout(8, 2, 5, 5));
        setSize(400, 300);
        setLocationRelativeTo(parent);

        add(new JLabel("Ngày giao dịch (YYYY-MM-DD):"));
        txtDate = new JTextField();
        add(txtDate);

        add(new JLabel("Đơn giá:"));
        txtUnitPrice = new JTextField();
        add(txtUnitPrice);

        add(new JLabel("Diện tích:"));
        txtArea = new JTextField();
        add(txtArea);

        add(new JLabel("Loại giao dịch (GDĐ/GDN):"));
        txtTransactionType = new JTextField();
        add(txtTransactionType);

        add(new JLabel("Loại đất:"));
        txtLandType = new JTextField();
        add(txtLandType);

        add(new JLabel("Loại nhà:"));
        txtHouseType = new JTextField();
        add(txtHouseType);

        add(new JLabel("Địa chỉ:"));
        txtAddress = new JTextField();
        add(txtAddress);

        btnSave = new JButton("Lưu");
        add(btnSave);
        add(new JLabel());

        getRootPane().setDefaultButton(btnSave);
    }

    public JTextField getTxtDate() { return txtDate; }
    public JTextField getTxtUnitPrice() { return txtUnitPrice; }
    public JTextField getTxtArea() { return txtArea; }
    public JTextField getTxtTransactionType() { return txtTransactionType; }
    public JTextField getTxtLandType() { return txtLandType; }
    public JTextField getTxtHouseType() { return txtHouseType; }
    public JTextField getTxtAddress() { return txtAddress; }
    public JButton getSaveButton() { return btnSave; }

    public TransactionDTO getUpdatedDTO() throws IllegalArgumentException {
        TransactionDTO dto = new TransactionDTO();
        try {
            dto.transactionDate = LocalDate.parse(getTxtDate().getText(), DATE_FORMATTER);
            dto.unitPrice = Double.parseDouble(getTxtUnitPrice().getText());
            dto.area = Double.parseDouble(getTxtArea().getText());
            dto.transactionType = getTxtTransactionType().getText().trim().toUpperCase();
            if (!dto.transactionType.equals("GDĐ") && !dto.transactionType.equals("GDN")) {
                throw new IllegalArgumentException("Loại giao dịch phải là GDĐ hoặc GDN");
            }
            dto.landType = getTxtLandType().getText().trim().isEmpty() ? null : getTxtLandType().getText().trim();
            dto.houseType = getTxtHouseType().getText().trim().isEmpty() ? null : getTxtHouseType().getText().trim();
            dto.address = getTxtAddress().getText().trim().isEmpty() ? null : getTxtAddress().getText().trim();
            if (dto.transactionType.equals("GDĐ") && dto.landType == null) {
                throw new IllegalArgumentException("Giao dịch đất (GDĐ) phải có loại đất");
            }
            if (dto.transactionType.equals("GDN") && dto.houseType == null) {
                throw new IllegalArgumentException("Giao dịch nhà (GDN) phải có loại nhà");
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Ngày giao dịch không đúng định dạng (YYYY-MM-DD): " + e.getMessage());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Đơn giá hoặc diện tích không hợp lệ: " + e.getMessage());
        }
        return dto;
    }
}