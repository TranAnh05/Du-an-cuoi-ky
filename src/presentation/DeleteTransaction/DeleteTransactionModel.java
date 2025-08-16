package presentation.DeleteTransaction;

import business.DeleteTransaction.DeleteTransactionViewDTO;

public class DeleteTransactionModel {
    private DeleteTransactionViewDTO deletedTransaction;

    public DeleteTransactionViewDTO getDeletedTransaction() { return deletedTransaction; }
    public void setDeletedTransaction(DeleteTransactionViewDTO deletedTransaction) { this.deletedTransaction = deletedTransaction; }
}