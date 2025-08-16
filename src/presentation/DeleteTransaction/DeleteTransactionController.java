package presentation.DeleteTransaction;

import business.DeleteTransaction.DeleteTransactionUseCase;
import business.DeleteTransaction.DeleteTransactionViewDTO;
import persistence.DeleteTransaction.DeleteTransactionDTO;

public class DeleteTransactionController {
    private final DeleteTransactionUseCase useCase;
    private final DeleteTransactionModel model; // used to store last deleted

    public DeleteTransactionController(DeleteTransactionUseCase useCase) {
        this.useCase = useCase;
        this.model = null;
    }

    public DeleteTransactionController(DeleteTransactionModel model, DeleteTransactionUseCase useCase) {
        this.model = model;
        this.useCase = useCase;
    }

    public boolean execute(String transactionId) throws java.sql.SQLException {
        DeleteTransactionDTO dto = useCase.execute(transactionId);
        if (dto != null) {
            if (model != null) {
                model.setDeletedTransaction(new DeleteTransactionViewDTO(
                    dto.getTransactionId(),
                    dto.getTransactionType(),
                    dto.getTransactionDate(),
                    dto.getUnitPrice(),
                    dto.getArea(),
                    dto.getLandType(),
                    dto.getHouseType(),
                    dto.getAddress()
                ));
            }
            return true;
        }
        return false;
    }
}