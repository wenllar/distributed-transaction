package dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TransactionResult implements java.io.Serializable{
    private String groupId;
    private TransactionStatus transactionStatus;

}
