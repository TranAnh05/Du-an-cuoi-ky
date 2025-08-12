
import java.sql.SQLException;
import java.text.ParseException;

import business.CalculateLandAverage.CalculateLandAverageUsecase;
import business.TransactionListView.TransactionListViewUseCase;
import persistence.CalculateAmountAverage.CalculateLandAverageDAO;
import persistence.TransactionListView.TransactionListViewDAO;
import presentation.CalculateLandAverage.CalculateLandAverageController;
import presentation.CalculateLandAverage.CalculateLandAverageModel;
import presentation.CalculateLandAverage.CalculateLandAverageView;
import presentation.TransactionListView.TransactionListViewController;
import presentation.TransactionListView.TransactionListViewUI;
import presentation.TransactionListView.TransactionViewModel;

public class AppTransaction {
    public static void main(String[] args) 
    {
        TransactionListViewUI mainView = new TransactionListViewUI();

        /* ***** RELATIVE TO LISTVIEW ***** */
        TransactionListViewDAO transactionListViewDAO = null;
        TransactionViewModel model = new TransactionViewModel();
        TransactionListViewController listViewController = null;
        TransactionListViewUseCase listViewUseCase = null;
        mainView.setViewModel(model);

        /* ***** RELATIVE TO AVERAGE ***** */
        CalculateLandAverageDAO averageDAO = null;
        CalculateLandAverageView averageShowUI = new CalculateLandAverageView();
        CalculateLandAverageModel averageModel = new CalculateLandAverageModel();
        averageShowUI.setModel(averageModel);
        CalculateLandAverageUsecase averageUsecase = null;
        CalculateLandAverageController averageController = null;

        try {   
            /* ***** RELATIVE TO LISTVIEW ***** */
            transactionListViewDAO = new TransactionListViewDAO();
            listViewUseCase = new TransactionListViewUseCase(transactionListViewDAO);

            listViewController = new TransactionListViewController(model, listViewUseCase);

            listViewController.execute();
            mainView.setVisible(true);

            /* ***** RELATIVE TO AVERAGE ***** */
            averageDAO = new CalculateLandAverageDAO();
            averageUsecase = new CalculateLandAverageUsecase(averageDAO);
            averageController = new CalculateLandAverageController(averageUsecase, averageModel);
            mainView.setAverageController(averageController);
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } 
    }
}

