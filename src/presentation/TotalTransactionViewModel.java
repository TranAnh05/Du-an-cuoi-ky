package presentation;

public class TotalTransactionViewModel extends Publisher
{
    private int totalCount;

    public void setTotalTransactionCount(int total) 
    {
        this.totalCount = total;
        notifySubscribers(); // thông báo cho View
    }

    public int getTotalTransactionCount() {
        return totalCount;
    }
}
