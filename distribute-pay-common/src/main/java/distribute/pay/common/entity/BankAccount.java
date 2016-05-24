package distribute.pay.common.entity;

import lombok.Data;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/5/20
 * Time: 17:10
 **/
//@Data
public class BankAccount {
    private static Logger log = LoggerFactory.getLogger(BankAccount.class);

    private String uuid;
    private String username;
    private String currency;
    private float balance;
    private String action;
    private float amount;

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

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public static BankAccount genAccountByUuid(String uuid) {
        BankAccount account = new BankAccount();
        account.setUuid(uuid);
        account.setUsername("USER" + RandomUtils.nextInt(0, 1000));
        account.setBalance(RandomUtils.nextFloat(0, 20000f));
        account.setCurrency("CNY");
        log.info("Get BankAccount by uuid: " + account.toString());
        return account;
    }

    public static BankAccount genRandomAccount() {
        String uuid = "KEY:" + System.currentTimeMillis();
        return genAccountByUuid(uuid);
    }
}