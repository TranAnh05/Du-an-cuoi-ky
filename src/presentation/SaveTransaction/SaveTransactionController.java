// package presentation.SaveTransaction;

// import java.sql.SQLException;
// import java.text.ParseException;
// import java.time.LocalDate;
// import java.util.ArrayList;
// import javax.swing.JOptionPane;
// import business.SaveTransaction.SaveTransactionUsecase;
// import presentation.TransactionListView.TransactionListViewController;

// public class SaveTransactionController {
//     private SaveTransactionModel model;
//     private SaveTransactionUsecase useCase;
//     private TransactionListViewController listViewController;

//     public SaveTransactionController(SaveTransactionModel model, SaveTransactionUsecase useCase, TransactionListViewController listViewController) {
//         this.model = model;
//         this.useCase = useCase;
//         this.listViewController = listViewController;
//     }

//     public boolean execute(String transactionId, String transactionDate, double unitPrice, double area, String transactionType, String landType, String houseType, String address) throws SQLException {
//         LocalDate parsedDate;
//         parsedDate = LocalDate.parse(transactionDate); // Attempt to parse the date

//         boolean success = useCase.execute(transactionId, transactionDate, unitPrice, area, transactionType, landType, houseType, address);
//         if (success) {
//             SaveTransactionItem item = new SaveTransactionItem();
//             item.transactionId = transactionId;
//             item.transactionDate = parsedDate; // Use the parsed date
//             item.unitPrice = unitPrice;
//             item.area = area;
//             item.transactionType = transactionType;
//             item.landType = landType;
//             item.houseType = houseType;
//             item.address = address;
//             model.transactionItems = new ArrayList<>();
//             model.transactionItems.add(item);
//             model.notifySubscribers();
//             if (listViewController != null) {
//                 try {
//                     listViewController.execute();
//                 } catch (SQLException e) {
//                     e.printStackTrace();
//                 } catch (ParseException e) {

//                     e.printStackTrace();
//                 } // Refresh full list
//             }
//         }
//         return success;
//     }
// }