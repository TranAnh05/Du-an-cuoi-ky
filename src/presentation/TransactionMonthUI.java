package presentation;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import business.TransactionMonthUseCase;
import business.TransactionViewDTO;

public class TransactionMonthUI {
    private JButton button;
    private TransactionListViewUI viewUI;
    private TransactionMonthSelectUI selectUI;
    private TransactionMonthUseCase usecase;
    private TransactionMonthShowUI monthShowUI;

    public TransactionMonthUI(TransactionListViewUI viewUI, TransactionMonthUseCase usecase) 
    {
        this.viewUI = viewUI;
        this.usecase = usecase;
    }

    public void execute() throws SQLException{
        button = viewUI.getBtnMonth();
        button.addActionListener(e -> {
            selectUI = new TransactionMonthSelectUI(viewUI);
            selectUI.setVisible(true);
            int month = selectUI.getSelectedMonth();
            int year = selectUI.getSelectedYear();

            if(month > 0 && year > 0) {
                try {
                    List<TransactionViewDTO> listViewDTO = usecase.execute(month, year);
                    if (listViewDTO == null || listViewDTO.isEmpty()) {
                        JOptionPane.showMessageDialog(viewUI,
                            "Không tìm thấy giao dịch nào trong tháng " + month + "/" + year,
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        monthShowUI = new TransactionMonthShowUI(viewUI, listViewDTO);
                        monthShowUI.setVisible(true);
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}
