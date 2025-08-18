package persistence.SaveTransaction;

import java.time.LocalDate;

public class SavedTransactionDTO {
    public String transactionId;
    public LocalDate transactionDate;
    public double unitPrice;
    public double area;
    public String transactionType;
    public String landType;
    public String houseType; 
    public String address;



    public SavedTransactionDTO(
        String transactionId,
        String transactionType,
        LocalDate transactionDate,
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

    public SavedTransactionDTO() {
        //TODO Auto-generated constructor stub
    }

    public String getTransactionId() { return transactionId; }
    public String getTransactionType() { return transactionType; }
    public LocalDate getTransactionDate() { return transactionDate; }
    public double getUnitPrice() { return unitPrice; }
    public double getArea() { return area; }
    public String getLandType() { return landType; }
    public String getHouseType() { return houseType; }
    public String getAddress() { return address; }
}
