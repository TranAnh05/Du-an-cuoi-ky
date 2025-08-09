package presentation;

import java.sql.SQLException;

import business.TransactionAverageUsecase;

public class TransactionAverageController {
    private TransactionAverageUsecase usecase;
    private TransactionAverageModel model;

    public TransactionAverageController(TransactionAverageUsecase usecase, TransactionAverageModel model) {
        this.usecase = usecase;
        this.model = model;
    }

    public void execute() throws SQLException {
        model.amountAverage = usecase.execute();
        model.notifySubscribers();
    }
    
}
