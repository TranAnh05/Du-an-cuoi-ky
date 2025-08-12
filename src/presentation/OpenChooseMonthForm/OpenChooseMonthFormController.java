package presentation.OpenChooseMonthForm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import business.OpenChooseMonthForm.OpenChooseMonthFormUsecase;
import business.OpenChooseMonthForm.TransactionMonthYearViewDTO;

public class OpenChooseMonthFormController {
    private OpenChooseMonthFormUsecase usecase;
    private OpenChooseMonthFormModel model;

    public OpenChooseMonthFormController(OpenChooseMonthFormUsecase usecase, OpenChooseMonthFormModel model) {
        this.usecase = usecase;
        this.model = model;
    }

    public void execute() throws SQLException {
        List<TransactionMonthYearViewDTO> listDTO = usecase.execute();
        List<MonthViewItem> listViewItems = convertToViewItem(listDTO);

        model.monthViewItems = listViewItems;
        model.notifySubscribers();
    }

    private List<MonthViewItem> convertToViewItem(List<TransactionMonthYearViewDTO> listDTO) {
        List<MonthViewItem> listViewItems = new ArrayList<>();
        for (TransactionMonthYearViewDTO dto : listDTO) {
            MonthViewItem item = new MonthViewItem();
            
            item.month = String.valueOf(dto.month);
            item.year = String.valueOf(dto.year);
            listViewItems.add(item);
        }

        return listViewItems;
    }

}
