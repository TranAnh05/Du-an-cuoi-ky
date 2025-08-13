package business.SaveEditTransaction;

import java.sql.SQLException;
import java.time.LocalDate;

import persistence.SaveEditTransaction.SaveEditTransactionDTO;
import persistence.SaveEditTransaction.SaveEditTransactionGateway;
import business.entity.Transaction;
import business.entity.HouseTransaction;
import business.entity.LandTransaction;
import presentation.Publisher;

public class SaveEditTransactionUseCase extends Publisher {
    private SaveEditTransactionGateway DAOGateway;

    public SaveEditTransactionUseCase(SaveEditTransactionGateway DAOGateway) {
        this.DAOGateway = DAOGateway;
    }

    public boolean execute(String transactionId, String transactionDate, double unitPrice, double area, String transactionType, String landType, String houseType, String address) throws SQLException {
        Transaction transaction = SaveEditTransactionFactory.createTransaction(transactionId, transactionDate, unitPrice, area, transactionType, landType, houseType, address);
        SaveEditTransactionDTO dto = convertToDTO(transaction);
        boolean success = DAOGateway.saveTransaction(dto);
        if (success) {
            notifySubscribers();
        }
        return success;
    }

    private SaveEditTransactionDTO convertToDTO(Transaction transaction) {
        SaveEditTransactionDTO dto = new SaveEditTransactionDTO();
        dto.transactionId = transaction.getTransactionId();
        dto.transactionDate = transaction.getTransactionDate().toString();
        dto.unitPrice = transaction.getUnitPrice();
        dto.area = transaction.getArea();
        dto.transactionType = transaction.getTransactionType();
        if (transaction instanceof LandTransaction) {
            dto.landType = ((LandTransaction) transaction).getLandType();
        } else if (transaction instanceof HouseTransaction) {
            dto.houseType = ((HouseTransaction) transaction).getHouseType();
            dto.address = ((HouseTransaction) transaction).getAddress();
        }
        return dto;
    }
}