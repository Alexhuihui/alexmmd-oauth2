package top.alexmmd.oauth2.domain.request;

import lombok.*;

/**
 * @author 汪永晖
 * @date 2022/1/5 16:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class VerifyCodeReq {

    /**
     * 验证码接收人
     */
    private String username;

    /**
     * 验证码使用场景，例如登录
     */
    private String scene;

    /**
     * 验证码类型，短信或者邮箱
     */
    private String type;

}
