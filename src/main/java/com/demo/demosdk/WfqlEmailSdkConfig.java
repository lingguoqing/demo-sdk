package com.demo.demosdk;

import com.demo.demosdk.client.DemoEmailClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("demo.email")
@Data
@ComponentScan
public class WfqlEmailSdkConfig {

    private String smtpServer;
    private String fromEmailKey;
    private String fromEmailAddres;
    private Boolean debug;

    @Bean
    public DemoEmailClient wfqlAppClient() {
        return new DemoEmailClient(smtpServer, fromEmailKey, fromEmailAddres, debug);
    }

}