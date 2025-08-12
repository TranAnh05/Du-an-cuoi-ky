package business.SaveEditTransaction;

import business.TransactionListView.entity.HouseTransaction;
import business.TransactionListView.entity.LandTransaction;
import business.TransactionListView.entity.Transaction;
import java.time.LocalDate;

public class SaveEditTransactionFactory {
    public static Transaction createTransaction(String transactionId, String transactionDate, double unitPrice, double area, String transactionType, String landType, String houseType, String address) {
        if ("GDĐ".equalsIgnoreCase(transactionType)) {
            return new LandTransaction(transactionId, LocalDate.parse(transactionDate), unitPrice, area, landType);
        } else if ("GDN".equalsIgnoreCase(transactionType)) {
            return new HouseTransaction(transactionId, LocalDate.parse(transactionDate), unitPrice, area, houseType, address);
        }
        return null;
    }
}