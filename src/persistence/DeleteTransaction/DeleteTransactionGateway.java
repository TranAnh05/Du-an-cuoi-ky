package persistence.DeleteTransaction;

import java.sql.SQLException;

public interface DeleteTransactionGateway {
    DeleteTransactionDTO deleteTransaction(String transactionId) throws SQLException;
}