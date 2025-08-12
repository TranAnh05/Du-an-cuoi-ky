package persistence.TotalTransaction;

import java.sql.SQLException;
import java.util.List;

public interface TotalTransactionGateway 
{
    List<TotalTransactioDTO> getTransactionsByType(String type) throws SQLException;
}
