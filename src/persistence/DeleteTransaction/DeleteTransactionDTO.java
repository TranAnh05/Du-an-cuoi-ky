package persistence.DeleteTransaction;

import java.util.Date;

public class DeleteTransactionDTO {
    private final String transactionId;
    private final String transactionType;
    private final Date transactionDate;
    private final double unitPrice;
    private final double area;
    private final String landType;
    private final String houseType;
    private final String address;

    public DeleteTransactionDTO(
        String transactionId,
        String transactionType,
        Date transactionDate,
        double unitPrice,
        double area,
        String landType,
        String houseType,
        String address
    ) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.unitPrice = unitPrice;
        this.area = area;
        this.landType = landType;
        this.houseType = houseType;
        this.address = address;
    }

    public String getTransactionId() { return transactionId; }
    public String getTransactionType() { return transactionType; }
    public Date getTransactionDate() { return transactionDate; }
    public double getUnitPrice() { return unitPrice; }
    public double getArea() { return area; }
    public String getLandType() { return landType; }
    public String getHouseType() { return houseType; }
    public String getAddress() { return address; }
}