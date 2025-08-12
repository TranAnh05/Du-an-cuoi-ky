package persistence.CalculateAmountAverage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalculateLandAverageDAO implements CalculateLandAverageGateway{
    private Connection conn;

    public CalculateLandAverageDAO() throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        String username = "root";
        String password = "130405";
        String url = "jdbc:mysql://localhost:3306/transaction?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        conn = DriverManager.getConnection(url, username, password);
    }

    public CalculateLandAverageDAO(Connection conn) {
        this.conn = conn;
    }

    public List<LandTransactionDTO> getLandTransactions() throws SQLException {
        List<LandTransactionDTO> list = new ArrayList<LandTransactionDTO>();
        Statement stmt = null;
        ResultSet rs = null;

        String sql = "SELECT id, date, unitPrice, area, transactionType, landType " +
                    "FROM transaction " +
                    "WHERE transactionType = 'GDĐ'"; 

        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);

        while (rs.next()) {
            LandTransactionDTO dto = new LandTransactionDTO();

            dto.transactionId = rs.getString("id");
            dto.transactionDate = rs.getObject("date", LocalDate.class);
            dto.unitPrice = rs.getDouble("unitPrice");
            dto.area = rs.getDouble("area");
            dto.transactionType = rs.getString("transactionType");
            dto.landType = rs.getString("landType");

            list.add(dto);
        }

        rs.close();
        stmt.close();

        return list;
    }
}
