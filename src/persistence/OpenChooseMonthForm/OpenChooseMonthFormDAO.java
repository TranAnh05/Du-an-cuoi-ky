package persistence.OpenChooseMonthForm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OpenChooseMonthFormDAO implements OpenChooseMonthFormGateway {
    private Connection conn;

    public OpenChooseMonthFormDAO() throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        String username = "root";
        String password = "12345678";
        String url = "jdbc:mysql://localhost:3306/transaction?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        conn = DriverManager.getConnection(url, username, password);
    }

    public OpenChooseMonthFormDAO(Connection conn) {
        this.conn = conn;
    }

    public List<MonthYearDTO> getMonthYear() throws SQLException {
        List<MonthYearDTO> listDTO = new ArrayList<>();

        PreparedStatement stmt;
        ResultSet rs;

        String sql = "SELECT DISTINCT MONTH(date) AS month, YEAR(date) AS year " +
                     "FROM transaction ORDER BY year DESC, month DESC";

        stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();

        while (rs.next()) {
            MonthYearDTO dto = new MonthYearDTO();

            dto.month = rs.getInt("month");
            dto.year = rs.getInt("year");
            listDTO.add(dto);
        }

        return listDTO;
    }
}
