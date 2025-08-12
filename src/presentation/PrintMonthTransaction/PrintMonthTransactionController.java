package presentation.PrintMonthTransaction;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import business.PrintMonthTransaction.PrintMonthTransactionUsecase;
import business.PrintMonthTransaction.TransactionMonthViewDTO;

public class PrintMonthTransactionController {
    private PrintMonthTransactionUsecase usecase;
    private PrintMonthTransactionModel model;

    public PrintMonthTransactionController(PrintMonthTransactionUsecase usecase, PrintMonthTransactionModel model) {
        this.usecase = usecase;
        this.model = model;
    }   

    public void execute(String selectedMonth, String selectedYear) throws Exception {
        int year = Integer.parseInt(selectedYear);
        int month = Integer.parseInt(selectedMonth);
        List<TransactionMonthViewDTO> listViewDTO = usecase.execute(month, year);
        List<TransactionMonthViewItem> listViewItem = convertToViewItem(listViewDTO);

        model.transactionList = listViewItem;
        model.notifySubscribers();
    }

    private List<TransactionMonthViewItem> convertToViewItem(List<TransactionMonthViewDTO> listViewDTO) {
       List<TransactionMonthViewItem> listItem = new ArrayList<TransactionMonthViewItem>();

        int stt = 1;
        for (TransactionMonthViewDTO dto : listViewDTO) {
            TransactionMonthViewItem item = new TransactionMonthViewItem();
            NumberFormat amountFormatter = new DecimalFormat("#,##0.###");

            item.stt = stt++;
            item.transactionId = dto.transactionId;
            item.transactionDate = dto.transactionDate.toString();
            item.unitPrice = String.valueOf(dto.unitPrice);
            item.area = String.valueOf(dto.area);
            item.transactionType = dto.transactionType;
            item.amountTotal = amountFormatter.format(dto.amountTotal);
            listItem.add(item);
        }

        return listItem;
    }

}
