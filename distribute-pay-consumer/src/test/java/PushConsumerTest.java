import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import distribute.pay.consumer.rocketmq.PushConsumer;
import org.junit.Test;

/**
 * Created by yangjingyi on 2016-5-21.
 */
public class PushConsumerTest extends AbstractTest {

    private PushConsumer pushConsumer;

    @Test
    public void testPushConsumer() {
        String key = "KEY:" + System.currentTimeMillis();
        //Message msg = new Message(TOPIC, OUT_TAG, key, "{}".getBytes());
        //SendResult sendResult = transProducer.sendTransactionMsg(msg);
        //log.info(sendResult.toString());
    }
}
