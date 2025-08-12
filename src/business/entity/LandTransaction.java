package business.entity;

import java.time.LocalDate;

public class LandTransaction extends Transaction {
    private String landType;

    public LandTransaction(String transactionId, LocalDate transactionDate, double unitPrice, double area,
           String landType) {
        super(transactionId, transactionDate, unitPrice, area, "GDĐ");
        this.landType = landType;
    }

    public String getLandType() { return landType; }

    @Override
    public double calculateAmount() {
        if ("A".equalsIgnoreCase(landType)) {
            return area * unitPrice * 1.5;
        } else if ("B".equalsIgnoreCase(landType) || "C".equalsIgnoreCase(landType)) {
            return area * unitPrice;
        } else {
           throw new IllegalArgumentException("Loại đất không hợp lệ: " + landType);
        }
    }
}