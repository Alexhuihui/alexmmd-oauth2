package top.alexmmd.oauth2.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 短信登录 AuthenticationToken
 *
 * @author 汪永晖
 */
public class EmailAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    /**
     * 邮箱
     */
    private final String email;

    /**
     * 验证码
     */
    private String verifyCode;

    /**
     * 用户详细信息
     */
    private Object principal;

    /**
     * 构建一个没有鉴权的 SmsCodeAuthenticationToken
     */
    public EmailAuthenticationToken(String email, String verifyCode) {
        super(null);
        this.email = email;
        this.verifyCode = verifyCode;
        setAuthenticated(false);
    }

    /**
     * 构建拥有鉴权的 SmsCodeAuthenticationToken
     */
    public EmailAuthenticationToken(UserDetails userDetails) {
        super(userDetails.getAuthorities());
        this.email = userDetails.getUsername();
        this.principal = userDetails;
        // must use super, as we override
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority buildValue instead");
        }

        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }

    public String getVerifyCode() {
        return this.verifyCode;
    }

    public String getEmail() {
        return email;
    }
}
