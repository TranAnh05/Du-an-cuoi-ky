package business.OpenChoseTransactionForm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistence.OpenChoseTransactionForm.OpenChoseTransactionGateway;
import persistence.OpenChoseTransactionForm.TypeDTO;

public class OpenChoseTransactionFormUseCase 
{
    private OpenChoseTransactionGateway gateway;

    public OpenChoseTransactionFormUseCase(OpenChoseTransactionGateway gateway) 
    {
        this.gateway = gateway;
    }

    public List<ResTypeDTO> execute() throws SQLException
    {
        List<TypeDTO> listDTO = gateway.getTransactionType();
        List<TransactionType> types = convertToBussiness(listDTO);
        return convertToResponseDTO(types);
    }

    private List<ResTypeDTO> convertToResponseDTO(List<TransactionType> types) {
		List<ResTypeDTO> resTypes = new ArrayList<ResTypeDTO>();
		for (TransactionType type : types) 
        {
			ResTypeDTO dto = new ResTypeDTO();
            dto.Type = type.getTransactionType();

            resTypes.add(dto);
		}
		return resTypes;
	}

	private List<TransactionType> convertToBussiness(List<TypeDTO> listDTO) 
    {
		List<TransactionType> types = new ArrayList<TransactionType>();
		for (TypeDTO typeDTO : listDTO) 
        {
			TransactionType transactionType = new TransactionType(typeDTO.transactionType);
            types.add(transactionType);
		}
		
		
		return types;
	}
}
