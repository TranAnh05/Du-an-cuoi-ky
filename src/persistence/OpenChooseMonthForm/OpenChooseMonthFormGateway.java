package persistence.OpenChooseMonthForm;

import java.sql.SQLException;
import java.util.List;

public interface OpenChooseMonthFormGateway {
    List<MonthYearDTO> getMonthYear() throws SQLException;
}
