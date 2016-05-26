import com.google.common.collect.Lists;
import distribute.pay.common.entity.BankAccount;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/5/26
 * Time: 16:17
 **/
public class ListTest extends AbstractTest {
    @Test
    public void indexOfTest() {
        List<BankAccount> list = new ArrayList<>();
        list.add(new BankAccount());

        System.out.println(list.indexOf(new BankAccount()));
        System.out.println(list.contains(new BankAccount()));
        System.out.println(list.remove(new BankAccount()));
    }
}
