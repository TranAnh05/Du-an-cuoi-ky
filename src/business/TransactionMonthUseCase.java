package business;

import java.util.List;

import business.entity.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;

import persistence.TransactionDTO;
import persistence.TransactionGateway;

public class TransactionMonthUseCase {
    private TransactionGateway transactionGateway;

    public TransactionMonthUseCase(TransactionGateway transactionGateway) {
        this.transactionGateway = transactionGateway;
    }

    public List<TransactionViewDTO> execute(int month, int year) throws SQLException {
        List<TransactionViewDTO> listTransactionViewDTOs = null;
        List<TransactionDTO> listDTO = transactionGateway.getByMonthYear(month, year);

        List<Transaction> listTransactionBusiness = convertPerDataToBusData(listDTO);
        listTransactionViewDTOs = convertDataBusToViewDTOData(listTransactionBusiness);

        return listTransactionViewDTOs;
    }

    private List<Transaction> convertPerDataToBusData(List<TransactionDTO> listDTO) {
        List<Transaction> listTransactionBus = new ArrayList<Transaction>();
        for (TransactionDTO dto : listDTO) {
            Transaction transaction = TransactionFactory.createTransaction(dto);
            listTransactionBus.add(transaction);
        }
        return listTransactionBus;
    }

    private List<TransactionViewDTO> convertDataBusToViewDTOData(List<Transaction> transactions) {
        List<TransactionViewDTO> itemList = new ArrayList<>();

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
