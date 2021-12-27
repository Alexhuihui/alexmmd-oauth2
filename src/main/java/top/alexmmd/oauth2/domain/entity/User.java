package top.alexmmd.oauth2.domain.entity;

import lombok.*;

import java.util.List;

/**
 * @author Alex 2021/12/16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class User {
    private Long id;
    private String username;
    private String password;
    private Integer status;
    private List<String> roles;
}
