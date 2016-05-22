package distribute.pay.provider.rocketmq;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.*;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.namesrv.NamesrvConfig;
import com.google.common.base.Throwables;
import distribute.pay.provider.rocketmq.impl.TransactionCheckListenerImpl;
import distribute.pay.provider.rocketmq.impl.TransactionExecuterImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.InitBinder;

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
    private final String GROUP_NAME = "transaction-producer";
    //
    private final String NAMESRV_ADDR = "192.168.1.16:9876;192.168.1.17:9876";

    //@Autowired
    //public TransactionProducer(TransactionMQProducer producer) {
    public TransactionProducer() {
        this.producer = new TransactionMQProducer(GROUP_NAME);
        this.producer.setNamesrvAddr(NAMESRV_ADDR);
        TransactionCheckListener transactionCheckListener = new TransactionCheckListenerImpl();
        //this.producer = producer;
        try {
            producer.setTransactionCheckListener(transactionCheckListener);
            this.producer.start();
            log.info("Trans producer start.");
        } catch (MQClientException e) {
            Throwables.propagateIfPossible(e);
        }

        String[] tags = new String[] { "TagA", "TagB", "TagC", "TagD", "TagE" };
        TransactionExecuterImpl tranExecuter = new TransactionExecuterImpl();
        for (int i = 0; i < 100; i++) {
            try {
                Message msg =
                        new Message(MessageConfig.TOPIC, tags[i % tags.length], "KEY" + i,
                                ("Hello RocketMQ " + i).getBytes());
                SendResult sendResult = this.producer.sendMessageInTransaction(msg, tranExecuter, null);
                System.out.println(sendResult);

            }
            catch (MQClientException e) {
                e.printStackTrace();
            }
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
        //public static final String IN_TAGS = "MONEY_IN";
        public static final String OUT_TAG = "MONEY_OUT";
    }

}
/*

    Caused by: com.alibaba.rocketmq.client.exception.MQClientException: The producer service state not OK, CREATE_JUST*/
