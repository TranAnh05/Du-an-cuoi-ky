package presentation;

import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.swing.JButton;

import business.TransactionAverageUsecase;

public class TransactionAverageUI {
    private JButton button;
    private TransactionListViewUI viewUI;
    private TransactionAverageUsecase usecase;
    private TransactionAverageShowUI averageShowUI;

    public TransactionAverageUI(TransactionListViewUI viewUI, TransactionAverageUsecase usecase) {
        this.viewUI = viewUI;
        this.usecase = usecase;
    }

    public void execute() throws SQLException {
        button = viewUI.getBtnAverage();

        
        
        button.addActionListener(e -> {
            double averageAmount = 0;

            try {
                averageAmount = usecase.execute();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            String averageAmountConverted = convertResultBusinessToPresentor(averageAmount);
            averageShowUI = new TransactionAverageShowUI(viewUI, averageAmountConverted);
            averageShowUI.setVisible(true);
        });
    }

    private String convertResultBusinessToPresentor(double average) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String averageConverted = df.format(average);
        return averageConverted;
    }
}
