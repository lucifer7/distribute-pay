package common.entity;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/5/20
 * Time: 17:10
 **/
@Data
public class BankAccount {
    private String uuid;
    private String username;
    private String currency;
    private float balance;

    public BankAccount() {
    }


}
