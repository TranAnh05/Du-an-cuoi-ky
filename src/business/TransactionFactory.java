package business;

import business.entity.HouseTransaction;
import business.entity.LandTransaction;
import business.entity.Transaction;
import persistence.TransactionDTO;

public class TransactionFactory {
    
    public static Transaction createTransaction(TransactionDTO dto) {
        if("GDĐ".equalsIgnoreCase(dto.transactionType)) {
                 return new LandTransaction(
                    dto.transactionId, dto.transactionDate,
                    dto.unitPrice != null ? dto.unitPrice : 0,
                    dto.area != null ? dto.area : 0,
                    dto.landType
                );
            }
            else if("GDN".equalsIgnoreCase(dto.transactionType)) {
                 return new HouseTransaction(
                    dto.transactionId, dto.transactionDate,
                    dto.unitPrice != null ? dto.unitPrice : 0,
                    dto.area != null ? dto.area : 0,
                    dto.houseType,
                    dto.address
                );
            }

        return null;
    }

}
