package business.SaveEditTransaction;

import business.entity.HouseTransaction;
import business.entity.LandTransaction;
import business.entity.Transaction;
import java.time.LocalDate;

public class SaveEditTransactionFactory {
    public static Transaction createTransaction(String transactionId, String transactionDate, double unitPrice, double area, String transactionType, String landType, String houseType, String address) {
        if ("GDD".equalsIgnoreCase(transactionType)) {
            return new LandTransaction(transactionId, LocalDate.parse(transactionDate), unitPrice, area, landType);
        } else if ("GDN".equalsIgnoreCase(transactionType)) {
            return new HouseTransaction(transactionId, LocalDate.parse(transactionDate), unitPrice, area, houseType, address);
        }
        return null;
    }
}