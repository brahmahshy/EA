package com.easyacg.controller.storage;

import com.easyacg.core.entity.ResponseVo;
import com.easyacg.storage.entity.input.CreateStorageBo;
import com.easyacg.storage.repository.StorageRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 照片服务
 *
 * @author brahma
 */
@Slf4j
@RestController
@RequestMapping("/storage")
public class StorageController {
    @Resource
    private StorageRepository storageRepository;

    /**
     * 读取照片
     *
     * @return 是否成功
     */
    @GetMapping("/create")
    @Operation(description = "创建存储策略")
    public ResponseVo<Void> create(@RequestBody @Valid CreateStorageBo storageBo) {
        return ResponseVo.success();
    }
}
