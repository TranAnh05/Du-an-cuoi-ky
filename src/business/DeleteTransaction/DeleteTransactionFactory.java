package business.DeleteTransaction;

import persistence.DeleteTransaction.DeleteTransactionDTO;

public class DeleteTransactionFactory {
    public static String makeDeletedMessage(DeleteTransactionDTO dto) {
        // why: message shown in logs/toasts for user confirmation
        return String.format("Đã xóa: %s (%s) - %.2f x %.2f", dto.getTransactionId(), dto.getTransactionType(), dto.getUnitPrice(), dto.getArea());
    }
}
