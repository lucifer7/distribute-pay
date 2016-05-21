package distribute.pay.consumer.rocketmq;


import com.alibaba.rocketmq.client.QueryResult;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 *
 * @author carl.yu
 * @since 2016/5/19
 */
@Component
public class EmailReceiver {
    private final String GROUP_NAME = "transaction-balance";
    private final String NAMESRV_ADDR = "10.200.157.81:9876";
    private DefaultMQPushConsumer consumer;

    public EmailReceiver() {
        try {
            this.consumer = new DefaultMQPushConsumer(GROUP_NAME);
            this.consumer.setNamesrvAddr(NAMESRV_ADDR);
            this.consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
            this.consumer.subscribe("email", "*");
            this.consumer.registerMessageListener(new Listener());
            this.consumer.start();
            System.out.println("consumer start");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public QueryResult queryMessage(String topic, String key, int maxNum, long begin, long end) throws Exception {
        long current = System.currentTimeMillis();
        return this.consumer.queryMessage(topic, key, maxNum, begin, end);
    }

    class Listener implements MessageListenerConcurrently {
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            MessageExt msg = msgs.get(0);
            try {
                String topic = msg.getTopic();
                //Message Body
                String tags = msg.getTags();
                String keys = msg.getKeys();

                //TODO: add process code here
                //Mail mail = FastJsonConvert.convertJSONToObject(new String(msg.getBody(), "utf-8"), Mail.class);
                //System.out.println(ToStringBuilder.reflectionToString(mail));
//                mailService.mailSend(mail);
            } catch (Exception e) {
                e.printStackTrace();
                //重试次数为3情况
                if (msg.getReconsumeTimes() == 3) {
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    //记录日志
                }
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }


}