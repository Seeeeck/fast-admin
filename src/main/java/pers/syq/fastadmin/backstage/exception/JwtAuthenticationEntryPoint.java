package pers.syq.fastadmin.backstage.exception;

import cn.hutool.json.JSONUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import pers.syq.fastadmin.backstage.common.constants.WebConstants;
import pers.syq.fastadmin.backstage.common.exception.ErrorCode;
import pers.syq.fastadmin.backstage.common.utils.R;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /**
     * 当用户尝试访问需要权限才能的REST资源而不提供Token或者Token错误或者过期时，
     * 将调用此方法发送401响应以及错误信息
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(WebConstants.CONTENT_TYPE_JSON);
        response.getWriter().write(JSONUtil.toJsonStr(R.error().errorCode(ErrorCode.UNAUTHORIZED)));
    }
}
