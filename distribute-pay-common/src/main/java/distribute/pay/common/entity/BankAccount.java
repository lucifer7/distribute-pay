package distribute.pay.provider.common.entity;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/5/20
 * Time: 17:10
 **/
//@Data
public class BankAccount {
    private String uuid;
    private String username;
    private String currency;
    private float balance;
    private String action;
    private float adjust;

    public BankAccount() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public float getAdjust() {
        return adjust;
    }

    public void setAdjust(float adjust) {
        this.adjust = adjust;
    }
}
