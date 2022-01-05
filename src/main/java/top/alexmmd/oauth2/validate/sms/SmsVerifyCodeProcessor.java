package top.alexmmd.oauth2.validate.sms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.alexmmd.oauth2.validate.impl.AbstractVerifyCodeProcessor;

/**
 * @author 汪永晖
 * @date 2022/1/5 15:39
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SmsVerifyCodeProcessor extends AbstractVerifyCodeProcessor {
    @Override
    protected void send(String username, String scene, String verifyCode) {
        log.info("手机验证码发送成功:{}, {}, {}", username, scene, verifyCode);
    }
}
