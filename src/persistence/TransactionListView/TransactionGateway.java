package persistence.TransactionListView;

import java.sql.SQLException;
import java.util.List;

public interface TransactionGateway {
    List<TransactionDTO> getAll() throws SQLException;
}