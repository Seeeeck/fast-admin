package pers.syq.fastadmin.backstage.common.filter;

import cn.hutool.crypto.SecureUtil;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import pers.syq.fastadmin.backstage.common.constants.SecurityConstants;
import pers.syq.fastadmin.backstage.common.utils.JwtTokenUtils;
import pers.syq.fastadmin.backstage.common.utils.RedisUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final RedisUtils redisUtils;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, RedisUtils redisUtils) {
        super(authenticationManager);
        this.redisUtils = redisUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER);
        if (token == null || !token.startsWith(SecurityConstants.TOKEN_PREFIX)){
            SecurityContextHolder.clearContext();
            chain.doFilter(request,response);
            return;
        }
        token = token.replace(SecurityConstants.TOKEN_PREFIX, "");
        UsernamePasswordAuthenticationToken authentication = null;
        try {
            String tokenKey = this.getTokenKey(token);
            Optional<String> previousToken = redisUtils.getStringValue(tokenKey);
            Long expireTime = redisUtils.getExpire(tokenKey);
            if (previousToken.isPresent() && token.equals(previousToken.get()) && expireTime > 0) {
                if (expireTime < SecurityConstants.TOKEN_REFRESH_TIME){
                    redisUtils.expire(tokenKey,expireTime + SecurityConstants.TOKEN_REFRESH_TIME, TimeUnit.MINUTES);
                }
                authentication = JwtTokenUtils.getAuthentication(token);
            }else {
                SecurityContextHolder.clearContext();
                chain.doFilter(request, response);
                return;
            }
        } catch (JwtException e) {
            logger.error("Invalid jwt : " + e.getMessage());
        }
        if (authentication != null){
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }else {
            SecurityContextHolder.clearContext();
        }
        chain.doFilter(request, response);
    }

    private String getTokenKey(String token){
        String id = JwtTokenUtils.getId(token);
        return SecureUtil.md5(SecurityConstants.REDIS_TOKEN_PREFIX + id);
    }
}
