package presentation;

public class TotalTransactionViewModel extends Publisher
{
    private int totalCount;

    public void setTotalTransactionCount(int total) 
    {
        this.totalCount = total;
        notifySubscribers();
    }

    public int getTotalTransactionCount() {
        return totalCount;
    }
}
