package distribute.pay.consumer.rocketmq;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import distribute.pay.consumer.common.util.ProjectConstants;
import distribute.pay.consumer.rocketmq.impl.PushMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
    /*private static final String TOPIC = "BANK_ACCOUNT_EXCHANGE123";
    private static final String SUB_EXPRESSION = "MONEY_OUT";

    private DefaultMQPushConsumer consumer;

    @Autowired
    public PushConsumer(DefaultMQPushConsumer consumer) {
        this.consumer = consumer;
        try {
            this.consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
            this.consumer.subscribe(TOPIC, SUB_EXPRESSION);
            this.consumer.start();
            System.out.println("Push consumer start");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }*/
/*
    public static void main(String[] args) throws MQClientException {
        //创建消费者Push对象
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("transaction-consumer");
        //设置NameSvr
        consumer.setNamesrvAddr(ProjectConstants.NAMESRV_ADDR);
        //订阅主题，并指明tags
        consumer.subscribe("BANK_ACCOUNT_EXCHANGE123", "*");
        //设置监听器对象
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                try {
                    for (MessageExt msg : msgs) {
                        String topic = msg.getTopic();
                        String msgBody = new String(msg.getBody(), "utf-8");
                        String tags = msg.getTags();
                        System.out.printf("收到消息：topic:%s,tags:%s,msg:%s",topic,tags,msgBody);
                        System.out.println();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //consumer.registerMessageListener(new PushMessageListener());

            //开始
        consumer.start();
    }*/
}
