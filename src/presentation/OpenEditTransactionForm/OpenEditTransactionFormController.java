package presentation.OpenEditTransactionForm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import business.OpenEditTransactionForm.OpenEditTransactionFormUseCase;
import business.OpenEditTransactionForm.TransactionViewDTO;

public class OpenEditTransactionFormController {
    private OpenEditTransactionFormModel model;
    private OpenEditTransactionFormUseCase useCase;

    public OpenEditTransactionFormController(OpenEditTransactionFormModel model, OpenEditTransactionFormUseCase useCase) {
        this.model = model;
        this.useCase = useCase;
    }

    public void execute(String transactionId) throws SQLException {
        List<TransactionViewDTO> dtos = useCase.execute(transactionId);
        model.transactionItems = convertToViewItems(dtos);
        model.notifySubscribers();
    }

    private List<OpenEditTransactionFormItem> convertToViewItems(List<TransactionViewDTO> dtos) {
        List<OpenEditTransactionFormItem> items = new ArrayList<>();
        int stt = 1;
        for (TransactionViewDTO dto : dtos) {
            OpenEditTransactionFormItem item = new OpenEditTransactionFormItem();
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