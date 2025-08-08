package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TotalTransactionDAO implements TotalTransactionGateway 
{
    private Connection conn;

    public TotalTransactionDAO() throws SQLException, ClassNotFoundException 
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String username = "root";
        String password = "123456789";
        String url = "jdbc:mysql://localhost:3306/transaction?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        conn = DriverManager.getConnection(url, username, password);
    }

    @Override
    public List<TransactionDTO> getTransactionsByType(String type) throws SQLException {
        List<TransactionDTO> transactions = new ArrayList<>();
        String sql = type == null ? "SELECT * FROM transaction" : "SELECT * FROM transaction WHERE transactionType = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (type != null) {
                stmt.setString(1, type);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TransactionDTO dto = new TransactionDTO();
                dto.transactionId = rs.getString("id");
                dto.transactionDate = rs.getDate("date").toLocalDate();
                dto.unitPrice = rs.getDouble("unitPrice");
                dto.area = rs.getDouble("area");
                dto.transactionType = rs.getString("transactionType");
                dto.landType = rs.getString("landType");
                dto.houseType = rs.getString("houseType");
                dto.address = rs.getString("address");
                transactions.add(dto);
            }
        }
        return transactions;
    }
}