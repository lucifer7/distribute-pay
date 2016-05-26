package distribute.pay.provider.rocketmq;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.TransactionCheckListener;
import com.alibaba.rocketmq.client.producer.TransactionMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import distribute.pay.common.util.ProjectConstants;
import distribute.pay.provider.rocketmq.impl.TransactionCheckListenerImpl;
import distribute.pay.provider.rocketmq.impl.TransactionExecuterImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public void shutdown() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                producer.shutdown();
            }
        }));
        producer.shutdown();
    }
}