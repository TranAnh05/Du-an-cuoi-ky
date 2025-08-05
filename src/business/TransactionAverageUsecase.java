package business;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import business.entity.Transaction;
import persistence.TransactionGateway;
import persistence.TransactionDTO;

public class TransactionAverageUsecase {
    private TransactionGateway transactionGateway;

    public TransactionAverageUsecase(TransactionGateway transactionGateway) {
        this.transactionGateway = transactionGateway;
    }

    public double execute() throws SQLException {
        List<TransactionDTO> listDTO = null;
        List<Transaction> listTransactionsBusiness = null;

        listDTO =  transactionGateway.getAll();
        listTransactionsBusiness = convertToBusinessData(listDTO);

        double result = calculateAverage(listTransactionsBusiness);
        return result;
    }

    private List<Transaction> convertToBusinessData(List<TransactionDTO> dtoList) {
        List<Transaction> listTransactionBS = new ArrayList<Transaction>();

        for(TransactionDTO dto : dtoList) {
            Transaction transaction = TransactionFactory.createTransaction(dto);
            listTransactionBS.add(transaction);
        }

        return listTransactionBS;
    }

    private double calculateAverage(List<Transaction> listTransactions) {
        int count = 0;
        double totalAmount = 0;

        for (Transaction transaction : listTransactions) {
            count++;
            totalAmount += transaction.calculateAmount();
        }

        double result = totalAmount / count;
        return result;
    }
}
