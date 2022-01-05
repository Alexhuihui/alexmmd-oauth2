package top.alexmmd.oauth2.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 汪永晖
 * @date 2021/12/30 19:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    private String mobile;

    private String verifyCode;
}
