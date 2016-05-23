package distribute.pay.provider.common.util;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/5/23
 * Time: 17:22
 **/
public class ProjectConstants {
    public static final String TOPIC = "BANK_EXCHANGE";
    public static final String OUT_TAG = "MONEY_OUT";
    /* Dell profile */
    //public static final String NAMESRV_ADDR = "10.200.157.81:9876";
    /* Lenovo profile */
    public static final String NAMESRV_ADDR = "192.168.1.16:9876;192.168.1.17:9876";
    public static final String PRODUCER_GROUP = "transaction-producer";
    public static final String CONSUMER_GROUP = "transaction-consumer";
    public static final String CONSUMER_SUB_TAGS = "*";
}
