package persistence;

import java.sql.SQLException;

public class TransactionUpdateDAO {
    private TransactionListViewDAO listViewDAO;

    public TransactionUpdateDAO(TransactionListViewDAO listViewDAO) {
        this.listViewDAO = listViewDAO;
    }

    public void updateTransaction(TransactionDTO dto) throws SQLException {
        listViewDAO.updateTransaction(dto);
    }
}