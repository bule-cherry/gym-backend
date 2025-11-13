package com.clz.config.springSecurity;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.clz.jwt.JwtUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.util.AntPathMatcher;


@Component
public class CheckTokenFilter extends OncePerRequestFilter {
    @Value("#{'${ignore.url}'.split(',')}")
    private List<String> ignoreUrls;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Resource
    private JwtUtils jwtUtils;
    @Resource
    private CustomUserDetailsService customUserDetailsService;
    @Resource
    private LoginFailureHandler loginFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String url = request.getRequestURI();

        try {
            boolean shouldIgnore = ignoreUrls.stream()
                    .anyMatch(ignore -> pathMatcher.match(ignore.trim(), url));

            if (!shouldIgnore && !url.startsWith("/images/")) {
                validateToken(request);
            }
        } catch (AuthenticationException e) {
            loginFailureHandler.commence(request, response, e);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void validateToken(HttpServletRequest request) {
        //从头部获取token
        String token = request.getHeader("token");
        //如果从头部获取token失败，那么从参数获取
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("token");
        }
        if (StringUtils.isEmpty(token)) {
            throw new CustomerAuthenionException("token获取失败");
        }
        if (!jwtUtils.verify(token)) {
            throw new CustomerAuthenionException("token验证失败");
        }
        DecodedJWT decodedJWT = jwtUtils.jwtDecode(token);
        Map<String, Claim> claims = decodedJWT.getClaims();
        String username = claims.get("username").asString();
        String userType = claims.get("userType").asString();
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username + ":" + userType);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        //设置到spring security上下文
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
