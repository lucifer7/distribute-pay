package distribute.pay.consumer.rocketmq.util;

import com.alibaba.rocketmq.common.message.MessageExt;
import lombok.extern.log4j.Log4j;

/**
 * Created by yangjingyi on 2016-6-6.
 */
@Log4j
public class MessagePrinter {

    public static void printMsgLog(MessageExt msg) {
        String topic = msg.getTopic();
        String tags = msg.getTags();
        String keys = msg.getKeys();
        log.info("Receiving message, under topic: " + topic);
        log.info("With tag: " + tags);
        log.info("Identified by: " + keys);
        log.info(msg.toString());
    }
}
