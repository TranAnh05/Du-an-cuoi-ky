package business.TotalTransaction;

import business.TransactionListView.entity.HouseTransaction;
import business.TransactionListView.entity.LandTransaction;
import business.TransactionListView.entity.Transaction;
import persistence.TotalTransaction.TotalTransactioDTO;

public class TotalTransactionFactory
{
    public static Transaction createTransaction(TotalTransactioDTO dto) 
    {
        if("GDĐ".equalsIgnoreCase(dto.transactionType)) {
                 return new LandTransaction(
                    dto.transactionId, dto.transactionDate,
                    dto.unitPrice = dto.unitPrice,
                    dto.area = dto.area,
                    dto.landType
                );
            }
            else if("GDN".equalsIgnoreCase(dto.transactionType)) {
                 return new HouseTransaction(
                    dto.transactionId, dto.transactionDate,
                    dto.unitPrice = dto.unitPrice,
                    dto.area = dto.area,
                    dto.houseType,
                    dto.address
                );
            }

        return null;
    }
}
