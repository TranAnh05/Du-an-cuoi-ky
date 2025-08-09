
// package persistence;

// import java.sql.SQLException;

// public class TransactionUpdateDAO {
//     private TransactionListViewDAO listViewDAO;

//     public TransactionUpdateDAO(TransactionListViewDAO listViewDAO) {
//         this.listViewDAO = listViewDAO;
//     }

//     public void updateTransaction(TransactionDTO dto) throws SQLException {
//         listViewDAO.updateTransaction(dto);
//     }
// }
package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionUpdateDAO implements TransactionGateway {
    private Connection conn;

    public TransactionUpdateDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void updateTransaction(TransactionDTO dto) throws SQLException {
        String sql = "UPDATE transaction SET date = ?, unitPrice = ?, area = ?, transactionType = ?, landType = ?, houseType = ?, address = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, dto.transactionDate);
            stmt.setDouble(2, dto.unitPrice);
            stmt.setDouble(3, dto.area);
            stmt.setString(4, dto.transactionType);
            stmt.setString(5, dto.landType);
            stmt.setString(6, dto.houseType);
            stmt.setString(7, dto.address);
            stmt.setString(8, dto.transactionId);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<TransactionDTO> getAll() throws SQLException {
        // Logic lấy tất cả giao dịch (giả định)
        return new ArrayList<>(); // Cần triển khai đầy đủ
    }

    @Override
    public List<TransactionDTO> searchByKeyword(String keyword) throws SQLException {
        throw new UnsupportedOperationException("Not supported for update DAO");
    }

    @Override
public List<TransactionDTO> getByMonthYear(int month, int year) throws SQLException {
    List<TransactionDTO> dtos = new ArrayList<>();
    String sql = "SELECT * FROM transaction WHERE MONTH(date) = ? AND YEAR(date) = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, month);
        stmt.setInt(2, year);
        try (ResultSet rs = stmt.executeQuery()) {
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
                dtos.add(dto);
            }
        }
    }
    return dtos;
}
}