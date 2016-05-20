package distribute.pay.consumer.rocketmq;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import distribute.pay.consumer.rocketmq.impl.PushMessageListener;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/5/20
 * Time: 16:44
 **/
public class PushConsumer {
    private static final String TOPIC = "BANK_ACCOUNT_EXCHANGE";
    private static final String SUB_EXPRESSION = "*";

    private DefaultMQPushConsumer consumer;

    // 幂等/去重
    // 批量处理，throughOut
    public PushConsumer() {
        //this.consumer
        this.consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        //this.consumer.setSubscription();
        try {
            PushMessageListener msgListener = new PushMessageListener();
            this.consumer.subscribe(TOPIC, SUB_EXPRESSION);
            this.consumer.setMessageListener(msgListener);
            this.consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }
}
