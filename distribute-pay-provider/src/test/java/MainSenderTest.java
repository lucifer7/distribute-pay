import distribute.pay.provider.rocketmq.EmailSender;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/5/20
 * Time: 14:13
 **/
public class MainSenderTest extends AbstractTest{
    @Autowired
    private EmailSender emailSender;

    @Test
    public void sendMsg() {
        //Topic: 一级消息类型
        //Tag  : 二级消息类型（nullable）

        emailSender.sendMsg("JTest", "river garden", "");
    }
}
