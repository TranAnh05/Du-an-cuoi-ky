package business.OpenAddTransactionForm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistence.OpenAddTransactionForm.OpenAddTransactionFormGateway;
import persistence.OpenAddTransactionForm.TransactionTypeDTO;

public class OpenAddTransactionFormUsecase {
    private OpenAddTransactionFormGateway gateway;

    public OpenAddTransactionFormUsecase(OpenAddTransactionFormGateway gateway) {
        this.gateway = gateway;
    }

    public List<ResTransactionAddFormDTO> execute() throws SQLException {
        List<TransactionTypeDTO> transactionTypes = gateway.getTransactionTypes();

        List<TransactionType> transactionTypeBusiness = convertToBusiness(transactionTypes);
        List<ResTransactionAddFormDTO> resDTO = convertToResDTO(transactionTypeBusiness);
        return resDTO;
    }

    private List<ResTransactionAddFormDTO> convertToResDTO(List<TransactionType> transactionTypeBusiness) {
        List<ResTransactionAddFormDTO> resDTOs = new ArrayList<>();

        for (TransactionType typeBus : transactionTypeBusiness) {
            ResTransactionAddFormDTO dto = new ResTransactionAddFormDTO();

            dto.transactionTypeCode = typeBus.getTransactionTypeCode();
            dto.transactionTypeName = typeBus.getTransactionTypeName();
            dto.description = typeBus.getDescription();

            resDTOs.add(dto);
        }

        return resDTOs;
    }

    private List<TransactionType> convertToBusiness(List<TransactionTypeDTO> transactionTypes) {
        List<TransactionType> businessList = new ArrayList<>();

        for (TransactionTypeDTO dto : transactionTypes) {
            TransactionType type = new TransactionType(dto.transactionTypeCode, dto.transactionTypeName, dto.description);
            businessList.add(type);
        }

        return businessList;
    }
}
