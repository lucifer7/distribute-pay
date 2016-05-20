package distribute.pay.provider.rocketmq;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.*;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.namesrv.NamesrvConfig;
import com.google.common.base.Throwables;
import distribute.pay.provider.rocketmq.impl.TransactionCheckListenerImpl;
import distribute.pay.provider.rocketmq.impl.TransactionExecuterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * <b>转账事务处理 - 生产者</b>
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/5/20
 * Time: 14:57
 **/
public class TransactionProducer {
    private TransactionMQProducer producer;

    @Autowired
    public TransactionProducer(TransactionMQProducer producer) {
        this.producer = producer;
        try {
            this.producer.start();
        } catch (MQClientException e) {
            Throwables.propagateIfPossible(e);
        }
    }

    public SendResult sendTransactionMsg(Message msg) {
        SendResult sendResult = null;
        TransactionExecuterImpl tranExecuter = new TransactionExecuterImpl();
        try {
            sendResult = this.producer.sendMessageInTransaction(msg, tranExecuter, null);
        } catch (MQClientException e) {
            Throwables.propagateIfPossible(e);
        }
        return sendResult;
    }

    public void shutdown() {
        //Runtime.getRuntime().addShutdownHook(new Thread((Runnable) () -> {producer.shutdown();}));
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                producer.shutdown();
            }
        }));
        producer.shutdown();
    }

    public class MessageConfig {
        public static final String TOPIC = "BANK_ACCOUNT_EXCHANGE";
        public static final String IN_TAGS = "MONEY_IN";
        public static final String OUT_TAG = "MONEY_OUT";
    }

}
