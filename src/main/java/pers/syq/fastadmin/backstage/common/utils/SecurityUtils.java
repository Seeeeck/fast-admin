package pers.syq.fastadmin.backstage.common.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pers.syq.fastadmin.backstage.common.entity.AuthUser;

import java.util.Optional;

public class SecurityUtils {

    private SecurityUtils(){}

    public static Optional<AuthUser> getAuthUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null){
            return Optional.ofNullable((AuthUser) authentication.getPrincipal());
        }
        return Optional.empty();
    }

    public static Optional<String> getUserName(){
        Optional<AuthUser> authUser = getAuthUser();
        return authUser.map(AuthUser::getName);
    }

    public static Optional<Long> getUserId(){
        Optional<AuthUser> authUser = getAuthUser();
        return authUser.map(AuthUser::getUserId);
    }
}
