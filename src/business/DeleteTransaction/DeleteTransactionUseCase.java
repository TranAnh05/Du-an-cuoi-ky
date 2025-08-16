package business.DeleteTransaction;

import persistence.DeleteTransaction.DeleteTransactionDTO;
import persistence.DeleteTransaction.DeleteTransactionGateway;

public class DeleteTransactionUseCase {
    private final DeleteTransactionGateway gateway;

    public DeleteTransactionUseCase(DeleteTransactionGateway gateway) {
        this.gateway = gateway;
    }

    public DeleteTransactionDTO execute(String transactionId) throws java.sql.SQLException {
        return gateway.deleteTransaction(transactionId);
    }
}