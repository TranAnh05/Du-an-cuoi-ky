package business.TransactionListView;
import java.util.List;

import business.entity.Transaction;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import presentation.Publisher;
import persistence.TransactionListView.TransactionDTO;
import persistence.TransactionListView.TransactionGateway;

public class TransactionListViewUseCase extends Publisher {
    private TransactionGateway DAOGateway;

    public TransactionListViewUseCase(TransactionGateway DAOGateway) {
        // super();
        this.DAOGateway = DAOGateway;
    }

    public List<TransactionViewDTO> execute() throws SQLException, ParseException {
        List<TransactionDTO> listDTO = null;
        List<Transaction> transactions = null;

        listDTO = DAOGateway.getAll();

        transactions = convertToBusinessObjects(listDTO);

        return convertToTransactionViewDTO(transactions);
    }

    private List<Transaction> convertToBusinessObjects(List<TransactionDTO> listDTO) {
        List<Transaction> transactions = new ArrayList<>();

        for (TransactionDTO dto : listDTO) {
            Transaction transaction = TransactionFactory.createTransaction(dto);
            transactions.add(transaction);
        }
        return transactions;
    }

    private List<TransactionViewDTO> convertToTransactionViewDTO(List<Transaction> transactions) {
        List<TransactionViewDTO> itemList = new ArrayList<TransactionViewDTO>();

        for (Transaction transaction : transactions) {
            TransactionViewDTO item = new TransactionViewDTO();

            item.transactionId = transaction.getTransactionId();
            item.transactionDate = transaction.getTransactionDate();
            item.unitPrice = transaction.getUnitPrice();
            item.area = transaction.getArea();
            item.transactionType = transaction.getTransactionType();
            item.amountTotal = transaction.calculateAmount();
            itemList.add(item);
        }

        return itemList;
    }
}