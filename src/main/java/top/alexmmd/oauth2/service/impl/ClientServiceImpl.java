package top.alexmmd.oauth2.service.impl;

import cn.hutool.core.collection.CollUtil;
import org.redisson.api.RedissonClient;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import top.alexmmd.oauth2.constant.MessageConstant;
import top.alexmmd.oauth2.constant.RedisConstant;
import top.alexmmd.oauth2.domain.entity.Client;
import top.alexmmd.oauth2.service.ClientService;
import top.alexmmd.oauth2.service.principal.ClientPrincipal;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 客户端管理业务类
 *
 * @author Alex 2021/12/18
 */
@Service
public class ClientServiceImpl implements ClientService {

    private List<Client> clientList;
    private final PasswordEncoder passwordEncoder;

    public ClientServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Resource
    private RedissonClient redissonClient;

    @PostConstruct
    public void initData() {
        String clientSecret = passwordEncoder.encode("123456");
        clientList = redissonClient.getList(RedisConstant.CLIENT_LIST);
        // 1、密码模式
        clientList.add(Client.builder()
                .clientId("client-app")
                .resourceIds("oauth2-resource")
                .secretRequire(false)
                .clientSecret(clientSecret)
                .scopeRequire(false)
                .scope("all")
                .authorizedGrantTypes("email,sms,password,refresh_token")
                .authorities("ADMIN,USER")
                .accessTokenValidity(3600)
                .refreshTokenValidity(86400).build());
        // 2、授权码模式
        clientList.add(Client.builder()
                .clientId("client-app-2")
                .resourceIds("oauth2-resource2")
                .secretRequire(false)
                .clientSecret(clientSecret)
                .scopeRequire(false)
                .scope("all")
                .authorizedGrantTypes("authorization_code,refresh_token")
                .webServerRedirectUri("https://www.gathub.cn,https://www.baidu.com")
                .authorities("USER")
                .accessTokenValidity(3600)
                .refreshTokenValidity(86400).build());
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        List<Client> findClientList = clientList.stream().filter(item -> item.getClientId().equals(clientId)).collect(Collectors.toList());
        if (CollUtil.isEmpty(findClientList)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstant.NOT_FOUND_CLIENT);
        }
        return new ClientPrincipal(findClientList.get(0));
    }
}
