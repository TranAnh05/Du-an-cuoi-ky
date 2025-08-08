package business;

import java.text.DecimalFormat;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import business.entity.Transaction;
import persistence.TransactionListViewDAO;
import persistence.TransactionDTO;
import persistence.TransactionGateway;
import presentation.Publisher;
import presentation.TransactionViewItem;
import presentation.TransactionViewModel;

public class TransactionSearchUseCase extends Publisher {
    private TransactionGateway gateway;
    private TransactionFactory factory;

    public TransactionSearchUseCase(TransactionGateway gateway, TransactionFactory factory) {
        this.gateway = gateway;
        this.factory = factory;
    }

    public List<TransactionViewItem> search(String keyword) throws SQLException {
        List<TransactionDTO> dtos = gateway.searchByKeyword(keyword);
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
            item.unitPrice = df.format(dto.unitPrice);
            item.area = df.format(dto.area);
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