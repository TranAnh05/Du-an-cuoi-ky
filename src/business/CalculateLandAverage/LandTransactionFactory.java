package business.CalculateLandAverage;

import business.entity.LandTransaction;
import business.entity.Transaction;
import persistence.CalculateAmountAverage.LandTransactionDTO;

public class LandTransactionFactory {
    public static Transaction createLandTransaction(LandTransactionDTO dto) {
        return new LandTransaction(
            dto.transactionId, dto.transactionDate,
            dto.unitPrice, dto.area, dto.landType
        );
    }
}    
