package business.OpenEditTransactionForm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import business.entity.Transaction;
import business.entity.HouseTransaction;
import business.entity.LandTransaction;
import persistence.OpenEditTransactionForm.OpenEditTransactionFormGateway;
import persistence.OpenEditTransactionForm.TransactionDTO;
import presentation.Publisher;

public class OpenEditTransactionFormUseCase extends Publisher {
    private OpenEditTransactionFormGateway DAOGateway;

    public OpenEditTransactionFormUseCase(OpenEditTransactionFormGateway DAOGateway) {
        this.DAOGateway = DAOGateway;
    }

    public List<TransactionViewDTO> execute(String transactionId) throws SQLException {
        List<TransactionDTO> dtos = DAOGateway.getTransactionById(transactionId);
        List<Transaction> transactions = convertToEntities(dtos);
        return convertToViewDTO(transactions);
    }

    private List<Transaction> convertToEntities(List<TransactionDTO> dtos) {
        List<Transaction> transactions = new ArrayList<>();
        for (TransactionDTO dto : dtos) {
            transactions.add(OpenEditTransactionFormFactory.createTransaction(dto));
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