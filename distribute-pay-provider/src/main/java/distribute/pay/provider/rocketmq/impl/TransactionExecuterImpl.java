package distribute.pay.provider.rocketmq.impl;

import com.alibaba.rocketmq.client.producer.LocalTransactionExecuter;
import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.common.message.Message;
import distribute.pay.common.entity.AccountExchange;
import distribute.pay.common.entity.BankAccount;
import distribute.pay.common.util.FastJsonConvert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/5/20
 * Time: 16:11
 **/
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TransactionExecuterImpl implements LocalTransactionExecuter {
    private static Logger log = LoggerFactory.getLogger(TransactionExecuterImpl.class);
    private AtomicInteger transactionIndex = new AtomicInteger();

    public LocalTransactionState executeLocalTransactionBranch(Message msg, Object arg) {
        log.info(msg.toString());

        AccountExchange accountExchange = _getAccountExchange(msg);
        if(null == accountExchange) {
            log.error("Invalid AccountExchange object.");
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }

        if(_executeTransaction(accountExchange)) {
            return LocalTransactionState.COMMIT_MESSAGE;
        } else {
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
    }

    private AccountExchange _getAccountExchange(Message msg) {
        return FastJsonConvert.convertJSONToObject(new String(msg.getBody(), Charset.defaultCharset()), AccountExchange.class);
    }

    private boolean _executeTransaction(AccountExchange accountExchange) {
        String sourceUuid = accountExchange.getSourceUuid();
        BankAccount sourceAccount = BankAccount.accountMap.get(sourceUuid);
        if("TRANSFER".equals(accountExchange.getSourceAction())) {
            sourceAccount.setBalance(sourceAccount.getBalance() - accountExchange.getAmount());
            _updateBankAccount(sourceAccount);
            return true;
        }
        return false;
    }

    private void _updateBankAccount(BankAccount sourceAccount) {
        log.info("Saving BankAccount: " + sourceAccount.toString());
    }

}
