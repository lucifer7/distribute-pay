import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
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
    @Autowired
    private TransactionProducer transProducer;

    @Test
    public void transMsgTest() {
        String key = "KEY:" + System.currentTimeMillis();
        //TODO: generate uuid key
        //TODO: use bank account
        Message msg = new Message(TOPIC, OUT_TAG, key, "{}".getBytes());
        SendResult sendResult = transProducer.sendTransactionMsg(msg);
        log.info(sendResult.toString());
    }
}

