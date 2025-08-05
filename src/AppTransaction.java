import business.*;
import persistence.*;
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
        try {
            // DBConnection dbConn = new DBConnection();
            TransactionListViewDAO transactionListViewDAO = new TransactionListViewDAO();
            // TransactionListViewDAO listDao = new TransactionListViewDAO(dbConn.getConnection());
            TransactionFactory factory = new TransactionFactory();
            listViewUseCase = new TransactionListViewUseCase(transactionListViewDAO, factory);
            searchUseCase = new TransactionSearchUseCase(transactionListViewDAO, factory);
            updateUseCase = new TransactionUpdateUseCase(transactionListViewDAO);
            controller = new TransactionListViewController(view, model, listViewUseCase, searchUseCase, updateUseCase);

            controller.execute();
            view.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}