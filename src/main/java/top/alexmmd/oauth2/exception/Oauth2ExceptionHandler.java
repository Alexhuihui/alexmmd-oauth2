package top.alexmmd.oauth2.exception;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.alexmmd.oauth2.api.CommonResult;


/**
 * 全局处理Oauth2抛出的异常
 *
 * @author Alex 2021/12/16
 */
@ControllerAdvice
public class Oauth2ExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = OAuth2Exception.class)
    public CommonResult<String> handleOauth2(OAuth2Exception e) {
        return CommonResult.failed(e.getMessage());
    }
}
