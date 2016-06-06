package distribute.pay.consumer.rocketmq;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import distribute.pay.common.util.ProjectConstants;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yangjingyi on 2016-6-6.
 */
@Log4j
@Component
public class OrderedConsumer {
    private DefaultMQPushConsumer orderConsumer;

    @Autowired
    public OrderedConsumer(DefaultMQPushConsumer orderConsumer) {
        this.orderConsumer = orderConsumer;
        this.orderConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        try {
            this.orderConsumer.subscribe(ProjectConstants.TOPIC, "*");
            this.orderConsumer.start();
            log.info("Push-Order-Consumer start---");
        } catch (MQClientException e) {
            log.error("Consumer start failed....");
            e.printStackTrace();
        }
    }

    /*@Autowired
    private PushOrderConsumer(DefaultMQPushConsumer orderConsumer) {
        this.orderConsumer = orderConsumer;
        this.orderConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        try {
            this.orderConsumer.subscribe(ProjectConstants.TOPIC, "*");
            this.orderConsumer.start();
            log.info("Push-Order-Consumer start---");
        } catch (MQClientException e) {
            log.error("Consumer start failed....");
            e.printStackTrace();
        }
    }

    public static PushOrderConsumer newInstance() {
        return new PushOrderConsumer();
    }*/
}
