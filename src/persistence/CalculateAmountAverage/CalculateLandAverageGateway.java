package persistence.CalculateAmountAverage;

import java.sql.SQLException;
import java.util.List;

public interface CalculateLandAverageGateway {
    List<LandTransactionDTO> getLandTransactions() throws SQLException;
}
