package com.demo.demosdk.client;


import com.demo.demosdk.utils.SendEmailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author:guoqing.ling
 * Date:2025/05/13  9:52
 * package_name: com.wfql.wfqlsdk.client
 * classname : WfqlAppClient
 */
@Slf4j
public class DemoEmailClient {

    private String smtpServer;
    private String fromEmailKey;
    private String fromEmailAddres;
    private Boolean debug;


    public DemoEmailClient() {
    }

    public DemoEmailClient(String smtpServer, String fromEmailKey, String fromEmailAddres, Boolean debug) {
        this.smtpServer = smtpServer;
        this.fromEmailAddres = fromEmailAddres;
        this.fromEmailKey = fromEmailKey;
        this.debug = debug;

    }


    public void sendEmail(List<String> toEmailAddressList, String subject, String context) throws Exception {
        this.sendEmail(this.fromEmailAddres, toEmailAddressList, subject, context, new ArrayList<>(), debug);
    }

    public void sendEmail(List<String> toEmailAddressList,List<String> copyEmailAddressList, String subject, String context) throws Exception {
        this.sendEmail(this.fromEmailAddres, toEmailAddressList, subject, context, copyEmailAddressList, debug);
    }

    /**
     * 不抄送
     *
     * @param fromEmailAddress   发送邮件地址
     * @param subject            主题
     * @param toEmailAddressList 接收方地址
     * @param context            内容
     * @return
     */
    public void sendEmail(String fromEmailAddress, List<String> toEmailAddressList, String subject, String context) throws Exception {
        this.sendEmail(fromEmailAddress, toEmailAddressList, subject, context, new ArrayList<>(), debug);
    }

    /**
     * 抄送
     *
     * @param fromEmailAddress     发送邮件地址
     * @param subject              主题
     * @param toEmailAddressList   接收方地址
     * @param context              内容
     * @param copyEmailAddressList 抄送邮件地址
     * @return
     */
    public void sendEmail(String fromEmailAddress, List<String> toEmailAddressList, String subject, String context, List<String> copyEmailAddressList, Boolean debug) throws Exception {
        log.debug("开始发送邮件");
        log.debug("邮件服务器：{}；邮件服务器key：{}；主题：{}；发送方地址：{}；接收方地址：{}；抄送人地址：{}",
                smtpServer,
                fromEmailKey,
                subject,
                fromEmailAddress,
                toEmailAddressList,
                copyEmailAddressList.stream().collect(Collectors.joining(",", "{", "}")));
        log.debug("发送内容：{}", context);
        SendEmailUtil.sendEmail(smtpServer, fromEmailAddress, fromEmailKey, subject, toEmailAddressList, context, copyEmailAddressList, debug);
        log.debug("邮件发送成功");
    }

}