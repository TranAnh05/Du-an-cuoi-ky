package persistence.TotalTransaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistence.OpenChoseTransactionForm.TypeDTO;
import persistence.TransactionListView.TransactionDTO;


public class TotalTransactionDAO  implements TotalTransactionGateway
{
    private Connection conn;

    public TotalTransactionDAO() throws ClassNotFoundException, SQLException
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String username = "root";
        String password = "123456789";
        String url = "jdbc:mysql://localhost:3306/transaction?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        conn = DriverManager.getConnection(url, username, password);
    }
    @Override
    public List<TotalTransactioDTO> getTransactionsByType(String type) throws SQLException {
        List<TotalTransactioDTO> listDTO = new ArrayList<>();
        String sql = type == null ? "SELECT * FROM transaction" : "SELECT * FROM transaction WHERE transactionType = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) 
        {
            if (type != null) {
                stmt.setString(1, type);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) 
            {
                TotalTransactioDTO dto = new TotalTransactioDTO();
                dto.transactionId = rs.getString("id");
                dto.transactionDate = rs.getDate("date").toLocalDate();
                dto.unitPrice = rs.getDouble("unitPrice");
                dto.area = rs.getDouble("area");
                dto.transactionType = rs.getString("transactionType");
                dto.landType = rs.getString("landType");
                dto.houseType = rs.getString("houseType");
                dto.address = rs.getString("address");
                listDTO.add(dto);
            }
        }
        return listDTO;
    }
}
