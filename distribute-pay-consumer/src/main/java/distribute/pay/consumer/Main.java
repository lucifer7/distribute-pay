package distribute.pay.consumer;

import distribute.pay.consumer.rocketmq.EmailReceiver;
import distribute.pay.consumer.rocketmq.PushConsumer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/5/20
 * Time: 12:47
 **/
public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
        ctx.start();
        //PushConsumer consumer = new PushConsumer();
        EmailReceiver receiver = new EmailReceiver();
    }

}
