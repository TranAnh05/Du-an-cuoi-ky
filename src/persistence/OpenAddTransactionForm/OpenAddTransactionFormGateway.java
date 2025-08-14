package persistence.OpenAddTransactionForm;

import java.sql.SQLException;
import java.util.List;

public interface OpenAddTransactionFormGateway {
    List<TransactionTypeDTO> getTransactionTypes() throws SQLException;
}


