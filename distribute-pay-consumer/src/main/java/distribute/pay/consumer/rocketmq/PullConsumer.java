package distribute.pay.consumer.rocketmq;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/5/23
 * Time: 13:39
 **/

import com.alibaba.rocketmq.client.consumer.DefaultMQPullConsumer;
import com.alibaba.rocketmq.client.consumer.PullResult;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;
import distribute.pay.consumer.common.util.ProjectConstants;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class PullConsumer {

    private static final String NAMESRV_ADDR = ProjectConstants.NAMESRV_ADDR;
    private static final Map<MessageQueue, Long> offseTable = new HashMap<MessageQueue, Long>();


    public static void main(String[] args) throws MQClientException {
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("transaction-consumer");

        consumer.setNamesrvAddr(NAMESRV_ADDR);
        consumer.start();

        for (int i=0; i<2; i++) {
            Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues("BANK_ACCOUNT_EXCHANGE123");
            for (MessageQueue mq : mqs) {
                System.out.println("Consume from the queue: " + mq);
                SINGLE_MQ:
                while (true) {
                    try {
                        PullResult pullResult =
                                consumer.pullBlockIfNotFound(mq, null, getMessageQueueOffset(mq), 320);
                        System.out.println(pullResult);
                        putMessageQueueOffset(mq, pullResult.getNextBeginOffset());
                        switch (pullResult.getPullStatus()) {
                            case FOUND:
                                // TODO
                                for (Message msg : pullResult.getMsgFoundList()) {
                                    System.out.println(msg.toString());
                                }
                                ;
                                break;
                            case NO_MATCHED_MSG:
                                break;
                            case NO_NEW_MSG:
                                break SINGLE_MQ;
                            case OFFSET_ILLEGAL:
                                break;
                            default:
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        consumer.shutdown();
    }


    private static void putMessageQueueOffset(MessageQueue mq, long offset) {
        offseTable.put(mq, offset);
    }


    private static long getMessageQueueOffset(MessageQueue mq) {
        Long offset = offseTable.get(mq);
        if (offset != null)
            return offset;

        return 0;
    }
}
