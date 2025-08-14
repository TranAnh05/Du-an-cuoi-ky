package persistence.SaveTransaction;

public interface SaveTransactionGateway {

    boolean insertTransaction(SavedTransactionDTO transaction);
} 
