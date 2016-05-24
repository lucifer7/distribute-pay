package distribute.pay.common.entity;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/5/24
 * Time: 10:29
 **/
//@Data
public class AccountExchange {
    private String transUuid;
    private String sourceUuid;
    private String destUuid;
    private String sourceAction;
    private String destAction;
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

    public String getSourceAction() {
        return sourceAction;
    }

    public void setSourceAction(String sourceAction) {
        this.sourceAction = sourceAction;
    }

    public String getDestAction() {
        return destAction;
    }

    public void setDestAction(String destAction) {
        this.destAction = destAction;
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
                ", sourceAction='" + sourceAction + '\'' +
                ", destAction='" + destAction + '\'' +
                ", amount=" + amount +
                '}';
    }
}
