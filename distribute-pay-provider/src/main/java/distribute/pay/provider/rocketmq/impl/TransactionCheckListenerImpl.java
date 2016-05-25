/**
 * Copyright (C) 2010-2013 Alibaba Group Holding Limited
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package distribute.pay.provider.rocketmq.impl;

import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.client.producer.TransactionCheckListener;
import com.alibaba.rocketmq.common.message.MessageExt;
import distribute.pay.common.entity.AccountExchange;
import distribute.pay.common.util.FastJsonConvert;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 未决事务，服务器回查客户端
 */
public class TransactionCheckListenerImpl implements TransactionCheckListener {
    private static Logger log = LoggerFactory.getLogger(TransactionCheckListenerImpl.class);

    private AtomicInteger transactionIndex = new AtomicInteger(0);

    public LocalTransactionState checkLocalTransactionState(MessageExt msg) {
        System.out.println("server checking TrMsg " + msg.toString());

        AccountExchange accountExchange = _getAccountExchange(msg);
        if(null == accountExchange) {
            log.error("Invalid AccountExchange object.");
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }

        if(_checkTransExecution(accountExchange)) {
            return LocalTransactionState.COMMIT_MESSAGE;
        } else {
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
    }

    private AccountExchange _getAccountExchange(MessageExt msg) {
        return FastJsonConvert.convertJSONToObject(new String(msg.getBody(), Charset.defaultCharset()), AccountExchange.class);
    }

    private boolean _checkTransExecution(AccountExchange accountExchange) {
        String transUuid = accountExchange.getTransUuid();
        if(_checkTransResultByUuid(transUuid)) {
            return true;
        }
        return false;
    }

    private boolean _checkTransResultByUuid(String transUuid) {
        //todo: check trans impl
        return true;
    }
}
