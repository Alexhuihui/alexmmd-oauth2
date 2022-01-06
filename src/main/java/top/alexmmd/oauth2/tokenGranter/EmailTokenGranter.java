package top.alexmmd.oauth2.tokenGranter;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import top.alexmmd.oauth2.token.EmailAuthenticationToken;
import top.alexmmd.oauth2.token.SmsCodeAuthenticationToken;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 汪永晖
 * @date 2021/12/30 16:05
 */
public class EmailTokenGranter extends AbstractTokenGranter {

    public static final String GRANT_TYPE = "email";

    private final AuthenticationManager authenticationManager;

    public EmailTokenGranter(AuthenticationManager authenticationManager,
                             AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        this(authenticationManager, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
    }

    protected EmailTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices,
                                ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {

        Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());
        String email = parameters.get("email");
        String verifyCode = parameters.get("verify_code");
        // Protect from downstream leaks of verify_code
        parameters.remove("verify_code");

        Authentication userAuth = new EmailAuthenticationToken(email, verifyCode);
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
        try {
            userAuth = authenticationManager.authenticate(userAuth);
        } catch (AccountStatusException | BadCredentialsException ase) {
            // covers expired, locked, disabled cases (mentioned in section 5.2, draft 31)
            // If the username/password are wrong the spec says we should send 400/invalid grant
            throw new InvalidGrantException(ase.getMessage());
        }
        if (userAuth == null || !userAuth.isAuthenticated()) {
            throw new InvalidGrantException("Could not authenticate user: " + email);
        }

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }
}
