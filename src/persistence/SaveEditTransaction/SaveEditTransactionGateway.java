package persistence.SaveEditTransaction;

import java.sql.SQLException;

public interface SaveEditTransactionGateway {
    boolean saveTransaction(SaveEditTransactionDTO dto) throws SQLException;
}