package presentation;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import business.TransactionMonthUseCase;
import business.TransactionViewDTO;

public class TransactionMonthController {
    private TransactionMonthUseCase usecase;
    private TransactionViewModel model;

    public TransactionMonthController(TransactionMonthUseCase usecase, TransactionViewModel model) {
        this.usecase = usecase;
        this.model = model;
    }

    public void execute(int month, int year) throws SQLException {
        List<TransactionViewDTO> listViewDTO = usecase.execute(month, year);
        List<TransactionViewItem> listItems = convertToViewItems(listViewDTO);
        model.transactionList = listItems;
        model.notifySubscribers();
    }

    // Hàm convert từ TransactionViewDTO sang TransactionViewItem
    private List<TransactionViewItem> convertToViewItems(List<TransactionViewDTO> listViewDTO) {
        List<TransactionViewItem> items = new ArrayList<>();
        int stt = 1;
        for (TransactionViewDTO dto : listViewDTO) {
            TransactionViewItem item = new TransactionViewItem();
            NumberFormat amountFormatter = new DecimalFormat("#,##0.###");
            item.stt = stt++;
            item.transactionId = dto.transactionId;
            item.transactionDate = dto.transactionDate != null ? dto.transactionDate.toString() : "";
            item.unitPrice = String.valueOf(dto.unitPrice);
            item.area = String.valueOf(dto.area);
            item.transactionType = dto.transactionType;
            item.amountTotal = amountFormatter.format(dto.amountTotal);
            items.add(item);
        }
        return items;
    }
}
