package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionSearchDAO implements TransactionGateway {
    private Connection conn;

    public TransactionSearchDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
public List<TransactionDTO> searchByKeyword(String keyword) throws SQLException {
    List<TransactionDTO> list = new ArrayList<>();
    String sql = "SELECT id, date, unitPrice, area, transactionType, landType, houseType, address FROM transaction WHERE id LIKE ? OR address LIKE ?";
    System.out.println("Executing search query with keyword: " + keyword + " on connection: " + conn);
    if (conn == null) {
        System.err.println("Connection is null in searchByKeyword!");
        throw new SQLException("Database connection is null");
    }
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, "%" + keyword + "%");
        stmt.setString(2, "%" + keyword + "%");
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                TransactionDTO dto = new TransactionDTO();
                dto.transactionId = rs.getString("id"); // Ánh xạ id sang transactionId trong DTO
                dto.transactionDate = rs.getObject("date", LocalDate.class);
                dto.unitPrice = rs.getDouble("unitPrice");
                dto.area = rs.getDouble("area");
                dto.transactionType = rs.getString("transactionType");
                dto.landType = rs.getString("landType");
                dto.houseType = rs.getString("houseType");
                dto.address = rs.getString("address");
                list.add(dto);
            }
        }
        System.out.println("Search for '" + keyword + "' returned " + list.size() + " records");
    } catch (SQLException e) {
        System.err.println("SQLException in searchByKeyword: " + e.getMessage());
        throw e;
    }
    return list;
}

    @Override
    public void updateTransaction(TransactionDTO dto) throws SQLException {
        throw new UnsupportedOperationException("Not supported for search DAO");
    }

    @Override
    public List<TransactionDTO> getAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported for search DAO");
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