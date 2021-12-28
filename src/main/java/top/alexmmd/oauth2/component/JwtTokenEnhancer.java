package top.alexmmd.oauth2.component;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;
import top.alexmmd.oauth2.service.principal.UserPrincipal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * JWT内容增强器
 *
 * @author Alex 2021/12/16
 */
@Component
public class JwtTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Map<String, Object> info = new HashMap<>();
        // 把用户ID设置到JWT中
        info.put("id", userPrincipal.getId());
        // 把用户角色放入JWT中
        ArrayList<String> arrayList = new ArrayList<>();
        userPrincipal.getAuthorities().forEach(grantedAuthority -> {
            arrayList.add(grantedAuthority.getAuthority());
        });
        info.put("role", arrayList);
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }
}
