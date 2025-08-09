package presentation;

import business.TransactionUpdateUseCase;
import persistence.TransactionDTO;
import persistence.TransactionGateway;

import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.JOptionPane;

public class TransactionUpdateController {
    private TransactionUpdateUseCase updateUseCase;
    private TransactionListViewUI view;
private TransactionListViewController listController; // Thêm tham chiếu đến listControlle
    public TransactionUpdateController(TransactionGateway gateway, TransactionListViewUI view, TransactionListViewController listController) {
        this.updateUseCase = new TransactionUpdateUseCase(gateway);
        this.view = view;
        this.listController = listController; // Gán tham chiếu
        this.view.setUpdateController(this);
    }

    public void editTransaction(int rowIndex) {
        TransactionViewModel model = view.getViewModel();
        if (rowIndex >= 0 && model != null && model.transactionList != null && rowIndex < model.transactionList.size()) {
            TransactionViewItem item = model.transactionList.get(rowIndex);
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
                    JOptionPane.showMessageDialog(view, "Cập nhật giao dịch thành công!", "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                            // Làm mới danh sách
                    if (listController != null) {
                        try {
                            listController.execute();
                        } catch (ParseException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        } // Tải lại dữ liệu từ cơ sở dữ liệu
                    }
                    dialog.dispose();
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(view, ex.getMessage(), "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(view, "Lỗi khi cập nhật giao dịch: " + ex.getMessage(), "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            });
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một giao dịch để sửa!", "Lỗi",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}