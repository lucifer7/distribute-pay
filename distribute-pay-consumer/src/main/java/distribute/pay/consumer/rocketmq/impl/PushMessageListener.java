package distribute.pay.consumer.rocketmq.impl;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import distribute.pay.consumer.common.entity.BankAccount;
import distribute.pay.consumer.common.util.FastJsonConvert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/5/20
 * Time: 18:11
 **/
public class PushMessageListener implements MessageListenerConcurrently {
    private static Logger log = LoggerFactory.getLogger(PushMessageListener.class);

    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        for (MessageExt msg : list) {
            try {
                String topic = msg.getTopic();
                String tags = msg.getTags();
                String keys = msg.getKeys();

                String content = new String(msg.getBody(), Charset.defaultCharset());
                BankAccount account = FastJsonConvert.convertJSONToObject(content, BankAccount.class);
                log.info("Receiving message, under topic: " + topic);
                log.info("With tag: " + tags);
                log.info("Identified by: " + keys);
                log.info(account.toString());

                if("SUB".equals(account.getAction())) {
                    account.setBalance(account.getBalance() - account.getAdjust());
                }
                log.info(account.getBalance() + " left in account.");
            } catch (Exception e) {
                e.printStackTrace();
                //重试次数为3情况
                if (msg.getReconsumeTimes() == 3) {
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    //记录日志
                }
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        }
        //TODO: return msg status
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
