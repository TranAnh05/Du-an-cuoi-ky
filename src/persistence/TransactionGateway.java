package persistence;

import java.sql.SQLException;
import java.util.List;

public interface TransactionGateway {
    List<TransactionDTO> getAll() throws SQLException;
    void updateTransaction(TransactionDTO dto) throws SQLException;
    List<TransactionDTO> searchByKeyword(String keyword) throws SQLException;
}