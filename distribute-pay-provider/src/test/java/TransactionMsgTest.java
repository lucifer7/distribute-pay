import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import distribute.pay.common.entity.AccountExchange;
import distribute.pay.common.entity.BankAccount;
import distribute.pay.common.util.FastJsonConvert;
import distribute.pay.common.util.ProjectConstants;
import distribute.pay.provider.rocketmq.TransactionProducer;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/5/20
 * Time: 16:17
 **/
public class TransactionMsgTest extends AbstractTest {
    @Autowired
    private TransactionProducer transProducer;
    //private TransactionProducer transProducer = new TransactionProducer();
    AccountExchange accountExchange;
    BankAccount account;

    @Before
    public void initData() {
        account = BankAccount.genRandomAccount();
        BankAccount.accountMap.put(account.getUuid(), account); //temp save BankAccount

        accountExchange = new AccountExchange();
        String uuid = "DEST_KEY:" + System.currentTimeMillis();
        String transUuid = "TRANS_KEY:" + System.currentTimeMillis();
        //String transUuid = "TRANS_KEY:1464165138409";      //test Duplicate trans key
        accountExchange.setAction(ProjectConstants.ACTION);
        accountExchange.setDestUuid(uuid);
        accountExchange.setTransUuid(transUuid);
        accountExchange.setSourceUuid(account.getUuid());
        accountExchange.setAmount(RandomUtils.nextFloat(0, account.getBalance()));
        log.info(accountExchange);
    }

    //@Test
    /*public void msgTest() {
        BankAccount account = BankAccount.genRandomAccount();
        BankAccount.accountMap.put(account.getUuid(), account);
        accountExchange.setSourceUuid(account.getUuid());
        accountExchange.setAmount(100f);
        Message msg = new Message(ProjectConstants.TOPIC, ProjectConstants.ACTION, account.getUuid(), FastJsonConvert.convertObjectToJSON(account).getBytes());
        SendResult sendResult = transProducer.sendMsg(msg);
        log.info(sendResult);
    }*/

    //@Test
    public void transMsgTest() {
        Message msg = new Message(ProjectConstants.TOPIC, ProjectConstants.ACTION, accountExchange.getTransUuid(),
                FastJsonConvert.convertObjectToJSON(accountExchange).getBytes());
        SendResult sendResult = transProducer.sendTransactionMsg(msg);
        log.info(sendResult);
    }

    @Test
    public void multiTransMsgTest() {
        for(int i = 0; i < 200; i++) {
            initData();
            transMsgTest();
        }
    }

    //@Test
    public void orderedMsgTest() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        Message msg = new Message(ProjectConstants.TOPIC, ProjectConstants.ACTION, accountExchange.getTransUuid(),
                FastJsonConvert.convertObjectToJSON(accountExchange).getBytes());
        SendResult sendResult = transProducer.sendOrderedMsg(msg, accountExchange.getSourceUuid());
        log.info(sendResult);
    }
}

