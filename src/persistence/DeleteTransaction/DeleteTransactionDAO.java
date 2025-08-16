package persistence.DeleteTransaction;

import java.sql.*;

public class DeleteTransactionDAO implements DeleteTransactionGateway {
    private final Connection conn;

    public DeleteTransactionDAO() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String username = "root";
        String password = "12345678";
        String url = "jdbc:mysql://localhost:3306/transaction?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        conn = DriverManager.getConnection(url, username, password);
    }

    @Override
    public DeleteTransactionDTO deleteTransaction(String transactionId) throws SQLException {
        DeleteTransactionDTO deleted = null;

        final String selectSQL = "SELECT id, date, unitPrice, area, transactionType, landType, houseType, address FROM transaction WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            pstmt.setString(1, transactionId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    java.sql.Date sqlDate = rs.getDate("date");
                    deleted = new DeleteTransactionDTO(
                        rs.getString("id"),
                        rs.getString("transactionType"),
                        sqlDate != null ? new java.util.Date(sqlDate.getTime()) : null,
                        rs.getDouble("unitPrice"),
                        rs.getDouble("area"),
                        rs.getString("landType"),
                        rs.getString("houseType"),
                        rs.getString("address")
                    );
                }
            }
        }

        // If not found, nothing to delete
        if (deleted == null) {
            return null;
        }

        final String deleteSQL = "DELETE FROM transaction WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            pstmt.setString(1, transactionId);
            pstmt.executeUpdate();
        }

        return deleted;
    }
}