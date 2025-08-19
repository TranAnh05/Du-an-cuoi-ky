package presentation.DeleteTransaction;

import java.util.List;

import business.DeleteTransaction.DeleteTransactionViewDTO;
import presentation.Publisher;
import presentation.TransactionListView.TransactionViewItem;

public class DeleteTransactionModel extends Publisher {
    private DeleteTransactionViewDTO deletedTransaction;

    public DeleteTransactionViewDTO getDeletedTransaction() { return deletedTransaction; }
    public void setDeletedTransaction(DeleteTransactionViewDTO deletedTransaction) { this.deletedTransaction = deletedTransaction; }
    public List<DeleteTransactionItem> transactionList;
}