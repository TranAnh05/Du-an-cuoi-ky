package presentation;

import java.sql.SQLException;
import java.util.List;
import business.TotalTransactionUseCase;
import javax.swing.JOptionPane;

public class TotalTransactionViewController 
{
    private TransactionViewModel model;
    private TotalTransactionUseCase totalUseCase;
    private TotalTransactionViewUI view;

    public TotalTransactionViewController(TotalTransactionViewUI view, TransactionViewModel model, TotalTransactionUseCase totalUseCase) 
    {
        this.view = view;
        this.model = model;
        this.totalUseCase = totalUseCase;
    }

    public void loadTransactionsByType(String type) 
    {
        try {
            // Lấy danh sách giao dịch theo loại
            List<TransactionViewItem> transactionList = totalUseCase.getTransactionsByType(type);
            
            // Cập nhật model với danh sách giao dịch
            model.transactionList = transactionList;
            
            // Đếm số lượng giao dịch
            int totalCount = transactionList.size();    
            
            view.ShowTotalList(model, totalCount);
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Lỗi khi tải dữ liệu giao dịch: " + e.getMessage());
        }
    }

    public void execute() throws SQLException 
    {
        List<TransactionViewItem> transactionList = totalUseCase.execute();
        model.transactionList = transactionList;
        view.ShowTotalList(model, transactionList.size());
    }
}