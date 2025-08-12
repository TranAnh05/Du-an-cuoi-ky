package business.SearchTransaction;

import business.TransactionListView.entity.HouseTransaction;
import business.TransactionListView.entity.LandTransaction;
import business.TransactionListView.entity.Transaction;
import persistence.SearchTransaction.SearchTransactionDTO;

public class SearchTransactionFactory {
    public static Transaction createTransaction(SearchTransactionDTO dto) {
        if ("GDĐ".equalsIgnoreCase(dto.transactionType)) {
            return new LandTransaction(dto.transactionId, dto.transactionDate, dto.unitPrice, dto.area, dto.landType);
        } else if ("GDN".equalsIgnoreCase(dto.transactionType)) {
            return new HouseTransaction(dto.transactionId, dto.transactionDate, dto.unitPrice, dto.area, dto.houseType, dto.address);
        }
        return null;
    }
}