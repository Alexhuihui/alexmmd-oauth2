package top.alexmmd.oauth2.validate.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.alexmmd.oauth2.validate.VerifyCodeGenerator;
import top.alexmmd.oauth2.validate.util.RandomCode;

/**
 * 邮箱验证码生成器
 *
 * @author 汪永晖
 * @date 2022/1/5 15:49
 */
@Component
@Slf4j
public class EmailVerifyCodeGenerator implements VerifyCodeGenerator {
    @Override
    public String generate(Integer length, Boolean onlyNum) {
        return RandomCode.random(length, onlyNum);
    }
}
