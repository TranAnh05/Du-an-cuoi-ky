package presentation.OpenAddTransactionForm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import business.OpenAddTransactionForm.OpenAddTransactionFormUsecase;
import business.OpenAddTransactionForm.ResTransactionAddFormDTO;

public class OpenAddTransactionFormController {
    private OpenAddTransactionFormUsecase usecase;
    private OpenAddTransactionFormModel model;

    public OpenAddTransactionFormController(OpenAddTransactionFormUsecase usecase, OpenAddTransactionFormModel model) {
        this.usecase = usecase;
        this.model = model;
    }

    public void execute() throws SQLException {
        List<ResTransactionAddFormDTO> resDTOs = usecase.execute();
        List<TransactionTypeItem> Items = convertToTransactionTypeItems(resDTOs);
        
        model.transactionTypeItems = Items;
        model.notifySubscribers();
    }

    private List<TransactionTypeItem> convertToTransactionTypeItems(List<ResTransactionAddFormDTO> resDTOs) {
        List<TransactionTypeItem> items = new ArrayList<>();

        for (ResTransactionAddFormDTO dto : resDTOs) {
            TransactionTypeItem item = new TransactionTypeItem();

            item.transactionTypeCode = dto.transactionTypeCode;
            item.transactionTypeName = dto.transactionTypeName;

            items.add(item);
        }

        return items;
    }
}
