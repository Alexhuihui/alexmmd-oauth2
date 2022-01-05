package top.alexmmd.oauth2.validate.sms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.alexmmd.oauth2.validate.VerifyCodeGenerator;
import top.alexmmd.oauth2.validate.util.RandomCode;

/**
 * 手机验证码生成器
 *
 * @author 汪永晖
 * @date 2022/1/5 15:47
 */
@Component
@Slf4j
public class SmsVerifyCodeGenerator implements VerifyCodeGenerator {
    @Override
    public String generate(Integer length, Boolean onlyNum) {
        // 定义手机验证码生成策略，可以使用 request 中从请求动态获取生成策略
        // 可以从配置文件中读取生成策略
        return RandomCode.random(length, onlyNum);
    }
}
