package com.clz.config.springSecurity;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.clz.utils.ResultUtils;
import com.clz.utils.ResultVo;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

@Component("customerAccessDeniedHandler")
public class CustomerAccessDeniedHandler implements AccessDeniedHandler{
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        String res = JSONObject.toJSONString(
                new ResultVo("无权限访问,请联系管理员", 700, null),
                SerializerFeature.DisableCircularReferenceDetect);
        httpServletResponse.setContentType("application/json;charset=utf-8");
        ServletOutputStream out = httpServletResponse.getOutputStream();
        out.write(res.getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }
}
