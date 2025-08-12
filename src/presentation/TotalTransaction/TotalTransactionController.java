package presentation.TotalTransaction;

import java.sql.SQLException;

import business.TotalTransaction.TotalTransactionUseCase;

public class TotalTransactionController {
    private TotalTransactionModel model;
    private TotalTransactionUseCase useCase;

    public TotalTransactionController(TotalTransactionModel model, TotalTransactionUseCase useCase) {
        this.model = model;
        this.useCase = useCase;
    }

    public void execute(String transactionType) throws SQLException 
    {
        int totalValue = useCase.execute(transactionType);
        model.total = totalValue;
        model.notifySubscribers();      
    }
}
