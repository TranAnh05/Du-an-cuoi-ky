package presentation;

import business.TransactionFactory;
import business.TransactionSearchUseCase;
import business.TransactionListViewUseCase; // Thêm import
import persistence.TransactionGateway;

import java.sql.SQLException;

import javax.swing.JOptionPane;

public class TransactionSearchController {
    private TransactionSearchUseCase searchUseCase;
    private TransactionListViewUI view;
    private TransactionListViewUseCase listViewUseCase; // Thêm tham chiếu

    public TransactionSearchController(TransactionGateway gateway, TransactionFactory factory, TransactionListViewUI view, TransactionListViewUseCase listViewUseCase) {
        this.searchUseCase = new TransactionSearchUseCase(gateway, factory);
        this.view = view;
        this.listViewUseCase = listViewUseCase; // Gán tham chiếu
        this.view.setSearchController(this);
        searchUseCase.addSubscriber((Subscriber) view);
    }

    public void searchTransaction(String keyword) {
        try {
            System.out.println("Tìm kiếm với từ khóa: '" + keyword + "'");
            if (keyword == null || keyword.trim().isEmpty()) {
                System.out.println("Từ khóa rỗng, hiển thị tất cả giao dịch.");
                // Lấy toàn bộ danh sách
                TransactionViewModel model = view.getViewModel();
                if (model != null && listViewUseCase != null) {
                    model.transactionList = listViewUseCase.executeQuery(); // Sử dụng UseCase để lấy tất cả
                    view.showList(model);
                    System.out.println("Hiển thị tất cả, số kết quả: "
                            + (model.transactionList != null ? model.transactionList.size() : 0));
                } else {
                    System.out.println("ViewModel hoặc listViewUseCase is null");
                }
            } else {
                TransactionViewModel model = view.getViewModel();
                if (model != null) {
                    model.transactionList = searchUseCase.search(keyword.trim());
                    view.showList(model);
                    System.out.println("Tìm kiếm hoàn tất, model.transactionList size: "
                            + (model.transactionList != null ? model.transactionList.size() : 0));
                } else {
                    System.out.println("ViewModel is null in searchTransaction");
                }
            }
        } catch (SQLException e) {
            System.out.println("SQLException in search: " + e.getMessage());
            JOptionPane.showMessageDialog(view, "Lỗi khi tìm kiếm giao dịch: " + e.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}