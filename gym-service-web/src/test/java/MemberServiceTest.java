import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clz.GYMServiceWebApplication;
import com.clz.web.member.entity.RechargeParam;
import com.clz.web.member.service.MemberService;
import com.clz.web.member_recharge.entity.MemberRecharge;
import com.clz.web.member_recharge.mapper.MemberRechargeMapper;
import com.clz.web.sys_menu.entity.SysMenu;
import com.clz.web.sys_menu.service.SysMenuService;
import com.clz.web.sys_user.entity.SysUser;
import com.clz.web.sys_user.service.SysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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

    @Resource
    SysUserService sysUserService;
    @Test
    public void test2(){
        SysUser user = sysUserService.getById(1L);
        System.out.println(user);
    }

    @Resource
    SysMenuService sysMenuService;
    @Test
    public void test3(){
        List<SysMenu> menuList = sysMenuService.getMenuByUserId(3L);
        System.out.println(menuList);
    }
    @Resource
    MemberRechargeMapper memberRechargeMapper;
    @Test
    public void test4(){
        Page<MemberRecharge> page = new Page<>(1, 10);
        IPage<MemberRecharge> rechargeList = memberRechargeMapper.getRechargeList(page);
        System.out.println(rechargeList);
    }


}
