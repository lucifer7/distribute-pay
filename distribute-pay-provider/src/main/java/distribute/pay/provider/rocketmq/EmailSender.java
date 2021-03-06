package distribute.pay.provider.rocketmq;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.google.common.base.Throwables;
import distribute.pay.common.util.ProjectConstants;
import org.springframework.stereotype.Component;

/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 *
 * @author carl.yu
 * @since 2016/5/19
 */
//@Component
    @Deprecated
public class EmailSender {

    //Group name
    private final String GROUP_NAME = "email-sender";
    //
    private final String NAMESRV_ADDR = ProjectConstants.NAMESRV_ADDR;

    private final DefaultMQProducer producer;


    public EmailSender() {
        this.producer = new DefaultMQProducer(GROUP_NAME);
        this.producer.setNamesrvAddr(NAMESRV_ADDR);
        try {
            this.producer.start();
            System.out.println("provider start");


            for (int i = 0; i < 10; i++) {
                try {
                    Message msg = new Message(ProjectConstants.TOPIC,// topic
                            ProjectConstants.ACTION,// tag
                            ("Hello RocketMQ " + i).getBytes()// body
                    );
                    SendResult sendResult = this.producer.send(msg);
                    System.out.println(sendResult);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (MQClientException e) {
            Throwables.propagateIfPossible(e);
        }
    }

    public SendResult sendMsg(String topic, String content, String tags) {
        Message msg = new Message(topic, tags, content.getBytes());
        SendResult result = null;
        try {
            result = this.producer.send(msg);
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void shutdown() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                producer.shutdown();
            }
        }));
        System.exit(0);
    }


}
