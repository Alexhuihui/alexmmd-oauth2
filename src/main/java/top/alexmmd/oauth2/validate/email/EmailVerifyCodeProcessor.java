package top.alexmmd.oauth2.validate.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.alexmmd.oauth2.validate.impl.AbstractVerifyCodeProcessor;

/**
 * @author 汪永晖
 * @date 2022/1/5 15:40
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class EmailVerifyCodeProcessor extends AbstractVerifyCodeProcessor {
    @Override
    protected void send(String username, String scene, String verifyCode) {
        log.info("邮箱验证码发送成功:{}, {}, {}", username, scene, verifyCode);
    }
}
