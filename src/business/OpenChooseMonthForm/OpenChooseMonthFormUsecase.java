package business.OpenChooseMonthForm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistence.OpenChooseMonthForm.MonthYearDTO;
import persistence.OpenChooseMonthForm.OpenChooseMonthFormGateway;

public class OpenChooseMonthFormUsecase {
    private OpenChooseMonthFormGateway gateway;

    public OpenChooseMonthFormUsecase(OpenChooseMonthFormGateway gateway) {
        this.gateway = gateway;
    }

    public List<TransactionMonthYearViewDTO> execute() throws SQLException {
        List<MonthYearDTO> listDTO = gateway.getMonthYear();
        List<MonthYear> listBusiness = ConvertToBusinessData(listDTO);

        return convertToViewDTO(listBusiness);
    }

    private List<TransactionMonthYearViewDTO> convertToViewDTO(List<MonthYear> listBusiness) {
        List<TransactionMonthYearViewDTO> listViewDTO = new ArrayList<>();

        for (MonthYear business : listBusiness) {
            TransactionMonthYearViewDTO viewDTO = new TransactionMonthYearViewDTO();
            viewDTO.month = business.getMonth();
            viewDTO.year = business.getYear();
            listViewDTO.add(viewDTO);
        }
        
        return listViewDTO;
    }

    private List<MonthYear> ConvertToBusinessData(List<MonthYearDTO> listDTO) {
        List<MonthYear> listBusiness = new ArrayList<>();

        for (MonthYearDTO dto : listDTO) {
            MonthYear business = new MonthYear(dto.month, dto.year);
            listBusiness.add(business);
        }

        return listBusiness;
    }
}
