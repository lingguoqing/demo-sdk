package com.demo.demosdk.utils;

import com.sun.mail.util.MailSSLSocketFactory;
import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * 邮件发送工具
 * Author:guoqing.ling
 * Date:2025/05/13  9:55
 * package_name: com.wfql.wfqlsdk.utils
 * classname : SendEmailUtil
 */
@Slf4j
public class SendEmailUtil {

    public SendEmailUtil() {
    }

    public static void sendEmail(String smtpServer, String fromEmailAddress, String fromEmailKey, String subject, List<String> toEmailAddressList, String context, List<String> copyEmailAddressList, Boolean debug) throws Exception {

        // 发送邮件操作
        Properties prop = new Properties();
        prop.setProperty("mail.host", smtpServer);///设置QQ邮件服务器
        prop.setProperty("mail.transport.protocol", "smtp");///邮件发送协议
        prop.setProperty("mail.smtp.auth", "true");//需要验证用户密码
        //QQ邮箱需要设置SSL加密
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);

        //使用javaMail发送邮件的5个步骤
        //1.创建定义整个应用程序所需要的环境信息的session对象
        Session session = Session.getDefaultInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmailAddress, fromEmailKey);
            }
        });
        //开启session的debug模式，这样可以查看到程序发送Email的运行状态
        session.setDebug(debug);
        //2.通过session得到transport对象
        Transport ts = session.getTransport();
        //3.使用邮箱的用户名和授权码连上邮件服务器
        log.debug("fromEmailKey {}", fromEmailAddress);
        log.debug("fromEmailKey key {}", fromEmailKey);
        log.debug("smtpServer key {}", smtpServer);
        ts.connect(smtpServer, fromEmailKey, fromEmailKey);
        //4.创建邮件：写文件
        //注意需要传递session
        MimeMessage message = new MimeMessage(session);
        //指明邮件的发件人
        message.setFrom(new InternetAddress(fromEmailAddress));
        //指明邮件的收件人
        InternetAddress[] toAddress = InternetAddress.parse(String.join(",", toEmailAddressList));
        message.setRecipients(Message.RecipientType.TO, toAddress);
        //增加抄送人
        if (copyEmailAddressList != null && !copyEmailAddressList.isEmpty()) {
            InternetAddress[] ccAddress = InternetAddress.parse(String.join(",", copyEmailAddressList));
            message.setRecipients(Message.RecipientType.CC, ccAddress);
        }
        //邮件标题
        message.setSubject(subject);
        //邮件的文本内容
        message.setContent(context, "text/html;charset=UTF-8");
        //5.发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        //6.关闭连接
        ts.close();
    }

    public static void main(String[] args) throws Exception {
        List<String> strings = new ArrayList<>();
        strings.add("guoqing.ling@wfjt.com");
//        邮件服务器：smtp.exmail.qq.com；邮件服务器key：MeJBnM3FxVaktPcY；主题：天然气采集异常设备提醒；发送方地址：notice@wfjt.com；接收方地址：guoqing.ling@wfjt.com；抄送人地址：{}
    }

}