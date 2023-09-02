package dto;

import dto.TransactionStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 分支事务
 */
@Getter
@Setter
@ToString
public class WenllarBatchTransaction implements java.io.Serializable{

    /**
     * group ID 全局事务ID
     */
    String groupId;

    /**
     * 分支事务ID
     */
    String transactionId;
    /**
     * 分支事务数量
     */
    int transactionCounts;

    /**
     * 存在分支事务的服务数量
     *
     */
    Integer batchTransactionServiceCount;
    /**
     * 分支事务的状态
     */
    TransactionStatus transactionStatus;


    private transient Object lock;
    public WenllarBatchTransaction(){

    }
    public WenllarBatchTransaction(String groupId, String transactionId){
        this.groupId = groupId;
        this.transactionId = transactionId;
        this.lock = new Object();
    }

    public WenllarBatchTransaction(String groupId, String transactionId, TransactionStatus transactionStatus){
        this.groupId = groupId;
        this.transactionId = transactionId;
        this.transactionStatus = transactionStatus;
        this.lock = new Object();
    }
}
