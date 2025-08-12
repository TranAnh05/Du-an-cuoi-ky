package persistence.PrintMonthTransaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrintMonthTransactionDAO implements PrintMonthTransactionGateway {
    private Connection conn;

    public PrintMonthTransactionDAO() throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        String username = "root";
        String password = "130405";
        String url = "jdbc:mysql://localhost:3306/transaction?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        conn = DriverManager.getConnection(url, username, password);
    }

    public PrintMonthTransactionDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<MonthTransactionDTO> getPrintMonthTransactions(int month, int year) throws Exception {
        List<MonthTransactionDTO> list = new ArrayList<MonthTransactionDTO>();

        String sql = "SELECT id, date, unitPrice, area, transactionType, landType, houseType, address " +
                 "FROM transaction WHERE MONTH(date) = ? AND YEAR(date) = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, month);
            stmt.setInt(2, year);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    MonthTransactionDTO dto = new MonthTransactionDTO();
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
            }
        }
        return list;
    }
}
