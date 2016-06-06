package distribute.pay.consumer.rocketmq.impl;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerOrderly;
import com.alibaba.rocketmq.common.message.MessageExt;
import lombok.extern.log4j.Log4j;

import java.util.List;

import static distribute.pay.consumer.rocketmq.util.MessagePrinter.printMsgLog;

/**
 * Created by yangjingyi on 2016-6-6.
 */
@Log4j
public class OrderMessageListener implements MessageListenerOrderly {
    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
        consumeOrderlyContext.setAutoCommit(true);
        log.info(Thread.currentThread().getName() + " Receives a message list.");
        for (MessageExt msgExt: list) {
            printMsgLog(msgExt);
        }
        return ConsumeOrderlyStatus.SUCCESS;
        //return null;  // caused dead loop
    }
}
