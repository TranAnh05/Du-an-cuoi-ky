package persistence.OpenChoseTransactionForm;

import java.sql.SQLException;
import java.util.List;

public interface OpenChoseTransactionGateway 
{
    List<TypeDTO> getTransactionType() throws SQLException;
}
