package dto;

import java.util.HashMap;
import java.util.Map;

public enum TransactionStatus implements java.io.Serializable{

    rollback("RollBack"),
    commit("Commit");

    static final Map<String, TransactionStatus> transactionStatusMap = new HashMap<>();
    private String value;
    TransactionStatus(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }




}
