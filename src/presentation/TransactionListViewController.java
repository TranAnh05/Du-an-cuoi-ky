package presentation;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import business.TransactionListViewUseCase;
import business.TransactionSearchUseCase;
import business.TransactionUpdateUseCase;
import business.TransactionViewItem;
import business.TransactionViewModel;
import persistence.TransactionDTO;

public class TransactionListViewController {
    private TransactionListViewUI view;
    private TransactionViewModel transactionViewModel;
    private TransactionListViewUseCase listViewUseCase;
    private TransactionSearchUseCase searchUseCase;
    private TransactionUpdateUseCase updateUseCase;

    public TransactionListViewController(TransactionListViewUI view, TransactionViewModel transactionViewModel,
            TransactionListViewUseCase listViewUseCase, TransactionSearchUseCase searchUseCase,
            TransactionUpdateUseCase updateUseCase) {
        this.view = view;
        this.transactionViewModel = transactionViewModel;
        this.listViewUseCase = listViewUseCase;
        this.searchUseCase = searchUseCase;
        this.updateUseCase = updateUseCase;
        view.setController(this);
        listViewUseCase.addSubscriber((Subscriber) view);
        searchUseCase.addSubscriber((Subscriber) view);
        updateUseCase.addSubscriber((Subscriber) view);
    }

    public void execute() {
        try {
            transactionViewModel.transactionList = listViewUseCase.execute();
            view.showList(transactionViewModel);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Lỗi khi tải danh sách giao dịch: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void editTransaction(int rowIndex) {
        if (rowIndex >= 0 && transactionViewModel.transactionList != null && rowIndex < transactionViewModel.transactionList.size()) {
            TransactionViewItem item = transactionViewModel.transactionList.get(rowIndex);
            TransactionEditDialog dialog = new TransactionEditDialog(view, item.transactionId);
            dialog.getTxtDate().setText(item.transactionDate);
            dialog.getTxtUnitPrice().setText(item.unitPrice.replace(",", ""));
            dialog.getTxtArea().setText(item.area.replace(",", ""));
            dialog.getTxtTransactionType().setText(item.transactionType);
            dialog.getTxtLandType().setText(item.landType != null ? item.landType : "");
            dialog.getTxtHouseType().setText(item.houseType != null ? item.houseType : "");
            dialog.getTxtAddress().setText(item.address != null ? item.address : "");
            dialog.getSaveButton().addActionListener(e -> {
                try {
                    TransactionDTO updatedDto = dialog.getUpdatedDTO();
                    updateUseCase.updateTransaction(item.transactionId, updatedDto);
                    JOptionPane.showMessageDialog(view, "Cập nhật giao dịch thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(view, ex.getMessage(), "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(view, "Lỗi khi cập nhật giao dịch: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            });
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một giao dịch để sửa!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void searchTransaction(String keyword) {
        try {
            System.out.println("Tìm kiếm với từ khóa: '" + keyword + "'");
            if (keyword == null || keyword.trim().isEmpty()) {
                execute();
            } else {
                transactionViewModel.transactionList = searchUseCase.search(keyword.trim());
                view.showList(transactionViewModel);
                System.out.println("Tìm kiếm hoàn tất, số kết quả: " + (transactionViewModel.transactionList != null ? transactionViewModel.transactionList.size() : 0));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Lỗi khi tìm kiếm giao dịch: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}