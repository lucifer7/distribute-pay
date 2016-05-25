package distribute.pay.common.entity;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/5/20
 * Time: 17:10
 **/
//@Data
public class BankAccount {
    private static Logger log = LoggerFactory.getLogger(BankAccount.class);
    public static final Map<String, BankAccount> accountMap = new HashMap<>();

    private String uuid;
    private String username;
    private String currency;
    private float balance;

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

    @Override
    public String toString() {
        return "BankAccount{" +
                "uuid='" + uuid + '\'' +
                ", username='" + username + '\'' +
                ", currency='" + currency + '\'' +
                ", balance=" + balance +
                '}';
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
