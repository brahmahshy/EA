package com.acg.easy.controller.wechat;

import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/wx")
public class WeChatController {
    @Value("${acg.wx.appId}")
    private String appId;

    @Value("${acg.wx.secret}")
    private String appSecret;

    private String accessToken;

    @PostMapping("/login")
    public String login(@RequestBody String code) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("appid", appId);
        paramMap.put("secret", appSecret);
        paramMap.put("grant_type", "authorization_code");
        paramMap.put("js_code", code);
        System.out.println(paramMap);
        String s = HttpUtil.get("https://api.weixin.qq.com/sns/jscode2session", paramMap);
        System.out.println(s);
        return s;
    }

    //    @Scheduled(cron = "* * 0/1 * * ? ")
    @PostMapping("/token")
    public void getAccessToken() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("appid", appId);
        paramMap.put("secret", appSecret);
        paramMap.put("grant_type", "client_credential");
        this.accessToken = HttpUtil.get("https://api.weixin.qq.com/cgi-bin/token", paramMap);
        log.debug(accessToken);
    }
}
