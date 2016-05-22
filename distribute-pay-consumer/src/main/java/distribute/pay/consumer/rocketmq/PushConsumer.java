package distribute.pay.consumer.rocketmq;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import distribute.pay.consumer.rocketmq.impl.PushMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/5/20
 * Time: 16:44
 **/
@Component
public class PushConsumer {
    private static final String TOPIC = "BANK_ACCOUNT_EXCHANGE";
    private static final String SUB_EXPRESSION = "*";

    private final String GROUP_NAME = "transaction-balance";
    private final String NAMESRV_ADDR = "192.168.1.16:9876;192.168.1.17:9876";

    //@Autowired
    private DefaultMQPushConsumer consumer;

    // 幂等/去重
    // 批量处理，throughOut
    public PushConsumer() {
        //this.consumer = consumer;
        //this.consumer.setSubscription();
        try {
            this.consumer = new DefaultMQPushConsumer(GROUP_NAME);
            this.consumer.setNamesrvAddr(NAMESRV_ADDR);
            this.consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
            PushMessageListener msgListener = new PushMessageListener();
            this.consumer.subscribe(TOPIC, SUB_EXPRESSION);
            this.consumer.setMessageListener(msgListener);
            this.consumer.start();
            System.out.println("Push consumer start");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    /*public PushConsumer(DefaultMQPushConsumer consumer) {
        this.consumer = consumer;
        this.consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        //this.consumer.setSubscription();
        try {
            PushMessageListener msgListener = new PushMessageListener();
            this.consumer.subscribe(TOPIC, SUB_EXPRESSION);
            this.consumer.setMessageListener(msgListener);
            this.consumer.start();
            System.out.println("Push consumer start");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }*/
}
