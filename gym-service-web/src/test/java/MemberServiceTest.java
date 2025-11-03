import com.clz.GYMServiceWebApplication;
import com.clz.web.member.entity.RechargeParam;
import com.clz.web.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

@SpringBootTest(classes = GYMServiceWebApplication.class)
public class MemberServiceTest {
    @Resource
    MemberService memberService;

    @Test
    public void test(){
        memberService.recharge(new RechargeParam(1L,1L,new BigDecimal("100.00")));
    }

    @Test
    public void test1(){
        Date date = new Date();
        System.out.println(date);
    }


}
