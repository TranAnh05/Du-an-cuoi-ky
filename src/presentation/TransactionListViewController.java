package presentation;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import business.entity.Transaction;
import business.TransactionListViewUseCase;
import business.TransactionViewDTO;

public class TransactionListViewController {
    private TransactionViewModel transactionViewModel;
    private TransactionListViewUseCase usecase;

    public TransactionListViewController(TransactionViewModel transactionViewModel,
            TransactionListViewUseCase usecase) {
        this.transactionViewModel = transactionViewModel;
        this.usecase = usecase;
    }

    public void execute() throws SQLException, ParseException {
        List<TransactionViewDTO> listDTO = usecase.execute();
        List<TransactionViewItem> listViewItem = convertDTODataToItemData(listDTO);

        transactionViewModel.transactionList = listViewItem;
        transactionViewModel.notifySubscribers();

    }

    private List<TransactionViewItem> convertDTODataToItemData(List<TransactionViewDTO> listDTO) {
        List<TransactionViewItem> listItem = new ArrayList<TransactionViewItem>();

        int stt = 1;
        for(TransactionViewDTO dto : listDTO) {
            TransactionViewItem item = new TransactionViewItem();

            item.stt = stt++;
            item.transactionId = dto.transactionId;
            item.transactionDate = dto.transactionDate.toString();
            item.unitPrice = String.valueOf(dto.unitPrice);
            item.area = String.valueOf(dto.area);
            item.transactionType = dto.transactionType;
            item.amountTotal = String.valueOf(dto.amountTotal);
            listItem.add(item);
        }

        return listItem;
    }

    
}