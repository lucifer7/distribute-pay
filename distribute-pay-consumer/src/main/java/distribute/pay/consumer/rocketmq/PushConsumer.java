package distribute.pay.consumer.rocketmq;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/5/20
 * Time: 16:44
 **/
public class PushConsumer {
    private DefaultMQPushConsumer consumer;

    // 幂等/去重
    // 批量处理，throughOut
    public PushConsumer() {
        //this.consumer
    }
}
