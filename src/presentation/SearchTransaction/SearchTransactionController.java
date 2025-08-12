package presentation.SearchTransaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import business.SearchTransaction.SearchTransactionUseCase;
import business.SearchTransaction.TransactionViewDTO;

public class SearchTransactionController {
    private SearchTransactionModel model;
    private SearchTransactionUseCase useCase;

    public SearchTransactionController(SearchTransactionModel model, SearchTransactionUseCase useCase) {
        this.model = model;
        this.useCase = useCase;
    }

    public void execute(String searchTerm) throws SQLException {
        List<TransactionViewDTO> dtos = useCase.execute(searchTerm);
        System.out.println("ViewDTOs count for search: " + dtos.size()); // Debug
        model.transactionItems = convertToViewItems(dtos);
        System.out.println("Updated transactionItems size: " + model.transactionItems.size()); // Debug
        for (SearchTransactionItem item : model.transactionItems) {
            System.out.println("Item: " + item.transactionId + ", Date: " + item.transactionDate + ", Type: " + item.transactionType);
        }
        model.notifySubscribers();
    }

    private List<SearchTransactionItem> convertToViewItems(List<TransactionViewDTO> dtos) {
        List<SearchTransactionItem> items = new ArrayList<>();
        int stt = 1;
        for (TransactionViewDTO dto : dtos) {
            SearchTransactionItem item = new SearchTransactionItem();
            item.stt = stt++;
            item.transactionId = dto.transactionId;
            item.transactionDate = dto.transactionDate;
            item.unitPrice = dto.unitPrice;
            item.area = dto.area;
            item.transactionType = dto.transactionType;
            item.landType = dto.landType;
            item.houseType = dto.houseType;
            item.address = dto.address;
            items.add(item);
        }
        return items;
    }
}