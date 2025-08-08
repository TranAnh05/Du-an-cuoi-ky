package presentation;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class TransactionMonthSelectUI extends JDialog {
    private JComboBox<Integer> monthCombo;
    private JComboBox<Integer> yearCombo;
    private JButton btnOk;
    private JButton btnCancel;
    private int selectedMonth = -1;
    private int selectedYear = -1;


    private TransactionMonthController controller;
    private TransactionMonthShowUI monthShowUI;


    public TransactionMonthSelectUI() {
        super((Frame) null, "Chọn tháng và năm", true);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Tháng:"));
        monthCombo = new JComboBox<>();
        for (int i = 1; i <= 12; i++) monthCombo.addItem(i);
        add(monthCombo);

        add(new JLabel("Năm:"));
        yearCombo = new JComboBox<>();
        int currentYear = java.time.Year.now().getValue();
        for (int i = currentYear - 10; i <= currentYear + 1; i++) yearCombo.addItem(i);
        yearCombo.setSelectedItem(currentYear);
        add(yearCombo);

        btnOk = new JButton("OK");
        btnCancel = new JButton("Hủy");
        add(btnOk);
        add(btnCancel);

        btnOk.addActionListener(e -> {
            selectedMonth = (int) monthCombo.getSelectedItem();
            selectedYear = (int) yearCombo.getSelectedItem();
            if (selectedMonth <= 0 || selectedYear <= 0) {
                JOptionPane.showMessageDialog(this,
                    "Không tìm thấy giao dịch nào trong tháng " + selectedMonth + "/" + selectedYear,
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
                dispose();
                return;
            }
            try {
                List<TransactionViewItem> result = controller.execute(selectedMonth, selectedYear);
                if (result == null || result.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                        "Không tìm thấy giao dịch nào trong tháng " + selectedMonth + "/" + selectedYear,
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                monthShowUI.setVisible(true);
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            dispose();
        });

        btnCancel.addActionListener(e -> {
            dispose();
        });

        setSize(300, 150);
        setLocationRelativeTo(null);
    }

    public void setController(TransactionMonthController controller){
        this.controller = controller;
    }

    public void setShowMonthUI(TransactionMonthShowUI monthShowUI) {
        this.monthShowUI = monthShowUI;
    }

    public int getSelectedMonth() {
        return selectedMonth;
    }

    public int getSelectedYear() {
        return selectedYear;
    }
}