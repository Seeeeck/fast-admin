package pers.syq.fastadmin.backstage.common.filter;

import cn.hutool.crypto.SecureUtil;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import pers.syq.fastadmin.backstage.common.constants.SecurityConstants;
import pers.syq.fastadmin.backstage.common.utils.IPUtils;
import pers.syq.fastadmin.backstage.common.utils.JwtTokenUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final RedisTemplate<String, Object> redisTemplate;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, RedisTemplate<String, Object> redisTemplate) {
        super(authenticationManager);
        this.redisTemplate = redisTemplate;
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
            String id = JwtTokenUtils.getId(token);
            String ipAddr = IPUtils.getIpAddr(request);
            String tokenKey = SecureUtil.md5(SecurityConstants.REDIS_TOKEN_PREFIX + id + ipAddr);
            String previousToken = (String) redisTemplate.opsForValue().get(tokenKey);
            Long expireTime = redisTemplate.getExpire(tokenKey);
            if (!token.equals(previousToken) || expireTime == null || expireTime < 0) {
                SecurityContextHolder.clearContext();
                chain.doFilter(request, response);
                return;
            }else {
                if (expireTime < SecurityConstants.TOKEN_REFRESH_TIME){
                    redisTemplate.expire(tokenKey,expireTime + SecurityConstants.TOKEN_REFRESH_TIME, TimeUnit.MINUTES);
                }
                authentication = JwtTokenUtils.getAuthentication(token);
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
}
