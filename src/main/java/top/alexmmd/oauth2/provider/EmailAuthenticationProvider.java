package top.alexmmd.oauth2.provider;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import top.alexmmd.oauth2.token.EmailAuthenticationToken;
import top.alexmmd.oauth2.token.SmsCodeAuthenticationToken;
import top.alexmmd.oauth2.tokenGranter.EmailTokenGranter;
import top.alexmmd.oauth2.tokenGranter.SmsTokenGranter;
import top.alexmmd.oauth2.validate.VerifyCodeProcessorHolder;

import javax.annotation.Resource;

import static top.alexmmd.oauth2.domain.enums.VerifyCodeSceneEnum.LOGIN;

/**
 * 短信登陆鉴权 Provider，要求实现 AuthenticationProvider 接口
 *
 * @author 汪永晖
 */
@Component
@Slf4j
public class EmailAuthenticationProvider implements AuthenticationProvider {

    private final static String TEMPLATE = "username:{}, scene:{}, verifyCode:{}";

    @Lazy
    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private VerifyCodeProcessorHolder verifyCodeProcessorHolder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        EmailAuthenticationToken authenticationToken = (EmailAuthenticationToken) authentication;

        String email = authenticationToken.getEmail();
        String verifyCode = authenticationToken.getVerifyCode();
        log.info("email -> {}, verifyCode -> {}", email, verifyCode);

        checkEmailCode(email, verifyCode);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        // 此时鉴权成功后，返回一个填满用户信息的 SmsCodeAuthenticationToken
        EmailAuthenticationToken authenticationResult = new EmailAuthenticationToken(userDetails);

        authenticationResult.setDetails(authenticationToken);
        log.info("邮箱登录验证成功");
        return authenticationResult;
    }

    private void checkEmailCode(String email, String verifyCode) {
        // 从redis中获取该手机号对应的短信验证码
        Boolean isSuccess = verifyCodeProcessorHolder.findVerifyCodeProcessor(EmailTokenGranter.GRANT_TYPE)
                .verifyCode(email, LOGIN.getDescription(), verifyCode);

        if (!isSuccess) {
            String format = StrUtil.format(TEMPLATE, email, LOGIN.getDescription(), verifyCode);
            log.error("验证码错误{}", format);
            throw new BadCredentialsException("验证码错误" + format);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 判断 authentication 是不是 EmailAuthenticationToken 的子类或子接口
        return EmailAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
