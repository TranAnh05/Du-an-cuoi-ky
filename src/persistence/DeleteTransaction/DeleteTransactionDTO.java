package persistence.DeleteTransaction;

import java.util.Date;

public class DeleteTransactionDTO {
    private String transactionId;
    private String transactionType;
    private Date transactionDate;
    private double unitPrice;
    private double area;
    private String landType;
    private String houseType;
    private String address;

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