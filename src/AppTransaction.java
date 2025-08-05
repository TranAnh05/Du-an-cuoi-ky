import java.sql.SQLException;
import java.text.ParseException;

import business.TransactionAverageUsecase;
import business.TransactionListViewUseCase;
import persistence.TransactionListViewDAO;
import presentation.TransactionAverageUI;
import presentation.TransactionListViewController;
import presentation.TransactionListViewUI;
import presentation.TransactionViewModel;

public class AppTransaction {
    public static void main(String[] args) {
        // list view
        TransactionViewModel model = new TransactionViewModel();
        TransactionListViewController controller = null;
        TransactionListViewUseCase listViewUseCase = null;
        TransactionListViewUI view = new TransactionListViewUI();
        view.setViewModel(model);

        // average
        TransactionAverageUsecase averageUsecase = null;

        try {
            TransactionListViewDAO transactionListViewDAO = new TransactionListViewDAO();
            listViewUseCase = new TransactionListViewUseCase(transactionListViewDAO);
            controller = new TransactionListViewController(model, listViewUseCase);

            controller.execute();
            view.setVisible(true);

            // average
            averageUsecase = new TransactionAverageUsecase(transactionListViewDAO);
            TransactionAverageUI averageUI = new TransactionAverageUI(view, averageUsecase);
            averageUI.execute();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}