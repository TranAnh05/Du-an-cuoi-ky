package persistence.SearchTransaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SearchTransactionDAO implements SearchTransactionGateway {
    private Connection conn;

    public SearchTransactionDAO() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String username = "root";
        String password = "130405"; 
        String url = "jdbc:mysql://localhost:3306/transaction?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        conn = DriverManager.getConnection(url, username, password);
    }
@Override
    public List<SearchTransactionDTO> searchTransactions(String searchTerm) throws SQLException {
        List<SearchTransactionDTO> dtos = new ArrayList<>();
        String sql = "SELECT id, date, unitPrice, area, transactionType, landType, houseType, address FROM transaction WHERE id LIKE ? OR address LIKE ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, "%" + searchTerm + "%");
        pstmt.setString(2, "%" + searchTerm + "%");
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            SearchTransactionDTO dto = new SearchTransactionDTO();
            dto.transactionId = rs.getString("id");
            dto.transactionDate = rs.getObject("date", LocalDate.class); // Remove toString()
            dto.unitPrice = rs.getDouble("unitPrice");
            dto.area = rs.getDouble("area");
            dto.transactionType = rs.getString("transactionType");
            dto.landType = rs.getString("landType");
            dto.houseType = rs.getString("houseType");
            dto.address = rs.getString("address");
            dtos.add(dto);
        }
        pstmt.close();
        rs.close();
        return dtos;
    }
}