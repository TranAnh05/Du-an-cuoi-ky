package business;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import business.entity.Transaction;
import persistence.TotalTransactionGateway;
import persistence.TransactionDTO;

public class TotalTransactionUseCase 
{

    private TotalTransactionGateway totalGateway;

    public TotalTransactionUseCase(TotalTransactionGateway totalGateway)    
    {
        this.totalGateway = totalGateway;
    }

    public int execute(String type)  throws SQLException
    {
        // Bước 1: Lấy danh sách DTO từ DAO
        List<TransactionDTO> transactionDTO = totalGateway.getTransactionsByType(type);

        // Bước 2: Chuyển từ DTO sang Business Entity
        List<Transaction> transactions = convertToBusinessData(transactionDTO);

        // Bước 3: Tính tổng số lượng
        int total = calculateTotal(transactions);
        System.out.println("Tổng số lượng giao dịch" + total);

        // Trả về  
        return total;
    }

    private List<Transaction> convertToBusinessData(List<TransactionDTO> dtoList)   
    {
        List<Transaction> transactions = new ArrayList<>();
        for (TransactionDTO dto : dtoList) {
            Transaction transaction = TransactionFactory.createTransaction(dto);
            transactions.add(transaction);
        }
        return transactions;
    }

    public int calculateTotal (List<Transaction> listTransactions)
    {
        int count = 0;
        for(Transaction transaction: listTransactions)
        {
            count++;
        }
        return count;
    }
}
