package persistence;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface TotalTransactionGateway 
{
    List<TransactionDTO> getTransactionsByType(String type) throws SQLException;
}
