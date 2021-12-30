package top.alexmmd.oauth2.service.impl;

import cn.hutool.core.collection.CollUtil;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import top.alexmmd.oauth2.constant.RedisConstant;
import top.alexmmd.oauth2.service.ResourceService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * 资源与角色匹配关系管理业务类
 *
 * @author Alex 2021/12/16
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Resource
    private RedissonClient redissonClient;

    @PostConstruct
    public void initData() {
        RMap<String, List<String>> map = redissonClient.getMap(RedisConstant.RESOURCE_ROLES_MAP);
        map.put("/user/hello", CollUtil.toList("ADMIN"));
        map.put("/user/currentUser", CollUtil.toList("ADMIN", "USER"));
    }
}
