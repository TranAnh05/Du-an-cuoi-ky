package persistence.SearchTransaction;

import java.sql.SQLException;
import java.util.List;

public interface SearchTransactionGateway {
    List<SearchTransactionDTO> searchTransactions(String searchTerm) throws SQLException;
}