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

    public List<TransactionViewDTO> getTransactionsByType(String type) throws SQLException 
    {
        List<TransactionDTO> listDTO = totalGateway.getTransactionsByType(type);
        System.out.println("Số giao dịch trả về: " + listDTO.size());
        List<Transaction> transactions = convertToBusinessObjects(listDTO);
        return convertToViewDTO(transactions);
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

    private List<TransactionViewDTO> convertToViewDTO(List<Transaction> transactions) 
    {
        List<TransactionViewDTO> itemList = new ArrayList<TransactionViewDTO>();
        for (Transaction t : transactions) {
            TransactionViewDTO dto = new TransactionViewDTO();
            dto.transactionId = t.getTransactionId();
            dto.transactionDate = t.getTransactionDate();
            dto.transactionType = t.getTransactionType();
            dto.unitPrice = t.getUnitPrice();
            dto.area = t.getArea();
            dto.amountTotal = t.calculateAmount();
            itemList.add(dto);
        }

        return itemList;
    }
    
}
