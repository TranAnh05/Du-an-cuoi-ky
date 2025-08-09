package presentation;

import business.TotalTransactionUseCase;

import java.sql.SQLException;

public class TotalTransactionViewController 
{
    private TotalTransactionViewModel model;
    private TotalTransactionUseCase totalUseCase;
    private TotalTransactionViewUI view;

    public TotalTransactionViewController(TotalTransactionViewUI view,TotalTransactionViewModel model, TotalTransactionUseCase totalUseCase) 
    {
        this.model = model;
        this.totalUseCase = totalUseCase;
        this.view = view;

        view.getLandButton().addActionListener(e -> loadTotalTransactionsByType("GDĐ"));
        view.getHouseButton().addActionListener(e -> loadTotalTransactionsByType("GDN"));
    }

    public void loadTotalTransactionsByType(String type) 
    {
        try 
        {
            // Gọi UseCase để lấy tổng số giao dịch theo loại
            int totalCount = totalUseCase.execute(type);

            // Cập nhật vào ViewModel -> tự động thông báo cho View cập nhật
            model.setTotalTransactionCount(totalCount);
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }


}