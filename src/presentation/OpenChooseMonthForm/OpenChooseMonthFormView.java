package presentation.OpenChooseMonthForm;

import javax.swing.*;

import business.PrintMonthTransaction.PrintMonthTransactionUsecase;
import persistence.PrintMonthTransaction.PrintMonthTransactionDAO;
import presentation.Subscriber;
import presentation.PrintMonthTransaction.PrintMonthTransactionController;
import presentation.PrintMonthTransaction.PrintMonthTransactionModel;
import presentation.PrintMonthTransaction.PrintMonthTransactionView;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class OpenChooseMonthFormView extends JFrame implements Subscriber{
    private JComboBox<String> cbYear;
    private JComboBox<String> cbMonth;
    private JButton btnSelect, btnCancel;
    private OpenChooseMonthFormModel monthModel;

    public OpenChooseMonthFormView() {
        initUI();
    }

    private void initUI() {
        setTitle("Chọn tháng và năm");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cbYear = new JComboBox<>();
        cbMonth = new JComboBox<>();

        cbYear.addActionListener(e -> loadMonthsForSelectedYear());

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.add(new JLabel("Năm:"));
        panel.add(cbYear);
        panel.add(new JLabel("Tháng:"));
        panel.add(cbMonth);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSelect = new JButton("Chọn");
        btnCancel = new JButton("Hủy");

        btnSelect.addActionListener(e -> {
            String selectedYear = (String) cbYear.getSelectedItem();
            String selectedMonth = (String) cbMonth.getSelectedItem();

            /* ****** RELATIVE TO MONTH  ****** */
            PrintMonthTransactionDAO monthDAO = null;
            PrintMonthTransactionUsecase monthUsecase = null;
            PrintMonthTransactionController monthController = null;
            PrintMonthTransactionModel monthModel = null;
            PrintMonthTransactionView monthView = null;

            try {
                monthModel = new PrintMonthTransactionModel();
                monthDAO = new PrintMonthTransactionDAO();
                monthUsecase = new PrintMonthTransactionUsecase(monthDAO);
                monthController = new PrintMonthTransactionController(monthUsecase, monthModel);
                monthView = new PrintMonthTransactionView();
                monthView.setViewModel(monthModel);

                monthController.execute(selectedMonth, selectedYear);

            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        btnSelect.addActionListener(e ->{
            dispose();
        });

        buttonPanel.add(btnSelect);
        buttonPanel.add(btnCancel);
        
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setModel(OpenChooseMonthFormModel monthModel) {
        this.monthModel = monthModel;
        monthModel.addSubscriber(this);
    }

    private void loadDataToUI() {
        cbYear.removeAllItems();
        cbMonth.removeAllItems();

        if (monthModel == null || monthModel.monthViewItems == null) return;

        // Lấy danh sách năm không trùng
        monthModel.monthViewItems.stream()
            .map(item -> item.year)
            .distinct()
            .forEach(cbYear::addItem);

        if (cbYear.getItemCount() > 0) {
            cbYear.setSelectedIndex(0);
            loadMonthsForSelectedYear();
        }

        setVisible(true);
    }

    private void loadMonthsForSelectedYear() {
        cbMonth.removeAllItems();
        if (monthModel == null) return;

        String selectedYear = (String) cbYear.getSelectedItem();
        List<String> months = monthModel.monthViewItems.stream()
            .filter(item -> item.year.equals(selectedYear))
            .map(item -> item.month)
            .toList();

        for (String month : months) {
            cbMonth.addItem(month);
        }
    }

    @Override
    public void update() {
        this.loadDataToUI();
    }
}
