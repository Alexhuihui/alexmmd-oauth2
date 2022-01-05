package top.alexmmd.oauth2.service;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.security.Principal;
import java.util.Map;

/**
 * 认证接口
 *
 * @author 汪永晖
 * @date 2021/12/31 11:04
 */
public interface AuthenticationService {

    OAuth2AccessToken auth(Principal principal, Map<String, String> parameters);
}
