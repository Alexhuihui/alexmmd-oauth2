package top.alexmmd.oauth2.validate.impl;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import top.alexmmd.oauth2.validate.VerifyRepository;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author 汪永晖
 * @date 2022/1/5 16:05
 */
@Component
@RequiredArgsConstructor
public class VerifyRepositoryImpl implements VerifyRepository {

    /**
     * 前缀
     */
    private final static String PREFIX = "verifyCode:";

    /**
     * 验证码存活时间
     */
    private final static Integer SURVIVE = 15;

    @Resource
    private RedissonClient redissonClient;

    @Override
    public void save(String username, String scene, String verifyCode) {
        RBucket<String> bucket = redissonClient.getBucket(buildKey(username, scene));
        bucket.set(verifyCode, SURVIVE, TimeUnit.MINUTES);
    }

    @Override
    public String get(String username, String scene) {
        RBucket<String> bucket = redissonClient.getBucket(buildKey(username, scene));
        return bucket.get();
    }

    @Override
    public void delete(String username, String scene) {
        RBucket<String> bucket = redissonClient.getBucket(buildKey(username, scene));
        bucket.delete();
    }

    public String buildKey(String username, String scene) {
        return PREFIX + username + StrUtil.COLON + scene;
    }
}
