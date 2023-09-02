package api;

import dto.WenllarBatchTransaction;

public interface TransactionRegistService {
    String registGlobalTransaction(String groupId);
    String registBatchTransaction(WenllarBatchTransaction batchTransaction, boolean isEnd);
}
