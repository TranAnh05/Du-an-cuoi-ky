import java.sql.SQLException;
import java.text.ParseException;

import business.TotalTransactionUseCase;
import business.TransactionAverageUsecase;
import business.TransactionListViewUseCase;
import persistence.TotalTransactionDAO;
import persistence.TotalTransactionGateway;
import persistence.TransactionListViewDAO;
import presentation.TotalTransactionViewController;
import presentation.TotalTransactionViewUI;
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
        
        //Total
        TotalTransactionUseCase totalUseCase = null;


        try 
        {
            TransactionListViewDAO transactionListViewDAO = new TransactionListViewDAO();
            listViewUseCase = new TransactionListViewUseCase(transactionListViewDAO);
            controller = new TransactionListViewController(model, listViewUseCase);

            
            // average
            averageUsecase = new TransactionAverageUsecase(transactionListViewDAO);
            TransactionAverageUI averageUI = new TransactionAverageUI(view, averageUsecase);
            averageUI.execute();
            
            //Total
            totalUseCase = new TotalTransactionUseCase(new TotalTransactionDAO());
            TransactionViewModel totalModel = new TransactionViewModel();
            TotalTransactionViewUI totalView = new TotalTransactionViewUI();
            TotalTransactionViewController totalController = new TotalTransactionViewController(totalView, totalModel, totalUseCase);
            view.setTotalTransactionView(totalView, totalController);

            
            controller.execute();
            view.setVisible(true);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();

        } catch (ParseException e) 
        {
            e.printStackTrace();
        }
    }
}