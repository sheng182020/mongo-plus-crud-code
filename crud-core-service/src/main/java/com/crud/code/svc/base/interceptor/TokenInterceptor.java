package com.crud.code.svc.base.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import com.crud.code.svc.base.enums.ResponseStatusEnum;
import com.crud.code.svc.base.exception.BaseApiException;
import com.crud.code.svc.base.config.IgnoreToken;
import com.crud.code.svc.base.constant.SystemContext;
import com.crud.code.svc.base.utils.UserContextUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import static cn.hutool.extra.validation.ValidationUtil.validate;


@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {


    @Value("${jwt.secretKey}")
    private String secretKey;

    private Long EXPIRE = Long.valueOf(30 * 60);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String path = request.getRequestURI();
        try {
            HandlerMethod method = (HandlerMethod) handler;
            if (method.getMethodAnnotation(IgnoreToken.class) != null) {
                return true;
            }
        } catch (Exception e) {
            log.error("log path: {}", path);
            throw new BaseApiException(ResponseStatusEnum.NOT_FOUND);
        }

        String tokenInHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isBlank(tokenInHeader)) {
            throw new BaseApiException(ResponseStatusEnum.UNAUTHORIZED);
        }
        verify(tokenInHeader);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        if (ex != null) {
            log.error(ex.getMessage(), ex);
        }
        UserContextUtils.removeContext();
    }

     private Boolean verify(String token){
         if (StrUtil.isBlank(token) || !token.startsWith(SystemContext.TOKEN_PREFIX)) {
             throw new BaseApiException(ResponseStatusEnum.UNAUTHORIZED);
         }

         token = token.replace(SystemContext.TOKEN_PREFIX, "").trim();
         if (StringUtils.isBlank(token)) {
             throw new BaseApiException(ResponseStatusEnum.UNAUTHORIZED);
         }

         JWT jwt = JWT.of(token).setKey(secretKey.getBytes());
         if (!jwt.verify()) {
             throw new BaseApiException(ResponseStatusEnum.FORBIDDEN);
         }

         String userId = StrUtil.utf8Str(jwt.getPayload().getClaim("id"));
         String email = StrUtil.utf8Str(jwt.getPayload().getClaim("email"));
         UserContextUtils.setUser(userId, email);
         return true;
    }
}

