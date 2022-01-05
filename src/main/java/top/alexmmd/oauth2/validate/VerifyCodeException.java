package top.alexmmd.oauth2.validate;

/**
 * @author 汪永晖
 * @date 2022/1/5 16:00
 */
public class VerifyCodeException extends RuntimeException {

    public VerifyCodeException(String message) {
        super(message);
    }
}
