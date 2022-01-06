package top.alexmmd.oauth2.domain.enums;

/**
 * 验证码类型枚举
 *
 * @author 汪永晖
 * @date 2022/1/6 15:13
 */
public enum VerifyCodeTypeEnum {

    SMS(1, "sms"),

    EMAIL(2, "email"),
    ;


    private final Integer num;
    private final String description;

    VerifyCodeTypeEnum(Integer num, String description) {
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
