import java.sql.SQLException;
import java.text.ParseException;

import business.CalculateLandAverage.CalculateLandAverageUsecase;
import business.OpenEditTransactionForm.OpenEditTransactionFormUseCase;
import business.SaveEditTransaction.SaveEditTransactionUseCase;
import business.SearchTransaction.SearchTransactionUseCase;
import business.TransactionListView.TransactionListViewUseCase;
import business.DeleteTransaction.DeleteTransactionUseCase; 
import persistence.CalculateAmountAverage.CalculateLandAverageDAO;
import persistence.OpenEditTransactionForm.OpenEditTransactionFormDAO;
import persistence.SaveEditTransaction.SaveEditTransactionDAO;
import persistence.SearchTransaction.SearchTransactionDAO;
import persistence.TransactionListView.TransactionListViewDAO;
import persistence.DeleteTransaction.DeleteTransactionDAO; 
import presentation.CalculateLandAverage.CalculateLandAverageController;
import presentation.CalculateLandAverage.CalculateLandAverageModel;
import presentation.CalculateLandAverage.CalculateLandAverageView;
import presentation.OpenEditTransactionForm.OpenEditTransactionFormController;
import presentation.OpenEditTransactionForm.OpenEditTransactionFormModel;
import presentation.OpenEditTransactionForm.OpenEditTransactionFormUI;
import presentation.TransactionListView.TransactionListViewController;
import presentation.TransactionListView.TransactionListViewUI;
import presentation.TransactionListView.TransactionViewModel;
import presentation.SaveEditTransaction.SaveEditTransactionController;
import presentation.SaveEditTransaction.SaveEditTransactionModel;
import presentation.SearchTransaction.SearchTransactionController;
import presentation.SearchTransaction.SearchTransactionModel;
import presentation.DeleteTransaction.DeleteTransactionController; 
import presentation.DeleteTransaction.DeleteTransactionModel; 



public class AppTransaction {
    public static void main(String[] args) {
        TransactionListViewUI mainView = new TransactionListViewUI();
        /* ***** RELATIVE TO LISTVIEW ***** */
        TransactionListViewDAO transactionListViewDAO = null;
        TransactionViewModel viewModel = new TransactionViewModel();
        TransactionListViewController listViewController = null;
        TransactionListViewUseCase listViewUseCase = null;
        mainView.setViewModel(viewModel); // Keep for initial list display

        /* ***** RELATIVE TO OPEN EDIT FORM ***** */
        OpenEditTransactionFormDAO editFormDAO = null;
        OpenEditTransactionFormModel editFormModel = new OpenEditTransactionFormModel();
        OpenEditTransactionFormUI editFormUI = new OpenEditTransactionFormUI();
        OpenEditTransactionFormController editFormController = null;
        OpenEditTransactionFormUseCase editFormUseCase = null;
        editFormUI.setModel(editFormModel);

        
        /* ***** RELATIVE TO SAVE EDIT ***** */
        SaveEditTransactionDAO saveEditDAO = null;
        SaveEditTransactionUseCase saveEditUseCase = null;
        SaveEditTransactionModel saveModel = new SaveEditTransactionModel();
        SaveEditTransactionController saveEditController = null;

        /* ***** RELATIVE TO SEARCH ***** */
        SearchTransactionDAO searchDAO = null;
        SearchTransactionUseCase searchUseCase = null;
        SearchTransactionModel searchModel = new SearchTransactionModel();
        SearchTransactionController searchController = null;

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
            listViewController = new TransactionListViewController(viewModel, listViewUseCase);

            listViewController.execute();
            mainView.setVisible(true);

            /* ***** RELATIVE TO OPEN EDIT FORM ***** */
            editFormDAO = new OpenEditTransactionFormDAO();
            editFormUseCase = new OpenEditTransactionFormUseCase(editFormDAO);
            editFormController = new OpenEditTransactionFormController(editFormModel, editFormUseCase);
            mainView.setEditFormController(editFormController);
            mainView.setEditFormUI(editFormUI);

            /* ***** RELATIVE TO SAVE EDIT ***** */
            saveEditDAO = new SaveEditTransactionDAO();
            saveEditUseCase = new SaveEditTransactionUseCase(saveEditDAO);
            saveEditController = new SaveEditTransactionController(saveModel, saveEditUseCase, listViewController);
            editFormUI.setSaveController(saveEditController);

            /* ***** RELATIVE TO SEARCH ***** */
            searchDAO = new SearchTransactionDAO();
            searchUseCase = new SearchTransactionUseCase(searchDAO);
            searchController = new SearchTransactionController(searchModel, searchUseCase);
            mainView.setSearchController(searchController);
            mainView.setSearchModel(searchModel);

            /* ***** RELATIVE TO AVERAGE ***** */
            averageDAO = new CalculateLandAverageDAO();
            averageUsecase = new CalculateLandAverageUsecase(averageDAO);
            averageController = new CalculateLandAverageController(averageUsecase, averageModel);
            mainView.setAverageController(averageController);

        
            /* ***** RELATIVE TO DELETE ***** */
            DeleteTransactionDAO deleteDAO = new DeleteTransactionDAO();
            DeleteTransactionUseCase deleteUseCase = new DeleteTransactionUseCase(deleteDAO);
            DeleteTransactionModel deleteModel = new DeleteTransactionModel();
            // DeleteTransactionController deleteController = new DeleteTransactionController(deleteModel, deleteUseCase);
            DeleteTransactionController deleteController = new DeleteTransactionController(deleteModel, deleteUseCase,listViewController);

            mainView.setDeleteController(deleteController);
            mainView.setDeleteModel(deleteModel);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
            
        }
    }
}
