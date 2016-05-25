package distribute.pay.consumer.rocketmq;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import distribute.pay.common.util.ProjectConstants;
import distribute.pay.consumer.rocketmq.impl.PushMessageListener;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final String GROUP_NAME = ProjectConstants.CONSUMER_GROUP;
    private final String NAMESRV_ADDR = ProjectConstants.NAMESRV_ADDR;
    private DefaultMQPushConsumer consumer;

    public PushConsumer() {
        try {
            this.consumer = new DefaultMQPushConsumer(GROUP_NAME);
            this.consumer.setNamesrvAddr(NAMESRV_ADDR);
            this.consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
            this.consumer.subscribe(ProjectConstants.TOPIC, ProjectConstants.ACTION);
            log.info(this.consumer.getSubscription().toString());
            //this.consumer.subscribe("BANK_EXCHANGE", "*");
            this.consumer.registerMessageListener(new PushMessageListener());
            this.consumer.start();
            System.out.println("consumer start");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
