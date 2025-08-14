package business.OpenAddTransactionForm;

public class TransactionType {
    private String transactionTypeCode;
    private String TransactionTypeName;
    private String description;

    public TransactionType(String transactionTypeCode, String transactionTypeName, String description) {
        this.transactionTypeCode = transactionTypeCode;
        TransactionTypeName = transactionTypeName;
        this.description = description;
    }

    public String getTransactionTypeCode() {
        return transactionTypeCode;
    }

    public String getTransactionTypeName() {
        return TransactionTypeName;
    }
    public String getDescription() {
        return description;
    }

    
}
