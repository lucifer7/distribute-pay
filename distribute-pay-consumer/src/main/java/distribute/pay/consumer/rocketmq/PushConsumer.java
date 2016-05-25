package distribute.pay.consumer.rocketmq;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import distribute.pay.common.util.ProjectConstants;
import distribute.pay.consumer.rocketmq.impl.PushMessageListener;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static Logger log = LoggerFactory.getLogger(PushConsumer.class);

    private DefaultMQPushConsumer consumer;

    @Autowired
    public PushConsumer(DefaultMQPushConsumer consumer) {
        try {
            this.consumer = consumer;
            this.consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
            this.consumer.subscribe(ProjectConstants.TOPIC, ProjectConstants.ACTION);
            this.consumer.start();
            System.out.println("consumer start");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
