package business.SaveTransaction;

import business.entity.HouseTransaction;
import business.entity.LandTransaction;
import business.entity.Transaction;
import persistence.SaveTransaction.SavedTransactionDTO;

public class FactorySavedDTO {
    public static SavedTransactionDTO createTransactionDTO(Transaction transaction)  {
        SavedTransactionDTO dto  = new SavedTransactionDTO();
        dto.transactionId = transaction.getTransactionId();
        dto.transactionDate = transaction.getTransactionDate();
        dto.unitPrice = transaction.getUnitPrice();
        dto.area = transaction.getArea();
        dto.transactionType = transaction.getTransactionType();
        
        if(transaction instanceof LandTransaction){
            dto.landType = ((LandTransaction)transaction).getLandType();
            dto.houseType = "";
            dto.address = "";
        }
        else{
            dto.landType = "";
            dto.houseType = ((HouseTransaction)transaction).getHouseType();
            dto.address = ((HouseTransaction)transaction).getAddress();
        }

        return dto;
    }
}
