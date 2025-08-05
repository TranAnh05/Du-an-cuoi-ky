package business;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import persistence.TransactionListViewDAO;
import persistence.TransactionDTO;
import presentation.Publisher;
import presentation.TransactionViewItem;
import presentation.TransactionViewModel;
import persistence.TransactionGateway;
import business.entity.HouseTransaction;
import business.entity.LandTransaction;
import business.entity.Transaction;

public class TransactionListViewUseCase extends Publisher {
    private TransactionListViewDAO listViewDAO;
    private TransactionFactory factory;
    private TransactionGateway DAOGateway;

    public TransactionListViewUseCase(TransactionGateway DAOGateway) {
        // super();
        this.DAOGateway = DAOGateway;
    }

    public TransactionListViewUseCase(TransactionListViewDAO listViewDAO, TransactionFactory factory) {
        this.listViewDAO = listViewDAO;
        this.factory = factory;
        this.DAOGateway = listViewDAO;
    }

    public List<TransactionViewItem> executeQuery() throws SQLException {
        List<TransactionDTO> listDTO = listViewDAO.getAll();
        List<TransactionViewItem> result = convertToTransactionViewItem(listDTO);
        TransactionViewModel model = new TransactionViewModel();
        model.transactionList = result;
        notifySubscribers(model);
        return result;
    }

    public List<TransactionViewDTO> execute() throws SQLException, ParseException {
        List<TransactionDTO> listDTO = null;
        List<Transaction> transactions = null;

        listDTO = DAOGateway.getAll();

        transactions = convertToBusinessObjects(listDTO);

        return convertToTransactionViewDTO(transactions);
    }

    private List<TransactionViewItem> convertToTransactionViewItem(List<TransactionDTO> listDTO) {
        List<TransactionViewItem> itemList = new ArrayList<>();
        int stt = 1;
        DecimalFormat df = new DecimalFormat("#,###.##");
        for (TransactionDTO dto : listDTO) {
            Transaction transaction = factory.createTransaction(dto);
            TransactionViewItem item = new TransactionViewItem();
            item.stt = stt++;
            item.transactionId = transaction.getTransactionId();
            item.transactionDate = transaction.getTransactionDate().toString();
            item.unitPrice = df.format(transaction.getUnitPrice());
            item.area = df.format(transaction.getArea());
            item.transactionType = transaction.getTransactionType();
            item.amountTotal = df.format(transaction.calculateAmount());
            item.landType = transaction instanceof LandTransaction ? ((LandTransaction) transaction).getLandType() : "";
            item.houseType = transaction instanceof HouseTransaction ? ((HouseTransaction) transaction).getHouseType()
                    : "";
            item.address = dto.address != null ? dto.address : ""; // Lấy address trực tiếp từ DTO
            itemList.add(item);
        }
        return itemList;
    }

    private List<Transaction> convertToBusinessObjects(List<TransactionDTO> listDTO) {
        List<Transaction> transactions = new ArrayList<>();

        for (TransactionDTO dto : listDTO) {
            Transaction transaction = TransactionFactory.createTransaction(dto);
            transactions.add(transaction);
        }

        return transactions;
    }

    private List<TransactionViewDTO> convertToTransactionViewDTO(List<Transaction> transactions) {
        List<TransactionViewDTO> itemList = new ArrayList<TransactionViewDTO>();

        for (Transaction transaction : transactions) {
            TransactionViewDTO item = new TransactionViewDTO();

            item.transactionId = transaction.getTransactionId();
            item.transactionDate = transaction.getTransactionDate();
            item.unitPrice = transaction.getUnitPrice();
            item.area = transaction.getArea();
            item.transactionType = transaction.getTransactionType();
            item.amountTotal = transaction.calculateAmount();
            itemList.add(item);
        }

        return itemList;
    }
}