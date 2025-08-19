package presentation.DeleteTransaction;

import java.sql.SQLException;
import java.text.ParseException;

import business.DeleteTransaction.DeleteTransactionUseCase;
import business.DeleteTransaction.DeleteTransactionViewDTO;
import persistence.DeleteTransaction.DeleteTransactionDTO;
import presentation.TransactionListView.TransactionListViewController;

public class DeleteTransactionController {
    private DeleteTransactionUseCase useCase;
    private DeleteTransactionModel model; // used to store last deleted
    private TransactionListViewController listViewController;

    public DeleteTransactionController(DeleteTransactionUseCase useCase) {
        this.useCase = useCase;
        this.model = null;
    }

    public DeleteTransactionController(DeleteTransactionModel model, DeleteTransactionUseCase useCase) {
        this.model = model;
        this.useCase = useCase;
    }

    public DeleteTransactionController(DeleteTransactionModel model, DeleteTransactionUseCase useCase,
            TransactionListViewController listViewController) {
        this.model = model;
        this.useCase = useCase;
        this.listViewController = listViewController;
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
                        dto.getAddress()));
            }

            if (listViewController != null) {

                try {
                    listViewController.execute();// Refresh full list
                } catch (SQLException e) {

                    e.printStackTrace();
                } catch (ParseException e) {

                    e.printStackTrace();
                }
            }
            return true;
        }
        return false;
    }

}