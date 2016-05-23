import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import distribute.pay.provider.common.entity.BankAccount;
import distribute.pay.provider.common.util.FastJsonConvert;
import distribute.pay.provider.common.util.ProjectConstants;
import distribute.pay.provider.rocketmq.TransactionProducer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/5/20
 * Time: 16:17
 **/
public class TransactionMsgTest extends AbstractTest {
    @Autowired
    private TransactionProducer transProducer;
    //private TransactionProducer transProducer = new TransactionProducer();

    @Test
    public void transMsgTest() {
        String key = "KEY:" + System.currentTimeMillis();

        BankAccount account = new BankAccount();
        account.setUuid(key);
        account.setUsername("provider0123");
        account.setCurrency("CNY");
        account.setBalance(3799.00f);
        account.setAction("SUB");
        account.setAdjust(99.90f);

        Message msg = new Message(ProjectConstants.TOPIC, ProjectConstants.OUT_TAG, key, FastJsonConvert.convertObjectToJSON(account).getBytes());
        SendResult sendResult = transProducer.sendTransactionMsg(msg);
        log.info(sendResult);
    }
}

