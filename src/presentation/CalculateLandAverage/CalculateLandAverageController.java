package presentation.CalculateLandAverage;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import business.CalculateLandAverage.CalculateLandAverageUsecase;

public class CalculateLandAverageController {
    private CalculateLandAverageUsecase usecase;
    private CalculateLandAverageModel model;

    public CalculateLandAverageController(CalculateLandAverageUsecase usecase, CalculateLandAverageModel model) {
        this.usecase = usecase;
        this.model = model;
    }

    public void execute() throws SQLException {
        NumberFormat amountFormatter = new DecimalFormat("#,##0.###");

        model.amountAverage = amountFormatter.format(usecase.execute());
        model.notifySubscribers();
    }
}
