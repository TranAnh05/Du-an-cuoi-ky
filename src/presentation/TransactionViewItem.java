package presentation;


public class TransactionViewItem 
{
    public int stt;
    public String transactionId;
    public String transactionDate;
    public String unitPrice;
    public String area;
    public String transactionType;
    public String amountTotal;
    public String landType;
    public String houseType;
    public String address;
    
    public int getStt() {
        return stt;
    }

    public String getId() {
        return transactionId;
    }

    public String getDate() {
        return transactionDate;
    }

    public String getPrice() {
        return unitPrice;
    }

    public String getArea() {
        return area;
    }

    public String getType() {
        return transactionType;
    }

    public String getTotal() {
        return amountTotal;
    }

    public String getLandType() {
        return landType != null ? landType : "";
    }
    public String getHouseType() {
        return houseType != null ? houseType : "";
    }

    public String getAddress() {
        return address != null ? address : "";
    }
}

