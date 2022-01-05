package top.alexmmd.oauth2.service.impl;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Service;
import top.alexmmd.oauth2.domain.dto.Oauth2TokenDto;
import top.alexmmd.oauth2.service.AuthenticationService;
import top.alexmmd.oauth2.tokenGranter.SmsTokenGranter;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Map;
import java.util.Objects;

/**
 * 认证服务
 *
 * @author 汪永晖
 * @date 2021/12/31 11:06
 */
@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final static String GRANT_TYPE = "grant_type";

    @Resource
    private TokenEndpoint tokenEndpoint;

    @Override
    public OAuth2AccessToken auth(Principal principal, Map<String, String> parameters) {
        String grantType = parameters.get(GRANT_TYPE);
        if (StrUtil.equals(grantType, SmsTokenGranter.GRANT_TYPE)) {

        }
        return null;
    }

    private Oauth2TokenDto generateOauth2TokenDto(OAuth2AccessToken oAuth2AccessToken) {
        return Oauth2TokenDto.builder()
                .token(Objects.requireNonNull(oAuth2AccessToken).getValue())
                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
                .expiresIn(oAuth2AccessToken.getExpiresIn())
                .tokenHead("Bearer ").build();
    }
}
