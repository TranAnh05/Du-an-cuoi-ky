package business.SearchTransaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import business.TransactionListView.entity.Transaction;
import business.TransactionListView.entity.HouseTransaction;
import business.TransactionListView.entity.LandTransaction;
import persistence.SearchTransaction.SearchTransactionDTO;
import persistence.SearchTransaction.SearchTransactionGateway;
import presentation.Publisher;

public class SearchTransactionUseCase extends Publisher {
    private SearchTransactionGateway DAOGateway;

    public SearchTransactionUseCase(SearchTransactionGateway DAOGateway) {
        this.DAOGateway = DAOGateway;
    }

    public List<TransactionViewDTO> execute(String searchTerm) throws SQLException {
        List<SearchTransactionDTO> dtos = DAOGateway.searchTransactions(searchTerm);
        System.out.println("Retrieved DTOs count: " + dtos.size()); // Debug
        List<Transaction> transactions = convertToEntities(dtos);
        System.out.println("Converted Transactions count: " + transactions.size()); // Debug
        return convertToViewDTO(transactions);
    }

    private List<Transaction> convertToEntities(List<SearchTransactionDTO> dtos) {
        List<Transaction> transactions = new ArrayList<>();
        for (SearchTransactionDTO dto : dtos) {
            transactions.add(SearchTransactionFactory.createTransaction(dto));
        }
        return transactions;
    }

    private List<TransactionViewDTO> convertToViewDTO(List<Transaction> transactions) {
        List<TransactionViewDTO> viewDTOS = new ArrayList<>();
        for (Transaction transaction : transactions) {
            TransactionViewDTO dto = new TransactionViewDTO();
            dto.transactionId = transaction.getTransactionId();
            dto.transactionDate = transaction.getTransactionDate();
            dto.unitPrice = transaction.getUnitPrice();
            dto.area = transaction.getArea();
            dto.transactionType = transaction.getTransactionType();
            if (transaction instanceof LandTransaction) {
                dto.landType = ((LandTransaction) transaction).getLandType();
            } else if (transaction instanceof HouseTransaction) {
                dto.houseType = ((HouseTransaction) transaction).getHouseType();
                dto.address = ((HouseTransaction) transaction).getAddress();
            }
            viewDTOS.add(dto);
        }
        return viewDTOS;
    }
}