import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import distribute.pay.provider.common.entity.BankAccount;
import distribute.pay.provider.common.util.FastJsonConvert;
import distribute.pay.provider.rocketmq.TransactionProducer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static distribute.pay.provider.rocketmq.TransactionProducer.MessageConfig.OUT_TAG;
import static distribute.pay.provider.rocketmq.TransactionProducer.MessageConfig.TOPIC;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/5/20
 * Time: 16:17
 **/
public class TransactionMsgTest extends AbstractTest {
    //@Autowired
    private TransactionProducer transProducer = new TransactionProducer();

    //@Test
    public void transMsgTest() {
        String key = "KEY:" + System.currentTimeMillis();

        BankAccount account = new BankAccount();
        account.setUuid(key);
        account.setUsername("provider0");
        account.setCurrency("CNY");
        account.setBalance(3799f);
        account.setAction("SUB");
        account.setAdjust(99.9f);

        Message msg = new Message(TOPIC, OUT_TAG, key, FastJsonConvert.convertObjectToJSON(account).getBytes());
        SendResult sendResult = transProducer.sendTransactionMsg(msg);
        log.info(sendResult.toString());
    }
}

