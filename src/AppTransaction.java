import java.sql.SQLException;
import java.text.ParseException;

import business.TransactionListViewUseCase;
import persistence.TransactionListViewDAO;
import presentation.TransactionListViewController;
import presentation.TransactionListViewUI;
import presentation.TransactionViewModel;

public class AppTransaction {
    //hàm chạy chương trình
    public static void main(String[] args) {
        TransactionViewModel model = new TransactionViewModel();
        TransactionListViewController controller = null;
        TransactionListViewUseCase listViewUseCase = null;
        TransactionListViewUI view = new TransactionListViewUI();
        view.setViewModel(model);

        try {
            TransactionListViewDAO transactionListViewDAO = new TransactionListViewDAO();
            listViewUseCase = new TransactionListViewUseCase(transactionListViewDAO);
            controller = new TransactionListViewController(model, listViewUseCase);

            controller.execute();
            view.setVisible(true);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}