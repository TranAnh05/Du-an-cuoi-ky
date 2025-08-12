package presentation.TotalTransaction;

import presentation.Publisher;

public class TotalTransactionModel extends Publisher 
{
    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
        notifySubscribers();
    }

    public void setFromItem(TotalTransactionItem item) {
        if (item != null) {
            this.total = item.Total;
            notifySubscribers();
        }
    }
}
