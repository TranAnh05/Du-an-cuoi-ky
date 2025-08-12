package business.CalculateLandAverage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import business.TransactionListView.TransactionFactory;
import business.entity.Transaction;
import persistence.CalculateAmountAverage.CalculateLandAverageGateway;
import persistence.CalculateAmountAverage.LandTransactionDTO;

public class CalculateLandAverageUsecase {
    private CalculateLandAverageGateway averageGateway;

    public CalculateLandAverageUsecase(CalculateLandAverageGateway averageGateway) {
        this.averageGateway = averageGateway;
    }

    public double execute() throws SQLException {
        List<LandTransactionDTO> listDTO = null;
        List<Transaction> listBusinessData = null;
        
        listDTO = averageGateway.getLandTransactions();
        listBusinessData = convertToBusinessObject(listDTO);
        double result = calculateAverage(listBusinessData);

        return result;
    }

    private List<Transaction> convertToBusinessObject(List<LandTransactionDTO> listDTO) {
        List<Transaction> transactions = new ArrayList<Transaction>();

        for (LandTransactionDTO dto : listDTO) {
            Transaction transaction = LandTransactionFactory.createLandTransaction(dto);
            transactions.add(transaction);
        }

        return transactions;
    }

    private double calculateAverage(List<Transaction> listBusinessData) {
        double totalAmount = 0; 
        int count = 0;
        for (Transaction transaction : listBusinessData) {
            totalAmount += transaction.calculateAmount();
            count++;
        }
        return count > 0 ? totalAmount / count : 0;
    }
}


