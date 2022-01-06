package top.alexmmd.oauth2.domain.enums;

/**
 * 验证码使用场景枚举
 *
 * @author 汪永晖
 * @date 2022/1/6 15:08
 */
public enum VerifyCodeSceneEnum {

    LOGIN(1, "Login"),
    ;

    private final Integer num;
    private final String description;

    VerifyCodeSceneEnum(Integer num, String description) {
        this.num = num;
        this.description = description;
    }

    public Integer getNum() {
        return num;
    }

    public String getDescription() {
        return description;
    }
}
