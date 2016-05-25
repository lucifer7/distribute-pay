package distribute.pay.common.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/5/24
 * Time: 10:29
 **/
//@Data
public class AccountExchange {
    //TODO: add concurrency check ?
    public static Map<String, AccountExchange> exchangeTransLog = new HashMap<>();

    private String transUuid;
    private String sourceUuid;
    private String destUuid;
    private String action;
    private float amount;

    public AccountExchange() {
    }

    public String getTransUuid() {
        return transUuid;
    }

    public void setTransUuid(String transUuid) {
        this.transUuid = transUuid;
    }

    public String getSourceUuid() {
        return sourceUuid;
    }

    public void setSourceUuid(String sourceUuid) {
        this.sourceUuid = sourceUuid;
    }

    public String getDestUuid() {
        return destUuid;
    }

    public void setDestUuid(String destUuid) {
        this.destUuid = destUuid;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "AccountExchange{" +
                "transUuid='" + transUuid + '\'' +
                ", sourceUuid='" + sourceUuid + '\'' +
                ", destUuid='" + destUuid + '\'' +
                ", action='" + action + '\'' +
                ", amount=" + amount +
                '}';
    }
}
