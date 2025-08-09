package business;

import java.text.DecimalFormat;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import business.entity.HouseTransaction;
import business.entity.LandTransaction;
import business.entity.Transaction;
import persistence.TransactionListViewDAO;
import persistence.TransactionDTO;
import persistence.TransactionGateway;
import presentation.Publisher;
import presentation.TransactionViewItem;
import presentation.TransactionViewModel;

public class TransactionUpdateUseCase extends Publisher {
    private TransactionGateway gateway;

    public TransactionUpdateUseCase(TransactionGateway gateway) {
        this.gateway = gateway;
    }

    public void updateTransaction(String transactionId, TransactionDTO updatedDto) throws SQLException {
        updatedDto.transactionId = transactionId;
        gateway.updateTransaction(updatedDto);
        List<TransactionDTO> updatedList = gateway.getAll();
        List<TransactionViewItem> result = convertToTransactionViewItem(updatedList);
        TransactionViewModel model = new TransactionViewModel();
        model.transactionList = result;
        notifySubscribers(model);
    }

    private List<TransactionViewItem> convertToTransactionViewItem(List<TransactionDTO> listDTO) {
        List<TransactionViewItem> itemList = new ArrayList<>();
        int stt = 1;
        DecimalFormat df = new DecimalFormat("#,###.##");
        for (TransactionDTO dto : listDTO) {
            Transaction transaction = TransactionFactory.createTransaction(dto);
            TransactionViewItem item = new TransactionViewItem();
            item.stt = stt++;
            item.transactionId = transaction.getTransactionId();
            item.transactionDate = transaction.getTransactionDate().toString();
            item.unitPrice = df.format(transaction.getUnitPrice());
            item.area = df.format(transaction.getArea());
            item.transactionType = transaction.getTransactionType();
            item.amountTotal = df.format(transaction.calculateAmount());
            item.landType = transaction instanceof LandTransaction ? ((LandTransaction) transaction).getLandType() : "";
            item.houseType = transaction instanceof HouseTransaction ? ((HouseTransaction) transaction).getHouseType() : "";
            item.address = dto.address != null ? dto.address : ""; // Lấy address trực tiếp từ DTO
            itemList.add(item);
        }
        return itemList;
    }
}