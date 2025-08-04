package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
import java.time.LocalDate;

public class TransactionListViewDAO implements TransactionGateway{
    private Connection conn;

    public TransactionListViewDAO() throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        String username = "root";
        String password = "130405";
        String url = "jdbc:mysql://localhost:3306/transaction?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        conn = DriverManager.getConnection(url, username, password);
    }

     public List<TransactionDTO> getAll() throws SQLException {
        List<TransactionDTO> list = new ArrayList<TransactionDTO>();
        Statement stmt = null;
		ResultSet rs = null;

        
        String sql = "SELECT id, date, unitPrice, area, transactionType, landType, houseType, address FROM transaction";

        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);

        while (rs.next()) {
            TransactionDTO dto = new TransactionDTO();
            dto.transactionId = rs.getString("id");
            dto.transactionDate = rs.getObject("date", LocalDate.class);
            dto.unitPrice = rs.getDouble("unitPrice");
            dto.area = rs.getDouble("area");
            dto.transactionType = rs.getString("transactionType");

            dto.landType = rs.getString("landType");

            dto.houseType = rs.getString("houseType");
            dto.address = rs.getString("address");

            list.add(dto);
        }
		conn.close();
		stmt.close();
		rs.close();

        return list;
    }
}
