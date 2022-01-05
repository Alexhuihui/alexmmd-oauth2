package top.alexmmd.oauth2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.alexmmd.oauth2.api.CommonResult;
import top.alexmmd.oauth2.domain.request.VerifyCodeReq;
import top.alexmmd.oauth2.validate.VerifyCodeProcessorHolder;

import javax.annotation.Resource;

/**
 * @author 汪永晖
 * @date 2022/1/5 16:31
 */
@RestController
@Slf4j
@RequestMapping("/verify")
public class VerifyCodeController {

    @Resource
    private VerifyCodeProcessorHolder verifyCodeProcessorHolder;

    @GetMapping("/code")
    public CommonResult<String> createCode(@RequestBody VerifyCodeReq verifyCodeReq) {
        verifyCodeProcessorHolder.findVerifyCodeProcessor(verifyCodeReq.getType())
                .createCode(verifyCodeReq.getUsername(), verifyCodeReq.getScene());
        return CommonResult.success("获取验证码成功");
    }
}
