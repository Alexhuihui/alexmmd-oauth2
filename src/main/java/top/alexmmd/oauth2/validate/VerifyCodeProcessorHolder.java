package top.alexmmd.oauth2.validate;

import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 汪永晖
 * @date 2022/1/5 16:36
 */
@Component
@RequiredArgsConstructor
public class VerifyCodeProcessorHolder {

    @Resource
    private Map<String, VerifyCodeProcessor> verifyCodeProcessorMap;

    public VerifyCodeProcessor findVerifyCodeProcessor(String type) {
        String name = type.toLowerCase() + VerifyCodeProcessor.class.getSimpleName();
        VerifyCodeProcessor processor = verifyCodeProcessorMap.get(name);
        if (ObjectUtil.isNull(processor)) {
            throw new VerifyCodeException("验证码处理器" + name + "不存在");
        }
        return processor;
    }
}
