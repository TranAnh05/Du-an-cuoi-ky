package persistence.OpenChoseTransactionForm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OpenChoseTransactionDAO implements OpenChoseTransactionGateway
{
    private Connection conn;
    public OpenChoseTransactionDAO() throws ClassNotFoundException, SQLException
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String username = "root";
        String password = "123456789";
        String url = "jdbc:mysql://localhost:3306/transaction?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        conn = DriverManager.getConnection(url, username, password);
    }
    @Override
    public List<TypeDTO> getTransactionType() throws SQLException 
    {
        List<TypeDTO> listDTO = new ArrayList<>();

        String sql = "SELECT DISTINCT transactionType FROM transaction";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                TypeDTO dto = new TypeDTO();
                dto.transactionType = rs.getString("transactionType");
                listDTO.add(dto);
            }
        }

        return listDTO;
    }
}
