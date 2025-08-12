package business.OpenEditTransactionForm;

import business.TransactionListView.entity.HouseTransaction;
import business.TransactionListView.entity.LandTransaction;
import business.TransactionListView.entity.Transaction;
import persistence.OpenEditTransactionForm.TransactionDTO;

public class OpenEditTransactionFormFactory {
    public static Transaction createTransaction(TransactionDTO dto) {
        if ("GDĐ".equalsIgnoreCase(dto.transactionType)) {
            return new LandTransaction(dto.transactionId, dto.transactionDate, dto.unitPrice, dto.area, dto.landType);
        } else if ("GDN".equalsIgnoreCase(dto.transactionType)) {
            return new HouseTransaction(dto.transactionId, dto.transactionDate, dto.unitPrice, dto.area, dto.houseType, dto.address);
        }
        return null;
    }
}