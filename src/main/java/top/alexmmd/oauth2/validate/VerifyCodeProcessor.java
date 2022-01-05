package top.alexmmd.oauth2.validate;

/**
 * 验证码处理接口
 *
 * @author 汪永晖
 * @date 2022/1/5 15:01
 */
public interface VerifyCodeProcessor {

    /**
     * 创建验证码
     *
     * @param username 用户名
     * @param scene    验证码使用场景
     */
    void createCode(String username, String scene);

    /**
     * 验证码校验
     *
     * @param username   用户名
     * @param scene      验证码使用场景
     * @param verifyCode 验证码
     */
    void verifyCode(String username, String scene, String verifyCode);
}
