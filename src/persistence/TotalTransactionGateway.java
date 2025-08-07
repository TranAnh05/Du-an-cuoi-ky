package persistence;

import java.sql.SQLException;
import java.util.List;

public interface TotalTransactionGateway 
{
    List<TransactionDTO> getTransactionsByType(String type) throws SQLException;
}
