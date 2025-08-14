package business.SaveTransaction;

import business.entity.HouseTransaction;
import business.entity.LandTransaction;
import business.entity.Transaction;

public class FactorySavedTransaction {
    public static Transaction createTransaction(SavedDataDTO dto) {
        if("GDD".equalsIgnoreCase(dto.transactionType)) {
                 return new LandTransaction(
                    dto.transactionId, dto.transactionDate,
                    dto.unitPrice = dto.unitPrice,
                    dto.area = dto.area,
                    dto.landType
                );
            }
            else if("GDN".equalsIgnoreCase(dto.transactionType)) {
                 return new HouseTransaction(
                    dto.transactionId, dto.transactionDate,
                    dto.unitPrice = dto.unitPrice,
                    dto.area = dto.area,
                    dto.houseType,
                    dto.address
                );
            }

        return null;
    }

    
}
