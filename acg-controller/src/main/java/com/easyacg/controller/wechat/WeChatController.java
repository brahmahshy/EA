package com.easyacg.controller.wechat;

import com.easyacg.wechat.WeChatService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/wx")
public class WeChatController {
    @Resource
    private WeChatService weChatService;

    @PostMapping("/login")
    public String login(@RequestBody String code) {
        return weChatService.login(code);
    }
}
