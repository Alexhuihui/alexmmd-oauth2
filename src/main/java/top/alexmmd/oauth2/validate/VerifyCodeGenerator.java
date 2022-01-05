package top.alexmmd.oauth2.validate;

/**
 * @author 汪永晖
 * @date 2022/1/5 15:44
 */
public interface VerifyCodeGenerator {

    /**
     * 生成验证码
     *
     * @param length  长度
     * @param onlyNum 是否是纯数字
     * @return 验证码
     */
    String generate(Integer length, Boolean onlyNum);

}
