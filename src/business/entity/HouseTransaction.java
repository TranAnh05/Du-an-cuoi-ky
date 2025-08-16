package business.entity;

import java.time.LocalDate;

public class HouseTransaction extends Transaction {
    private String houseType; 
    private String address;

    public HouseTransaction(String transactionId, LocalDate transactionDate, double unitPrice, double area,
            String houseType, String address) {
        super(transactionId, transactionDate, unitPrice, area, "GDN");
        this.houseType = houseType;
        this.address = address;
    }

    public String getHouseType() { return houseType; }

    public String getAddress() { return address; }

    @Override
    public double calculateAmount() {
        if ("luxury".equalsIgnoreCase(houseType)) { 
            return area * unitPrice;
        } 
        else if ("normal".equalsIgnoreCase(houseType)) { 
            return area * unitPrice * 0.9; 
        } else {
            throw new IllegalArgumentException("Loại nhà không hợp lệ: " + houseType);
        }
    }
}