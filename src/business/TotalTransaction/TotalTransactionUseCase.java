package business.TotalTransaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import business.TransactionListView.entity.Transaction;
import persistence.TotalTransaction.TotalTransactioDTO;
import persistence.TotalTransaction.TotalTransactionGateway;


public class TotalTransactionUseCase 
{
    private TotalTransactionGateway gateway;

    public TotalTransactionUseCase(TotalTransactionGateway gateway) 
    {
        this.gateway = gateway;
    }

    public int execute (String type) throws SQLException 
    {
        List<TotalTransactioDTO> transactionDTO = gateway.getTransactionsByType(type);
        // Bước 2: Chuyển từ DTO sang Business Entity
        List<Transaction> transactions = convertToBusinessData(transactionDTO);

        // Bước 3: Tính tổng số lượng
        int total = calculateTotal(transactions);
        System.out.println("Tổng số lượng giao dịch" + total);

        return total;
    }
    private List<Transaction> convertToBusinessData(List<TotalTransactioDTO> dtoList)   
    {
        List<Transaction> transactions = new ArrayList<>();
        for (TotalTransactioDTO dto : dtoList) {
            Transaction transaction = TotalTransactionFactory.createTransaction(dto);
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
