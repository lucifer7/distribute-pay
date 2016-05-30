package distribute.pay.provider.rocketmq;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.TransactionMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import distribute.pay.provider.rocketmq.impl.TransactionExecuterImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <b>转账事务处理 - 生产者</b>
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/5/20
 * Time: 14:57
 **/
@Component
public class TransactionProducer {
    private static Logger log = LoggerFactory.getLogger(TransactionProducer.class);

    private TransactionMQProducer producer;

    /*
     * 消息生产者初始化，TODO： 重构为工厂方法，私有化构造器
     */
    @Autowired
    public TransactionProducer(TransactionMQProducer producer) {
        this.producer = producer;
        try {
            this.producer.start();
            log.info("Trans producer start.");
        } catch (MQClientException e) {
            log.error("Transaction producer start error.", e);
        }
    }

    public SendResult sendMsg(Message msg) {
        SendResult sendResult = null;
        try {
            sendResult = this.producer.send(msg);
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return sendResult;
    }

    /*
     * 事务消息
     */
    public SendResult sendTransactionMsg(Message msg) {
        SendResult sendResult = null;
        TransactionExecuterImpl tranExecuter = new TransactionExecuterImpl();
        try {
            sendResult = this.producer.sendMessageInTransaction(msg, tranExecuter, null);
        } catch (MQClientException e) {
            //Throwables.propagateIfPossible(e);
            log.error("Transaction message send error.", e);
        }
        return sendResult;
    }

    /*
     * 顺序消息
     */
    public SendResult sendOrderedMsg(Message msg, Object uuid) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        SendResult sendResult = null;
        sendResult = this.producer.send(msg, new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> list, Message message, Object arg) {
                Integer id = (Integer) arg;
                int index = id % list.size();
                return list.get(index);
            }
        }, uuid);
        return sendResult;
    }

    public void shutdown() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                producer.shutdown();
            }
        }));
        producer.shutdown();
    }

}
