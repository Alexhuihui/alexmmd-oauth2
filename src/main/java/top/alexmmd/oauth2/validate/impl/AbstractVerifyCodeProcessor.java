package top.alexmmd.oauth2.validate.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import top.alexmmd.oauth2.validate.VerifyCodeException;
import top.alexmmd.oauth2.validate.VerifyCodeGenerator;
import top.alexmmd.oauth2.validate.VerifyCodeProcessor;
import top.alexmmd.oauth2.validate.VerifyRepository;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 验证码处理的模板方法
 *
 * @author 汪永晖
 * @date 2022/1/5 15:08
 */
@Slf4j
public abstract class AbstractVerifyCodeProcessor implements VerifyCodeProcessor {

    private final static String EMAIL_TYPE = "email";
    private final static String SMS_TYPE = "sms";

    @Resource
    private Map<String, VerifyCodeGenerator> verifyCodeGeneratorMap;

    @Resource
    private VerifyRepository verifyRepository;

    @Override
    public void createCode(String username, String scene) {
        String verifyCode = generate(username, scene);
        this.save(username, scene, verifyCode);
        this.send(username, scene, verifyCode);
    }

    protected abstract void send(String username, String scene, String verifyCode);

    private void save(String username, String scene, String verifyCode) {
        verifyRepository.save(username, scene, verifyCode);
    }

    private String generate(String username, String scene) {
        // 根据username判断选取哪种验证码生成器
        String type = getValidateCodeType(username);
        String componentName = type + VerifyCodeGenerator.class.getSimpleName();
        VerifyCodeGenerator verifyCodeGenerator = verifyCodeGeneratorMap.get(componentName);
        if (ObjectUtil.isNull(verifyCodeGenerator)) {
            log.error("找不到对应的验证码生成器，{}", componentName);
            throw new VerifyCodeException("找不到对应的验证码生成器");
        }
        return verifyCodeGenerator.generate(4, true);
    }

    private String getValidateCodeType(String username) {
        if (Validator.isEmail(username)) {
            return EMAIL_TYPE;
        }
        return SMS_TYPE;
    }

    @Override
    public void verifyCode(String username, String scene, String verifyCode) {

    }
}
