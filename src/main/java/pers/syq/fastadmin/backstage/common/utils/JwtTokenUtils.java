package pers.syq.fastadmin.backstage.common.utils;


import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pers.syq.fastadmin.backstage.common.constants.SecurityConstants;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shuang.kou
 * @description JWT工具类
 */
public class JwtTokenUtils {

    private static final String SECRET_KEY = SecurityConstants.JWT_SECRET_KEY;


    public static String createToken(String username, String id, List<String> permissions) {
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + SecurityConstants.EXPIRATION * 1000);
        String tokenPrefix = Jwts.builder()
                .setHeaderParam("type", SecurityConstants.TOKEN_TYPE)
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .claim(SecurityConstants.PERMISSION_CLAIMS, String.join(",", permissions))
                .setId(id)
                .setIssuer("FastAdmin")
                .setIssuedAt(createdDate)
                .setSubject(username)
                .setExpiration(expirationDate)
                .compact();
        return SecurityConstants.TOKEN_PREFIX + tokenPrefix; // 添加 token 前缀 "Bearer ";
    }

    public static String getId(String token) {
        Claims claims = getClaims(token);
        return claims.getId();
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
        Claims claims = getClaims(token);
        List<SimpleGrantedAuthority> authorities = getAuthorities(claims);
        String userName = claims.getSubject();
        return new UsernamePasswordAuthenticationToken(userName, token, authorities);
    }

    private static List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
        String permissions = (String) claims.get(SecurityConstants.PERMISSION_CLAIMS);
        return Arrays.stream(permissions.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private static Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public static String parseToken(String token){
        if (StrUtil.isNotBlank(token) && token.startsWith(SecurityConstants.TOKEN_PREFIX)){
            return token.replaceFirst(SecurityConstants.TOKEN_PREFIX,"");
        }
        return "";
    }

}
