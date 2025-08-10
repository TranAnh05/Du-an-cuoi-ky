package business.TransactionListView.entity;

import java.time.LocalDate;

public abstract class Transaction {
    protected String transactionId;
    protected LocalDate transactionDate;
    protected double unitPrice;
    protected double area;
    protected String transactionType;

    public Transaction(String transactionId, LocalDate transactionDate, double unitPrice, double area,
            String transactionType) {
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
        this.unitPrice = unitPrice;
        this.area = area;
        this.transactionType = transactionType;
    }

    
    public String getTransactionId() { return transactionId; }
    public LocalDate getTransactionDate() { return transactionDate; };
    public double getUnitPrice() { return unitPrice; }
    public double getArea() { return area; }
    public String getTransactionType() { return transactionType;}

    public abstract double calculateAmount();
}