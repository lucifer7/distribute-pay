package distribute.pay.consumer.rocketmq.impl;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import distribute.pay.common.entity.AccountExchange;
import distribute.pay.common.entity.BankAccount;
import distribute.pay.common.util.FastJsonConvert;
import distribute.pay.common.util.ProjectConstants;
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

    // 幂等/去重
    // 批量处理，throughOut
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        for (MessageExt msg : list) {
            try {
                _printMsgLog(msg);

                AccountExchange exchange = _getAccountExchange(msg);
                if (exchange == null) continue;

                if(_isExchangeTransDuplicate(exchange.getTransUuid())) {
                    continue;
                } else {
                    AccountExchange.exchangeTransLog.put(exchange.getTransUuid(), exchange);    //save exchange value
                }

                BankAccount destAccount = BankAccount.genAccountByUuid(exchange.getDestUuid());     //生成随机账户，不从数据库读取
                if(ProjectConstants.ACTION.equals(exchange.getAction())) {
                    destAccount.setBalance(destAccount.getBalance() + exchange.getAmount());
                }
                log.info(destAccount.getBalance() + " left in account.");
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
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    private void _printMsgLog(MessageExt msg) {
        String topic = msg.getTopic();
        String tags = msg.getTags();
        String keys = msg.getKeys();
        log.info("Receiving message, under topic: " + topic);
        log.info("With tag: " + tags);
        log.info("Identified by: " + keys);
        log.info(msg.toString());
    }

    private AccountExchange _getAccountExchange(MessageExt msg) {
        String content = new String(msg.getBody(), Charset.defaultCharset());
        AccountExchange exchange = FastJsonConvert.convertJSONToObject(content, AccountExchange.class);
        if (null == exchange) {
            log.info("Invalid account: " + content);
            return null;
        }

        log.info(exchange.toString());
        return exchange;
    }

    private boolean _isExchangeTransDuplicate(String transUuid) {
        AccountExchange accoutExchange = AccountExchange.exchangeTransLog.get(transUuid);   //retrieve exchange value
        if(null == accoutExchange) {
            return false;
        } else {
            log.warn("Duplicate account exchange transaction, under UUID: " + transUuid);
            return true;
        }
    }

}
