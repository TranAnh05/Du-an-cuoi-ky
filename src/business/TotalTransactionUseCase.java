package business;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import business.entity.Transaction;
import persistence.TotalTransactionGateway;
import persistence.TransactionDTO;
import presentation.TransactionViewItem;

public class TotalTransactionUseCase 
{

    private TotalTransactionGateway totalGateway;

    public TotalTransactionUseCase(TotalTransactionGateway totalGateway) 
    {
        this.totalGateway = totalGateway;
    }

    public List<TransactionViewItem> execute() throws SQLException 
    {
        List<TransactionDTO> listDTO = totalGateway.getTransactionsByType(null);
        List<Transaction> transactions = convertToBusinessObjects(listDTO);
        return convertToViewItems(transactions);
    }

    public List<TransactionViewItem> getTransactionsByType(String type) throws SQLException 
    {
        List<TransactionDTO> listDTO = totalGateway.getTransactionsByType(type);
        List<Transaction> transactions = convertToBusinessObjects(listDTO);
        return convertToViewItems(transactions);
    }

    private List<Transaction> convertToBusinessObjects(List<TransactionDTO> listDTO) 
    {
        List<Transaction> transactions = new ArrayList<>();
        for (TransactionDTO dto : listDTO) {
            Transaction t = TransactionFactory.createTransaction(dto);
            transactions.add(t);
        }
        return transactions;
    }

    private List<TransactionViewItem> convertToViewItems(List<Transaction> transactions) {
        List<TransactionViewItem> itemList = new ArrayList<>();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DecimalFormat df = new DecimalFormat("#.00");
        int stt = 1;

        for (Transaction t : transactions) {
            TransactionViewItem item = new TransactionViewItem();
            item.stt = stt++;
            item.transactionId = t.getTransactionId();
            item.transactionDate = t.getTransactionDate().format(fmt);
            item.transactionType = t.getTransactionType();
            item.unitPrice = df.format(t.getUnitPrice());
            item.area = df.format(t.getArea());
            item.amountTotal = df.format(t.calculateAmount());
            itemList.add(item);
        }

        return itemList;
    }
    
}
