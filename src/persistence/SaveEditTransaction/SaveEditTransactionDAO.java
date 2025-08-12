package persistence.SaveEditTransaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class SaveEditTransactionDAO implements SaveEditTransactionGateway {
    private Connection conn;

    public SaveEditTransactionDAO() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String username = "root";
        String password = "130405";
        String url = "jdbc:mysql://localhost:3306/transaction?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        conn = DriverManager.getConnection(url, username, password);
    }

    public boolean saveTransaction(SaveEditTransactionDTO dto) throws SQLException {
        String sql = "UPDATE transaction SET date = ?, unitPrice = ?, area = ?, transactionType = ?, landType = ?, houseType = ?, address = ? WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setObject(1, LocalDate.parse(dto.transactionDate));
        pstmt.setDouble(2, dto.unitPrice);
        pstmt.setDouble(3, dto.area);
        pstmt.setString(4, dto.transactionType);
        if ("GDĐ".equalsIgnoreCase(dto.transactionType)) {
            pstmt.setString(5, dto.landType);
            pstmt.setNull(6, java.sql.Types.VARCHAR);
            pstmt.setNull(7, java.sql.Types.VARCHAR);
        } else if ("GDN".equalsIgnoreCase(dto.transactionType)) {
            pstmt.setNull(5, java.sql.Types.VARCHAR);
            pstmt.setString(6, dto.houseType);
            pstmt.setString(7, dto.address);
        }
        pstmt.setString(8, dto.transactionId);
        int rowsAffected = pstmt.executeUpdate();
        pstmt.close();
        return rowsAffected > 0;
    }
}