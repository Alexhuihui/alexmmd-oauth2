package top.alexmmd.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 启动类
 */
@EnableDiscoveryClient
@SpringBootApplication
public class AlexmmdOauth2Application {

    public static void main(String[] args) {
        SpringApplication.run(AlexmmdOauth2Application.class, args);
    }

}
