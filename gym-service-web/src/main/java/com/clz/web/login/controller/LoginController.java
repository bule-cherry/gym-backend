package com.clz.web.login.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.clz.jwt.JwtUtils;
import com.clz.utils.ResultUtils;
import com.clz.utils.ResultVo;
import com.clz.web.login.entity.LoginParam;
import com.clz.web.login.entity.LoginResult;
import com.clz.web.member.entity.Member;
import com.clz.web.member.service.MemberService;
import com.clz.web.sys_user.entity.SysUser;
import com.clz.web.sys_user.service.SysUserService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    @Resource
    private DefaultKaptcha kaptcha;


    @PostMapping("image")
    public ResultVo imageCode(HttpServletRequest request) {
        //获取验证码
        String code = kaptcha.createText();
        //存储验证码到session
        request.getSession().setAttribute("code", code);
        //生成图片
        BufferedImage bufferedImage = kaptcha.createImage(code);// 这个是内存中的图片, 每个像素点
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "jpg", out);
            BASE64Encoder encoder = new BASE64Encoder();
            String base64 = encoder.encode(out.toByteArray());
            String captchaBase64 = "data:image/jpeg;base64," + base64.replace("\r\n", "");
            return ResultUtils.success("生成成功", captchaBase64);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Autowired
    MemberService memberService;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    JwtUtils jwtUtils;

    //登录
    @PostMapping("/login")
    public ResultVo login(HttpServletRequest request, @RequestBody LoginParam param) {
        //从session中获取 验证码
        String code = (String) request.getSession().getAttribute("code");
        //验证验证码
        if (code != null && !code.equals(param.getCode())) {
            return ResultUtils.error("验证码错误");
        }
        // 得到用户名 和 md5 加密后的密码
        String username = param.getUsername();
        String password = DigestUtils.md5DigestAsHex(param.getPassword().getBytes());
        String type = param.getUserType();
        //用户类型判断
        if (type.equals("1")) { // 会员
            LambdaQueryWrapper<Member> query = new LambdaQueryWrapper<Member>().eq(Member::getUsername, username).eq(Member::getPassword, password);
            Member one = memberService.getOne(query);
            if (one == null) {
                return ResultUtils.error("用户名或密码错误!");
            }
            //生成token, 返回成功信息
            HashMap<String, String> map = new HashMap<>();
            map.put("userId", one.getMemberId().toString());
            map.put("username", one.getUsername());
            String token = jwtUtils.generateToken(map);
            System.out.println("token=" + token);
            LoginResult result = new LoginResult(one.getMemberId(), one.getUsername(), token, "1");
            return ResultUtils.success("登录成功", result);
        } else if (type.equals("2")) { // 用户
            LambdaQueryWrapper<SysUser> query = new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username).eq(SysUser::getPassword,password);
            SysUser one = sysUserService.getOne(query);
            if (one == null) {
                return ResultUtils.error("用户名或密码错误!");
            }
            //生成token, 返回成功信息
            HashMap<String, String> map = new HashMap<>();
            map.put("userId", one.getUserId().toString());
            map.put("username", one.getUsername());
            String token = jwtUtils.generateToken(map);
            LoginResult result = new LoginResult(new Long(one.getUserId()), one.getUsername(), token, "2");
            return ResultUtils.success("登录成功", result);
        } else {
            return ResultUtils.error("用户类型错误");
        }
    }
}
