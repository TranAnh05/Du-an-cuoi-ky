
import java.sql.SQLException;
import java.text.ParseException;

import business.TransactionAverageUsecase;
import business.TransactionFactory;
import business.TransactionListViewUseCase;
import business.TransactionSearchUseCase;
import business.TransactionUpdateUseCase;
import persistence.TransactionListViewDAO;
import presentation.TransactionAverageUI;
import presentation.TransactionListViewController;
import presentation.TransactionListViewUI;
import presentation.TransactionViewModel;

public class AppTransaction {
    public static void main(String[] args) {
        TransactionListViewUI view = new TransactionListViewUI();

        TransactionViewModel model = new TransactionViewModel();
        TransactionListViewController controller = null;
        TransactionListViewUseCase listViewUseCase = null;
        TransactionSearchUseCase searchUseCase = null;
        TransactionUpdateUseCase updateUseCase = null;
        view.setViewModel(model);
        // average
        TransactionAverageUsecase averageUsecase = null;
        try {
            // DBConnection dbConn = new DBConnection();
            TransactionListViewDAO transactionListViewDAO = new TransactionListViewDAO();
            // TransactionListViewDAO listDao = new TransactionListViewDAO(dbConn.getConnection());
            TransactionFactory factory = new TransactionFactory();
            // listViewUseCase = new TransactionListViewUseCase(transactionListViewDAO);
            listViewUseCase = new TransactionListViewUseCase(transactionListViewDAO, factory);
            searchUseCase = new TransactionSearchUseCase(transactionListViewDAO, factory);
            updateUseCase = new TransactionUpdateUseCase(transactionListViewDAO);
            controller = new TransactionListViewController(view, model, listViewUseCase, searchUseCase, updateUseCase);

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