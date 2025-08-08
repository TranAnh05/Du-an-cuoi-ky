package presentation;

import java.sql.SQLException;
import javax.swing.JOptionPane;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import business.TransactionListViewUseCase;
import business.TransactionSearchUseCase;
import business.TransactionUpdateUseCase;
import business.TransactionViewDTO;
import persistence.TransactionDTO;

public class TransactionListViewController {
    private TransactionListViewUI view;
    private TransactionViewModel transactionViewModel;
    private TransactionListViewUseCase usecase;
    private TransactionSearchUseCase searchUseCase;
    private TransactionUpdateUseCase updateUseCase;

    public TransactionListViewController(TransactionViewModel transactionViewModel,
            TransactionListViewUseCase usecase) {
        this.transactionViewModel = transactionViewModel;
        this.usecase = usecase;
    }

    public TransactionListViewController(TransactionListViewUI view, TransactionViewModel transactionViewModel,
            TransactionListViewUseCase listViewUseCase, TransactionSearchUseCase searchUseCase,
            TransactionUpdateUseCase updateUseCase) {
        this.view = view;
        this.transactionViewModel = transactionViewModel;
        this.usecase = listViewUseCase;
        this.searchUseCase = searchUseCase;
        this.updateUseCase = updateUseCase;
        view.setController(this);
        usecase.addSubscriber((Subscriber) view);
        searchUseCase.addSubscriber((Subscriber) view);
        updateUseCase.addSubscriber((Subscriber) view);
    }

    public void execute() throws SQLException, ParseException {
        List<TransactionViewDTO> listDTO = usecase.execute();
        List<TransactionViewItem> listViewItem = convertDTODataToItemData(listDTO);
        transactionViewModel.transactionList = listViewItem;
        transactionViewModel.notifySubscribers();
        transactionViewModel.transactionList = usecase.executeQuery();
        view.showList(transactionViewModel);

    }

    private List<TransactionViewItem> convertDTODataToItemData(List<TransactionViewDTO> listDTO) {
        List<TransactionViewItem> listItem = new ArrayList<TransactionViewItem>();

        int stt = 1;
        for (TransactionViewDTO dto : listDTO) {
            TransactionViewItem item = new TransactionViewItem();
            NumberFormat amountFormatter = new DecimalFormat("#,##0.###");

            item.stt = stt++;
            item.transactionId = dto.transactionId;
            item.transactionDate = dto.transactionDate.toString();
            item.unitPrice = String.valueOf(dto.unitPrice);
            item.area = String.valueOf(dto.area);
            item.transactionType = dto.transactionType;
            item.amountTotal = amountFormatter.format(dto.amountTotal);
            listItem.add(item);
        }

        return listItem;
    }

    public void editTransaction(int rowIndex) {
        if (rowIndex >= 0 && transactionViewModel.transactionList != null
                && rowIndex < transactionViewModel.transactionList.size()) {
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
                    JOptionPane.showMessageDialog(view, "Cập nhật giao dịch thành công!", "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
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

    public void searchTransaction(String keyword) throws ParseException {
        try {
            System.out.println("Tìm kiếm với từ khóa: '" + keyword + "'");
            if (keyword == null || keyword.trim().isEmpty()) {
                execute();
            } else {
                transactionViewModel.transactionList = searchUseCase.search(keyword.trim());
                view.showList(transactionViewModel);
                System.out.println("Tìm kiếm hoàn tất, số kết quả: "
                        + (transactionViewModel.transactionList != null ? transactionViewModel.transactionList.size()
                                : 0));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Lỗi khi tìm kiếm giao dịch: " + e.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}