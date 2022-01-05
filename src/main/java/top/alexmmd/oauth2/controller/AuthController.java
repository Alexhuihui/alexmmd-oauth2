package top.alexmmd.oauth2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.alexmmd.oauth2.api.CommonResult;
import top.alexmmd.oauth2.domain.dto.Oauth2TokenDto;
import top.alexmmd.oauth2.domain.entity.User;
import top.alexmmd.oauth2.domain.request.UserRequest;
import top.alexmmd.oauth2.service.principal.UserPrincipal;
import top.alexmmd.oauth2.token.SmsCodeAuthenticationToken;
import top.alexmmd.oauth2.tokenGranter.SmsTokenGranter;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 自定义Oauth2获取令牌接口
 *
 * @author Alex 2021/12/16
 */
@RestController
@RequestMapping("/oauth")
@Slf4j
public class AuthController {

    private final TokenEndpoint tokenEndpoint;

    public AuthController(TokenEndpoint tokenEndpoint) {
        this.tokenEndpoint = tokenEndpoint;
    }

    /**
     * Oauth2登录认证
     */
    @PostMapping("/token")
    public CommonResult<Oauth2TokenDto> postAccessToken(Principal principal,
                                                        @RequestParam Map<String, String> parameters)
            throws HttpRequestMethodNotSupportedException {

        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        return CommonResult.success(generateOauth2TokenDto(oAuth2AccessToken));
    }

    @PostMapping("/login/sms")
    public CommonResult<Oauth2TokenDto> userLogin(UserRequest request) {
        //封装所需要的参数
        Map<String, String> parameters = createClientParameters(request.getMobile(), request.getVerifyCode(), SmsTokenGranter.GRANT_TYPE);
        //生成验证token
        SmsCodeAuthenticationToken token = new SmsCodeAuthenticationToken(request.getMobile(), request.getVerifyCode());

        try {
            OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(token, parameters).getBody();
            return CommonResult.success(generateOauth2TokenDto(oAuth2AccessToken));
        } catch (Exception e) {
            log.error("手机验证码登录失败！！" + e.getMessage());
        }
        return CommonResult.failed("登录失败，请重试！");
    }

    private Oauth2TokenDto generateOauth2TokenDto(OAuth2AccessToken oAuth2AccessToken) {
        return Oauth2TokenDto.builder()
                .token(Objects.requireNonNull(oAuth2AccessToken).getValue())
                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
                .expiresIn(oAuth2AccessToken.getExpiresIn())
                .tokenHead("Bearer ").build();
    }

    //构造客户端请求
    private Map<String, String> createClientParameters(String mobile, String verifyCode, String grantType) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("mobile", mobile);
        parameters.put("verifyCode", verifyCode);
        parameters.put("grant_type", grantType);
        return parameters;
    }

}
