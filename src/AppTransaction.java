
import java.sql.SQLException;
import java.text.ParseException;

import business.TotalTransactionUseCase;
import business.TransactionAverageUsecase;
import business.TransactionFactory;
import business.TransactionListViewUseCase;
import business.TransactionMonthUseCase;
import business.TransactionSearchUseCase;
import business.TransactionUpdateUseCase;
import persistence.TotalTransactionDAO;
import persistence.TransactionListViewDAO;
import presentation.TotalTransactionViewController;
import presentation.TotalTransactionViewModel;
import presentation.TotalTransactionViewUI;
import presentation.TransactionAverageController;
import presentation.TransactionAverageModel;
import presentation.TransactionAverageShowUI;
import presentation.TransactionListViewController;
import presentation.TransactionListViewUI;
import presentation.TransactionMonthController;
import presentation.TransactionMonthSelectUI;
import presentation.TransactionMonthShowUI;
import presentation.TransactionViewModel;

public class AppTransaction {
    public static void main(String[] args) 
    {
        TransactionListViewUI view = new TransactionListViewUI();
        TransactionMonthSelectUI selectUI = new TransactionMonthSelectUI();


        TransactionViewModel model = new TransactionViewModel();
        TransactionListViewController controller = null;
        TransactionListViewUseCase listViewUseCase = null;
        TransactionSearchUseCase searchUseCase = null;
        TransactionUpdateUseCase updateUseCase = null;
        view.setViewModel(model);
        // relative to month
        view.setSelectUI(selectUI);

        // relative to average
        TransactionAverageShowUI averageShowUI = new TransactionAverageShowUI();
        TransactionAverageModel averageModel = new TransactionAverageModel();
        averageShowUI.setModel(averageModel);
        TransactionAverageUsecase averageUsecase = null;
        TransactionAverageController averageController = null;

        // relative to get month
        TransactionMonthUseCase monthUsecase = null;
        TransactionMonthController monthController = null;

        //Total
        TotalTransactionUseCase totalUseCase = null;

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

            // relative to average
            averageUsecase = new TransactionAverageUsecase(transactionListViewDAO);
            averageController = new TransactionAverageController(averageUsecase, averageModel);
            view.setAverageController(averageController);

            // relative to get month
            monthUsecase = new TransactionMonthUseCase(transactionListViewDAO);
            TransactionViewModel monthModel = new TransactionViewModel();
            TransactionMonthShowUI showUI = new TransactionMonthShowUI();
            showUI.setViewModel(monthModel);
            monthController = new TransactionMonthController(monthUsecase, monthModel);
            selectUI.setController(monthController);

            //Total
            totalUseCase = new TotalTransactionUseCase(new TotalTransactionDAO());
            TotalTransactionViewModel totalModel = new TotalTransactionViewModel();
            TotalTransactionViewUI totalView = new TotalTransactionViewUI();

            // truyền view, model, useCase
            TotalTransactionViewController totalController = new TotalTransactionViewController(totalView, totalModel, totalUseCase);

            totalView.setViewModel(totalModel); // Đăng ký Subscriber
            view.setTotalTransactionView(totalView);
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } 
    }
}

