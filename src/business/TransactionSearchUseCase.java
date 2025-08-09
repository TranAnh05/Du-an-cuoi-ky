// package business;

// import java.text.DecimalFormat;
// import java.sql.SQLException;
// import java.util.ArrayList;
// import java.util.List;
// import business.entity.Transaction;
// import persistence.TransactionListViewDAO;
// import persistence.TransactionDTO;
// import persistence.TransactionGateway;
// import presentation.Publisher;
// import presentation.TransactionViewItem;
// import presentation.TransactionViewModel;

// public class TransactionSearchUseCase extends Publisher {

//     // private TransactionListViewDAO dao;

//     private TransactionGateway gateway;
//     private TransactionFactory factory;

//     public TransactionSearchUseCase(TransactionGateway gateway, TransactionFactory factory) {
//         this.gateway = gateway;
//         this.factory = factory;
//     }

//     public List<TransactionViewItem> search(String keyword) throws SQLException {
//         List<TransactionDTO> dtos = gateway.searchByKeyword(keyword);
//         List<TransactionViewItem> result = convertToTransactionViewItem(dtos);
//         TransactionViewModel model = new TransactionViewModel();
//         model.transactionList = result;
//         notifySubscribers(model);
//         return result;
//     }

//     private List<TransactionViewItem> convertToTransactionViewItem(List<TransactionDTO> dtos) {
//         List<TransactionViewItem> items = new ArrayList<>();
//         int stt = 1;
//         DecimalFormat df = new DecimalFormat("#,###.##");
//         for (TransactionDTO dto : dtos) {
//             Transaction transaction = factory.createTransaction(dto);
//             TransactionViewItem item = new TransactionViewItem();
//             item.stt = stt++;
//             item.transactionId = dto.transactionId;
//             item.transactionDate = dto.transactionDate.toString();
//             item.unitPrice = df.format(dto.unitPrice);
//             item.area = df.format(dto.area);
//             item.transactionType = dto.transactionType;
//             item.landType = dto.landType != null ? dto.landType : "";
//             item.houseType = dto.houseType != null ? dto.houseType : "";
//             item.address = dto.address != null ? dto.address : ""; // Lấy address trực tiếp từ DTO
//             item.amountTotal = df.format(transaction.calculateAmount());
//             items.add(item);
//         }
//         return items;
//     }
// }

package business;

import java.text.DecimalFormat;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import business.entity.Transaction;
import persistence.TransactionGateway;
import persistence.TransactionDTO;
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
//     public List<TransactionViewItem> search(String keyword) throws SQLException {
//         List<TransactionDTO> dtos = gateway.searchByKeyword(keyword);
//         List<TransactionViewItem> result = convertToTransactionViewItem(dtos);
//         TransactionViewModel model = new TransactionViewModel();
//         model.transactionList = result;
//         notifySubscribers(model);
//         return result;
//     }
    public List<TransactionViewItem> search(String keyword) throws SQLException {
        // Bước 3: Phần mềm tìm thông tin giao dịch trong cơ sở dữ liệu
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Vui lòng nhập mã giao dịch để tìm kiếm!");
        }
        List<TransactionDTO> dtos = gateway.searchByKeyword(keyword);

        // Bước 4: Phần mềm trả về giao dịch phù hợp với tiêu chí tìm kiếm
        List<TransactionViewItem> result = convertToTransactionViewItem(dtos);
        TransactionViewModel model = new TransactionViewModel();
        model.transactionList = result;
        notifySubscribers(model); // Cập nhật giao diện
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
            item.address = dto.address != null ? dto.address : "";
            item.amountTotal = df.format(transaction.calculateAmount());
            items.add(item);
        }
        return items;
    }
}