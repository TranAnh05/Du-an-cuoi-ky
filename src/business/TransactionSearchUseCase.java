package business;

import java.text.DecimalFormat;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import persistence.TransactionListViewDAO;
import persistence.TransactionDTO;
import presentation.Publisher;

public class TransactionSearchUseCase extends Publisher {
    private TransactionListViewDAO dao;
    private TransactionFactory factory;

    public TransactionSearchUseCase(TransactionListViewDAO dao, TransactionFactory factory) {
        this.dao = dao;
        this.factory = factory;
    }

    public List<TransactionViewItem> search(String keyword) throws SQLException {
        List<TransactionDTO> dtos = dao.searchByKeyword(keyword);
        List<TransactionViewItem> result = convertToTransactionViewItem(dtos);
        TransactionViewModel model = new TransactionViewModel();
        model.transactionList = result;
        notifySubscribers(model);
        return result;
    }

    private List<TransactionViewItem> convertToTransactionViewItem(List<TransactionDTO> dtos) {
        List<TransactionViewItem> items = new ArrayList<>();
        int stt = 1;
        DecimalFormat df = new DecimalFormat("#,###.##");
        for (TransactionDTO dto : dtos) {
            Transaction transaction = factory.createTransaction(dto);
            TransactionViewItem item = new TransactionViewItem();
            item.stt = stt++;
            item.transactionId = dto.transactionId;
            item.transactionDate = dto.transactionDate.toString();
            item.unitPrice = df.format(dto.unitPrice != null ? dto.unitPrice : 0);
            item.area = df.format(dto.area != null ? dto.area : 0);
            item.transactionType = dto.transactionType;
            item.landType = dto.landType != null ? dto.landType : "";
            item.houseType = dto.houseType != null ? dto.houseType : "";
            item.address = dto.address != null ? dto.address : ""; // Lấy address trực tiếp từ DTO
            item.amountTotal = df.format(transaction.calculateAmount());
            items.add(item);
        }
        return items;
    }
}