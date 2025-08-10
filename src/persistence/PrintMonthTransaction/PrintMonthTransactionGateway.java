package persistence.PrintMonthTransaction;

import java.util.List;

public interface PrintMonthTransactionGateway {
    List<MonthTransactionDTO> getPrintMonthTransactions(int month, int year) throws Exception;
}
