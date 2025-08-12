package business.PrintMonthTransaction;

import java.util.ArrayList;
import java.util.List;

import business.entity.Transaction;
import persistence.PrintMonthTransaction.MonthTransactionDTO;
import persistence.PrintMonthTransaction.PrintMonthTransactionGateway;

public class PrintMonthTransactionUsecase {
    private PrintMonthTransactionGateway gateway;

    public PrintMonthTransactionUsecase(PrintMonthTransactionGateway gateway) {
        this.gateway = gateway;
    }

    public List<TransactionMonthViewDTO> execute(int month, int year) throws Exception {
        List<MonthTransactionDTO> monthTransactionDTO = null;
        List<Transaction> monthTransactionBusiness = null;

        monthTransactionDTO = gateway.getPrintMonthTransactions(month, year);
        monthTransactionBusiness = convertToBusinessData(monthTransactionDTO);

        return convertToDTOData(monthTransactionBusiness);
    }

    private List<TransactionMonthViewDTO> convertToDTOData(List<Transaction> monthTransactionBusiness) {
        List<TransactionMonthViewDTO> dtos = new ArrayList<>();

        for (Transaction transaction : monthTransactionBusiness) {
            TransactionMonthViewDTO dto = new TransactionMonthViewDTO();

            dto.transactionId = transaction.getTransactionId();
            dto.transactionDate = transaction.getTransactionDate();
            dto.unitPrice = transaction.getUnitPrice();
            dto.area = transaction.getArea();
            dto.transactionType = transaction.getTransactionType();
            dto.amountTotal = transaction.calculateAmount();

            dtos.add(dto);
        }
        
        return dtos;
    }

    private List<Transaction> convertToBusinessData(List<MonthTransactionDTO> listDTO) {
        List<Transaction> transactions = new ArrayList<Transaction>();

        for (MonthTransactionDTO dto : listDTO) {
            Transaction transaction = TransactionMonthFactory.createTransaction(dto);
            transactions.add(transaction);
        }

        return transactions;
    }
}