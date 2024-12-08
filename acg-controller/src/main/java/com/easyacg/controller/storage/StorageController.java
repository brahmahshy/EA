package com.easyacg.controller.storage;

import com.easyacg.core.entity.ResponseVo;
import com.easyacg.storage.entity.input.CreateStorageBo;
import com.easyacg.storage.repository.StorageRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 存储策略Controller
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
     * 创建存储策略
     *
     * @return 是否成功
     */
    @PostMapping("/create")
    @Operation(description = "创建存储策略")
    public ResponseVo<Void> create(@RequestBody @Valid CreateStorageBo storageBo) {
        storageRepository.save(storageBo.trans());
        return ResponseVo.success();
    }
}
