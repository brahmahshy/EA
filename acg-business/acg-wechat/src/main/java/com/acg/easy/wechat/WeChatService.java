package com.acg.easy.wechat;

import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class WeChatService {
    @Value("${acg.wx.appId}")
    private String appId;

    @Value("${acg.wx.secret}")
    private String appSecret;

    public String login(String code) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("appid", appId);
        paramMap.put("secret", appSecret);
        paramMap.put("grant_type", "authorization_code");
        paramMap.put("js_code", code);
        return HttpUtil.get("https://api.weixin.qq.com/sns/jscode2session", paramMap);
    }
}
