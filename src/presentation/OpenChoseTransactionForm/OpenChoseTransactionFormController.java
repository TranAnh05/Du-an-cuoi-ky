package presentation.OpenChoseTransactionForm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import business.OpenChoseTransactionForm.OpenChoseTransactionFormUseCase;
import business.OpenChoseTransactionForm.ResTypeDTO;

public class OpenChoseTransactionFormController 
{
    private OpenChoseTransactionFormModel model;
    private OpenChoseTransactionFormUseCase useCase;
    public OpenChoseTransactionFormController(OpenChoseTransactionFormModel model,
            OpenChoseTransactionFormUseCase useCase) 
    {
        this.model = model;
        this.useCase = useCase;
    }
    public void execute() throws SQLException
    {
        List<ResTypeDTO> resList = useCase.execute();
        model.typeItems = convertToPresenter(resList);
        model.notifySubscribers(); 
    }

    private List<TransactionTypeItem> convertToPresenter(List<ResTypeDTO> resList) 
    {
		List<TransactionTypeItem> typePresenters = new ArrayList<TransactionTypeItem>();
		for (ResTypeDTO resTypeDTO : resList) {
			TransactionTypeItem item = new TransactionTypeItem();
			item.type = resTypeDTO.Type;
			
            typePresenters.add(item);
		}
		return typePresenters;
	}
}