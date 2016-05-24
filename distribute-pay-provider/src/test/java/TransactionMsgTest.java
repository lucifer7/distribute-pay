import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import distribute.pay.common.entity.BankAccount;
import distribute.pay.common.util.FastJsonConvert;
import distribute.pay.common.util.ProjectConstants;
import distribute.pay.provider.rocketmq.TransactionProducer;
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

    @Test
    public void msgTest() {
        BankAccount account = BankAccount.genRandomAccount();
        Message msg = new Message(ProjectConstants.TOPIC, ProjectConstants.ACTION, account.getUuid(), FastJsonConvert.convertObjectToJSON(account).getBytes());
        SendResult sendResult = transProducer.sendMsg(msg);
        log.info(sendResult);
    }

    @Test
    public void transMsgTest() {
        BankAccount account = BankAccount.genRandomAccount();
        Message msg = new Message(ProjectConstants.TOPIC, ProjectConstants.ACTION, account.getUuid(), FastJsonConvert.convertObjectToJSON(account).getBytes());
        SendResult sendResult = transProducer.sendTransactionMsg(msg);
        log.info(sendResult);
    }
}

