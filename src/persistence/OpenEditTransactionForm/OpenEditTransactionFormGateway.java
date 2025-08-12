package persistence.OpenEditTransactionForm;

import java.sql.SQLException;
import java.util.List;

public interface OpenEditTransactionFormGateway {
    List<TransactionDTO> getTransactionById(String transactionId) throws SQLException;
}