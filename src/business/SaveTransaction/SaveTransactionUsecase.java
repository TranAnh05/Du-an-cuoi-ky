package business.SaveTransaction;

import business.entity.LandTransaction;
import business.entity.Transaction;
import persistence.SaveTransaction.SaveTransactionGateway;
import persistence.SaveTransaction.SavedTransactionDTO;

public class SaveTransactionUsecase {
    private SaveTransactionGateway gateway;

    public SaveTransactionUsecase(SaveTransactionGateway gateway) {
        this.gateway = gateway;
    }

    public boolean execute(SavedDataDTO data) {
        // convert sang Business
        Transaction transaction = FactorySavedTransaction.createTransaction(data);
        // convert sang TransactionSavedDTO 
        SavedTransactionDTO perDTO = convertToPerData(transaction);
        boolean success = gateway.insertTransaction(perDTO);
        return success;
    }

    private SavedTransactionDTO convertToPerData(Transaction transaction) {
        SavedTransactionDTO dto = FactorySavedDTO.createTransactionDTO(transaction);
        return dto;
    }


}
