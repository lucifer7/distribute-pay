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
@Data
public class AccountExchange {
    //TODO: add concurrency check ?
    public static Map<String, AccountExchange> exchangeTransLog = new HashMap<>();

    private String transUuid;
    private String sourceUuid;
    private String destUuid;
    private String action;
    private float amount;

}
