package pers.syq.fastadmin.backstage.exception;

import cn.hutool.json.JSONUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import pers.syq.fastadmin.backstage.common.constants.WebConstants;
import pers.syq.fastadmin.backstage.common.exception.ErrorCode;
import pers.syq.fastadmin.backstage.common.utils.R;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(WebConstants.CONTENT_TYPE_JSON);
        response.getWriter().write(JSONUtil.toJsonStr(R.error().errorCode(ErrorCode.FORBIDDEN)));
    }
}
