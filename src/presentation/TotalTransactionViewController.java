package presentation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import business.TotalTransactionUseCase;
import business.TransactionViewDTO;

import javax.swing.JOptionPane;

public class TotalTransactionViewController 
{
    private TransactionViewModel model;
    private TotalTransactionUseCase totalUseCase;

    public TotalTransactionViewController(TransactionViewModel model, TotalTransactionUseCase totalUseCase) 
    {
        this.model = model;
        this.totalUseCase = totalUseCase;
    }

    public void loadTransactionsByType(String type) 
    {
        try {
            // Lấy danh sách giao dịch theo loại
            List<TransactionViewDTO> dtoList = totalUseCase.getTransactionsByType(type);

            // chuyển sang dữ liệu trình bày 
            List<TransactionViewItem> presenterList = this.convertToPresenter(dtoList);

            //yêu cầu Model trình diễn dữ liệu mới 
            model.transactionList = presenterList;
            model.notifySubscribers();
        } 
        catch (SQLException e) 
        {
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE); 
            e.printStackTrace();
        }
    }
    private List<TransactionViewItem> convertToPresenter(List<TransactionViewDTO> dtos) 
    {
        List<TransactionViewItem> listViewItem = new ArrayList<>();
        int stt = 1;

        for (TransactionViewDTO dto : dtos) {
            TransactionViewItem item = new TransactionViewItem();
            item.stt = stt++;
            item.transactionId = dto.transactionId;
            item.transactionDate = dto.transactionDate.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            item.unitPrice = String.format("%,.0f", dto.unitPrice);
            item.area = String.format("%,.1f", dto.area);
            item.transactionType = dto.transactionType;
            item.amountTotal = String.format("%,.0f", dto.amountTotal);

            listViewItem.add(item);
        }

        return listViewItem;
    }

}