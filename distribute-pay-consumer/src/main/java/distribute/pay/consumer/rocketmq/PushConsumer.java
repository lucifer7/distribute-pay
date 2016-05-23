package distribute.pay.consumer.rocketmq;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import distribute.pay.consumer.rocketmq.impl.PushMessageListener;
import distribute.pay.provider.common.util.ProjectConstants;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/5/20
 * Time: 16:44
 **/
@Component
public class PushConsumer {
    private final String GROUP_NAME = ProjectConstants.CONSUMER_GROUP;
    private final String NAMESRV_ADDR = ProjectConstants.NAMESRV_ADDR;
    private DefaultMQPushConsumer consumer;

    public PushConsumer() {
        try {
            this.consumer = new DefaultMQPushConsumer(GROUP_NAME);
            this.consumer.setNamesrvAddr(NAMESRV_ADDR);
            this.consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
            this.consumer.subscribe(ProjectConstants.TOPIC, ProjectConstants.CONSUMER_SUB_TAGS);
            this.consumer.registerMessageListener(new PushMessageListener());
            this.consumer.start();
            System.out.println("consumer start");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
