package top.alexmmd.oauth2.validate;

/**
 * @author 汪永晖
 * @date 2022/1/5 16:03
 */
public interface VerifyRepository {

    void save(String username, String scene, String verifyCode);

    String get(String username, String scene);

    void delete(String username, String scene);
}
