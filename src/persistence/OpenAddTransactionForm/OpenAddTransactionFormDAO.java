package persistence.OpenAddTransactionForm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OpenAddTransactionFormDAO implements OpenAddTransactionFormGateway{
    private Connection conn;

    public OpenAddTransactionFormDAO() throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        String username = "root";
        String password = "12345678";
        String url = "jdbc:mysql://localhost:3306/transaction?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        conn = DriverManager.getConnection(url, username, password);
    }

    public OpenAddTransactionFormDAO(Connection conn) {
        this.conn = conn;
    }

    public List<TransactionTypeDTO> getTransactionTypes() throws SQLException {
        List<TransactionTypeDTO> transactionTypes = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;

        String sql = "SELECT DISTINCT transactionType AS transactionTypeCode, " +
             "transactionType AS transactionTypeName, '' AS description " +
             "FROM transaction";
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);

        while (rs.next()) {
            TransactionTypeDTO dto = new TransactionTypeDTO();

            dto.transactionTypeCode = rs.getString("transactionTypeCode");
            dto.transactionTypeName = rs.getString("transactionTypeName");
            dto.description = rs.getString("description");

            transactionTypes.add(dto);
        }

        return transactionTypes;
    }
}    
