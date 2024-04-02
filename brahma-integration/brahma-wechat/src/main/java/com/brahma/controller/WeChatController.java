package com.brahma.controller;

import lombok.extern.slf4j.Slf4j;

import com.brahma.util.HttpsUtil;

import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/wx")
public class WeChatController {
    @Value("${brahma.wx.appId:wx78ff3d63b5b24d44}")
    private String appid;

    @Value("${brahma.wx.secret:6ac187171f4e380930480e9c2036017b}")
    private String appSecret;

    private String accessToken;

    @PostMapping("/login")
    public String login(@RequestBody String code) {
        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("appid", appid));
        list.add(new BasicNameValuePair("secret", appSecret));
        list.add(new BasicNameValuePair("grant_type", "authorization_code"));
        list.add(new BasicNameValuePair("js_code", code));
        String s = HttpsUtil.get("https://api.weixin.qq.com/sns/jscode2session", list);
        log.debug(s);
        return s;
    }

//    @Scheduled(cron = "* * 0/1 * * ? ")
    @PostMapping("/token")
    public void getAccessToken() {
        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("appid", appid));
        list.add(new BasicNameValuePair("secret", appSecret));
        list.add(new BasicNameValuePair("grant_type", "client_credential"));
        this.accessToken = HttpsUtil.get("https://api.weixin.qq.com/cgi-bin/token", list);
        log.debug(accessToken);
    }
}
