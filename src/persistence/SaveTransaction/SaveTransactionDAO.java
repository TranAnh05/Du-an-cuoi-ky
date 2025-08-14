package persistence.SaveTransaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SaveTransactionDAO implements SaveTransactionGateway{
    private Connection conn;

    public SaveTransactionDAO() throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        String username = "root";
        String password = "130405";
        // String password = "12345678";
        String url = "jdbc:mysql://localhost:3306/transaction?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        conn = DriverManager.getConnection(url, username, password);
    }

    public SaveTransactionDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean insertTransaction(SavedTransactionDTO transaction) {
        // Kiểm tra trùng ID
        String checkSql = "SELECT COUNT(*) FROM transaction WHERE id = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setString(1, transaction.transactionId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false; // ID đã tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        // Thực hiện insert
        String sql = "INSERT INTO transaction (id, date, unitPrice, area, transactionType, landType, houseType, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, transaction.transactionId);
            pstmt.setObject(2, transaction.transactionDate);
            pstmt.setDouble(3, transaction.unitPrice);
            pstmt.setDouble(4, transaction.area);
            pstmt.setString(5, transaction.transactionType);
            pstmt.setString(6, transaction.landType);
            pstmt.setString(7, transaction.houseType);
            pstmt.setString(8, transaction.address);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
